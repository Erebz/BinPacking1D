package core;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;

public class BinPacking {
    private List<Item> items;
    private int tailleBin;
    private String nomProbleme;
    private StrategieVoisinage voisinage;

    public BinPacking(){
        nomProbleme = "Problème vide";
        this.items = new ArrayList<Item>();
        this.voisinage = new StrategieDeplacer();
    }

    public BinPacking(String filename){
        this();
        this.nomProbleme = filename;
        this.initFromFile(filename);
    }

    private void initFromFile(String filename) {
        try {
            File file = new File("./data/" + filename);
            Scanner reader = new Scanner(file);
            if(reader.hasNextLine()){
                //Premiere ligne
                String line = reader.nextLine();
                String[] datas = line.split(" ");
                tailleBin = Integer.parseInt(datas[0]);
            }
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                int taille = Integer.parseInt(line);
                this.items.add(new Item(taille));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getBorneInferieure() {
        int borne = 0;
        for (Item i : items){
            borne += i.getTaille();
        }
        return (int) Math.ceil((double) borne / tailleBin);
    }

    public int getTailleBin() {
        return tailleBin;
    }

    public void setVoisinage(StrategieVoisinage voisinage) {
        this.voisinage = voisinage;
    }

    public void trierItemsDecroissant(){
        items.sort(Collections.reverseOrder());
    }

    public void melangerItems(){ Collections.shuffle(items); }

    //Algorithmes de résolution
    private PackingSolution firstFit(){
        PackingSolution packingSolution = new PackingSolution();
        List<Bin> bins = packingSolution.getBins();
        for(Item item : items){
            boolean stop = false;
            for (int i=0; i < bins.size() && !stop; i++){
                Bin bin = bins.get(i);
                if(bin.peutAccueillir(item)){
                    bin.ajouterItem(item);
                    stop = true;
                }
            }
            if(!stop){
                Bin bin = new Bin(tailleBin);
                if(bin.peutAccueillir(item)){
                    bin.ajouterItem(item);
                }
                bins.add(bin);
            }
        }
        return packingSolution;
    }

    public PackingSolution firstFitDecreasing(){
        this.trierItemsDecroissant();
        return this.firstFit();
    }

    public PackingSolution firstFitRandom(){
        this.melangerItems();
        return this.firstFit();
    }

    public PackingSolution genererUnBinParItem(){
        PackingSolution packingSolution = new PackingSolution();
        for(Item i : items){
            Bin bin = new Bin(tailleBin);
            bin.ajouterItem(i);
            packingSolution.ajouterBin(bin);
        }
        return packingSolution;
    }

    public PackingSolution linearProgrammingResolution(){
        PackingSolution packingSolution = new PackingSolution();
        List<Bin> bins = packingSolution.getBins();
        int nbItems = items.size();
        int nbBinsMax = nbItems;
        Loader.loadNativeLibraries();
        MPSolver solver = MPSolver.createSolver("SCIP"); //GLOP, SCIP
        //Variables
        MPVariable[][] x = new MPVariable[nbItems][nbBinsMax];
        for (int i = 0; i < nbItems; i++) {
            for (int j = 0; j < nbBinsMax; j++) {
                String s = "X[" + i + "," + j +"]";
                x[i][j] = solver.makeBoolVar(s);
            }
        }
        MPVariable[] y = new MPVariable[nbBinsMax];
        for (int j = 0; j < nbBinsMax; j++) {
            String s = "Y" + j;
            y[j] = solver.makeBoolVar(s);
        }
        //Contraintes
        //Pour chaque  item i : somme(j=1 à nbBinsMax)[Xij] = 1
        for (int i = 0; i < nbItems; i++) {
            MPConstraint c = solver.makeConstraint(1.0, 1.0, "");
            for (int j = 0; j < nbBinsMax; j++) {
                c.setCoefficient(x[i][j], 1);
            }
        }
        //Pour chaque  bin j : somme(i=1 à nbItems)[Xij * Ti] <= TailleBinMax
        double infinity = Double.POSITIVE_INFINITY;
        for (int j = 0; j < nbBinsMax; j++) {
            //MPConstraint c = solver.makeConstraint(-infinity, tailleBin, "");
            MPConstraint constraint = solver.makeConstraint(0, infinity, "");
            constraint.setCoefficient(y[j], tailleBin);
            for (int i = 0; i < nbItems; i++) {
                //c.setCoefficient(x[i][j], items.get(i).getTaille());
                constraint.setCoefficient(x[i][j], -items.get(i).getTaille());
            }
        }
        //Objectif
        MPObjective objective = solver.objective();
        for (int j = 0; j < nbBinsMax; j++) {
            objective.setCoefficient(y[j], 1);
        }
        objective.setMinimization();
        //Résolution
        final MPSolver.ResultStatus resultat = solver.solve();
        if (resultat == MPSolver.ResultStatus.OPTIMAL) {
            //System.out.println("[Solution optimale] nbBins = " + objective.value());
            for (int j = 0; j < nbBinsMax; j++) {
                if (y[j].solutionValue() == 1) {
                    Bin bin = new Bin(tailleBin);
                    for (int i = 0; i < nbItems; i++) {
                        if (x[i][j].solutionValue() == 1) {
                            bin.ajouterItem(items.get(i));
                        }
                    }
                    bins.add(bin);
                }
            }
        } else {
            System.err.println("Pas de solution optimale.");
        }
        return packingSolution;
    }

    public PackingSolution recuitSimule(PackingSolution x0, double t0, int n1, int n2, double mu){
        PackingSolution xMax = x0;
        PackingSolution currentX = x0;
        double t = t0;
        double fMax = xMax.fitness();
        for (int k = 0; k < n1; k++){
            for (int l = 0; l < n2; l++){
                //On choisit un voisin aléatoirement
                Map<Transition, PackingSolution> voisins = voisinage.getVoisinage(currentX, 1);
                if(voisins.size() == 0) return xMax;
                Transition transition = (Transition) voisins.keySet().toArray()[0];
                PackingSolution newX = voisins.get(transition);
                double newFitness = newX.fitness();
                double delta =  newFitness - currentX.fitness(); //valeur absolue ?
                if(delta >= 0){
                    currentX = newX;
                    if(newFitness > fMax){
                        xMax = newX;
                        fMax = newFitness;
                    }
                }else{
                    double rand = Math.random();
                    double proba = Math.exp(-delta/t);
                    if(rand <= proba){
                        currentX = newX;
                    }
                }
            }
            t *= mu;
        }
        return xMax;
    }

    public PackingSolution methodeTabou(PackingSolution x0, int tailleTabou, int maxIter, int tailleVoisinage){
        PackingSolution xMax = x0;
        double fMax = x0.fitness();
        PackingSolution currentX = x0;
        LinkedList<Transition> T = new LinkedList<>();
        for (int i = 0 ; i < maxIter; i++){
            Map<Transition, PackingSolution> voisinage = this.voisinage.getVoisinage(currentX, tailleVoisinage);
            double maxFitness = 0;
            PackingSolution bestVoisin = null;
            Transition bestTransition = null;
            for(Transition t : voisinage.keySet()){
                PackingSolution sol = voisinage.get(t);
                double f = sol.fitness();
                if(f > maxFitness && !T.contains(t)) {
                    maxFitness = f;
                    bestVoisin = sol;
                    bestTransition = t;
                }
            }
            if(bestVoisin != null && bestTransition != null){
                double delta = maxFitness - currentX.fitness();
                if(delta >= 0){
                    Transition transitionInverse = bestTransition.getInverse();
                    if(transitionInverse != null) T.add(transitionInverse);
                    if(T.size() >= tailleTabou) T.poll();
                }
                currentX = bestVoisin;
                if(fMax < maxFitness){
                    xMax = currentX;
                    fMax = maxFitness;
                }
            }
        }
        return xMax;
    }

    public boolean estValide(PackingSolution solution){
        boolean estCorrect = true;
        List<Bin> bins = solution.getBins();
        //tous les items sont utilisés
        for (Item item : items) {
            boolean stop = false;
            for (int j = 0; j < bins.size() && !stop; j++) {
                Bin b = bins.get(j);
                if (b.contient(item)) {
                    stop = true;
                }
            }
            if (!stop) estCorrect = false;
        }
        //toutes les bins sont valides
        for (Bin bin : bins){
            if(bin.capaciteActuelle() > tailleBin){
                estCorrect = false;
                System.out.println("Trop grande !!!!!! " + bin);
            }
        }
        return estCorrect;
    }

    @Override
    public String toString() {
        String s =  "[" + nomProbleme + "]\n" +
                    "Données : " +
                    "nItems = " + items.size() +
                    ", tailleBin = " + tailleBin + "\n";
        /*s += "items : ";
        for(Item i : items){
            s += i.getTaille() + " ";
        }
        s+="\n"*/
        s += "Borne inférieuere : " + this.getBorneInferieure() + "\n";
        return s;
    }

}

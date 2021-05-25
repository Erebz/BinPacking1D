package core;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinPacking {
    private String nomProbleme;

    private List<Item> items;
    private List<Bin> bins;

    private int tailleBin;

    public BinPacking(){
        nomProbleme = "Problème vide";
        tailleBin = 0;
        items = new ArrayList<Item>();
        bins = new ArrayList<Bin>();
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
                this.tailleBin = Integer.parseInt(datas[0]);
            }
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                int taille = Integer.parseInt(line);
                Item i = new Item(taille);
                items.add(i);
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

    private void trierItemsDecroissant(){
        Collections.sort(items, Collections.reverseOrder());
    }

    private void trierItemsCroissant(){
        Collections.sort(items);
    }

    public int getNbBins(){
        return bins.size();
    }

    private void reset() {
        bins.clear();
    }


    //Algorithmes de résolution
    public void firstFitDecreasing(){
        this.reset();
        this.trierItemsDecroissant();
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
    }


    public void linearProgrammingResolution(){
        this.reset();
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
        //s += "nbBins = " + bins.size() + "\n";
        /*for(Bin b : bins){
            s += "- [" + b + "]\n";
        }*/
        return s;
    }
}

package core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        File dossierData = new File("./data");
        StrategieMultiple voisinageBoth = new StrategieMultiple();
        voisinageBoth.ajouterStrategie(new StrategieDeplacer());
        voisinageBoth.ajouterStrategie(new StrategieEchanger());
        StrategieVoisinage voisinageMove = new StrategieDeplacer();
        StrategieVoisinage voisinageSwap = new StrategieEchanger();
        PackingSolution solution;
        long t, dt;

        try {
            File f = new File("./analyseTabou.csv");
            File f2 = new File("./analyseRecuit.csv");
            File resultats = new File("./Résultat final.txt");
            f.createNewFile();
            f2.createNewFile();
            resultats.createNewFile();
            PrintStream out = new PrintStream(new FileOutputStream("./Résultat final.txt"));
            System.out.println("Les résultats seront écrits dans le fichier 'Résultat final.txt' à la racine.");
            System.setOut(out);

            for(File problems : dossierData.listFiles()){
                String name = problems.getName();
                BinPacking bp = new BinPacking(name);
                bp.setVoisinage(voisinageBoth);
                System.out.print(bp);

                System.out.print("> First Fit Decreasing ... ");
                t = System.currentTimeMillis();
                solution = bp.firstFitDecreasing();
                dt = System.currentTimeMillis() - t;
                System.out.println(solution.getNbBins() + " (" + dt + "ms)");
                System.out.println(solution.getBins()+"\n");

                System.out.print("> Recuit Simulé (Un par Bin)... ");
                t = System.currentTimeMillis();
                solution = bp.recuitSimule(bp.genererUnBinParItem(), 5000, 200, 100, 0.95);
                dt = System.currentTimeMillis() - t;
                System.out.println(solution.getNbBins() + " (" + dt + "ms)");
                System.out.println(solution.getBins()+"\n");

                System.out.print("> Recuit Simulé (FirstFitRandom)... ");
                t = System.currentTimeMillis();
                solution = bp.recuitSimule(bp.firstFitRandom(), 5000, 200, 100, 0.95);
                dt = System.currentTimeMillis() - t;
                System.out.println(solution.getNbBins() + " (" + dt + "ms)");
                System.out.println(solution.getBins()+"\n");

                System.out.print("> Méthode Tabou (Un par Bin)... ");
                t = System.currentTimeMillis();
                solution = bp.methodeTabou(bp.genererUnBinParItem(), 1000, 1000, 300);
                dt = System.currentTimeMillis() - t;
                System.out.println(solution.getNbBins() + " (" + dt + "ms)");
                System.out.println(solution.getBins()+"\n");

                System.out.print("> Méthode Tabou (FirstFitRandom)... ");
                t = System.currentTimeMillis();
                solution = bp.recuitSimule(bp.firstFitRandom(), 5000, 200, 100, 0.95);
                dt = System.currentTimeMillis() - t;
                System.out.println(solution.getNbBins() + " (" + dt + "ms)");
                System.out.println(solution.getBins()+"\n");
                System.out.println("=======================================");
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
}

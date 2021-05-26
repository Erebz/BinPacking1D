package core;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        /*File dossierData = new File("./data");
        for(File f : dossierData.listFiles()){
            String data = f.getName();
            BinPacking bp = new BinPacking(data);
            System.out.print(bp);
            System.out.print("> FirstFitDecreasing ... ");
            bp.firstFitDecreasing();
            System.out.println(bp.getNbBins() + "bins");
            System.out.print("> Résolution programmation linéaire ... ");
            bp.linearProgrammingResolution();
            System.out.println(bp.getNbBins() + "bins");
            System.out.println("\n");
        }*/

        String data = "binpack1d_05.txt";
        BinPacking bp = new BinPacking(data);
        StrategieMultiple voisinage = new StrategieMultiple();
        voisinage.ajouterStrategie(new StrategieDeplacer());
        voisinage.ajouterStrategie(new StrategieEchanger());
        bp.setVoisinage(voisinage);
        PackingSolution solution;
        System.out.print(bp);
        System.out.print("> FirstFitDecreasing ... ");
        solution = bp.firstFitDecreasing();
        System.out.println(solution.getNbBins() + "bins");
        /*System.out.print("> Résolution programmation linéaire ... ");
        solution = bp.linearProgrammingResolution();
        System.out.println(solution.getNbBins() + "bins");*/
        System.out.print("> Recuit Simulé ... ");
        solution = bp.recuitSimule(bp.firstFitRandom(), 4000, 100, 100, 0.75d);
        System.out.println(solution.getNbBins() + "bins");
        System.out.println(">> correct ? " + bp.estValide(solution));
        System.out.println("\n");
    }
}

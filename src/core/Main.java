package core;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File dossierData = new File("./data");
        for(File f : dossierData.listFiles()){
            String data = f.getName();
            BinPacking bp = new BinPacking(data);
            System.out.print(bp);
            System.out.print("> FirstFitDecreasing ... ");
            bp.firstFitDecreasing();
            System.out.print(bp.getNbBins() + "bins");
            System.out.println("\n");
        }
    }
}

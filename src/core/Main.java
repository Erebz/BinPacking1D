package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        int nbBins;

        try {
            File file = new File("./resultatsBin.csv");
            file.createNewFile();
            file = new File("./resultatsTemps.csv");
            file.createNewFile();
            file = new File("./analyseRecuit.csv");
            file.createNewFile();
            file = new File("./analyseTabou.csv");
            file.createNewFile();
            //FileWriter writer1 = new FileWriter("./resultatsBin.csv");
            //FileWriter writer2 = new FileWriter("./resultatsTemps.csv");
            //FileWriter writer3 = new FileWriter("./analyseRecuit.csv");
            //FileWriter writer4 = new FileWriter("./analyseTabou.csv");
            String header = ";FFD;RS (FFR);RS (1PB);MT (FFR);MT (1PB)";
            String header2 = ";";
            //writer1.write(header+"\n");
            //writer2.write(header+"\n");
            //writer3.write(";0.8;0.85;0.9;0.95;0.99\n");

            //for(File f : dossierData.listFiles()){
                String data = "binpack1d_01.txt";
                //String data = f.getName();
                //writer1.write(data+";");
                //writer2.write(data+";");
                BinPacking bp = new BinPacking(data);
                bp.setVoisinage(voisinageBoth);
                //bp.setVoisinage(voisinageMove);
                //bp.setVoisinage(voisinageSwap);
                //System.out.print(bp);

                //System.out.print("> FirstFitDecreasing ... ");
                //t = System.currentTimeMillis();
                //solution = bp.firstFitDecreasing();
                //dt = System.currentTimeMillis()-t;
                //nbBins = solution.getNbBins();
                //System.out.print(nbBins + "bins ");
                //System.out.println("(" + dt +"ms)");
                //writer1.write(nbBins+";");
                //writer2.write(dt+";");
                //System.out.print("> Résolution programmation linéaire ... ");
                //solution = bp.linearProgrammingResolution();
                //System.out.println(solution.getNbBins() + "bins");

            System.out.println(bp.getBorneInferieure());
            solution = bp.firstFitDecreasing();
            System.out.println(solution.getNbBins());
            //double moyBins = 0, moyFit = 0;
            //for(int i = 0 ; i < 10; i++){
            solution = bp.recuitSimule(bp.genererUnBinParItem(), 16000, 452, 155, 0.99);
            System.out.println(solution.getNbBins());
                //moyBins += solution.getNbBins();
                //moyFit += solution.fitness();
            //}
            //moyBins = moyBins / 10;
            //moyFit = moyFit / 10;
            //System.out.println("moyBin :" + moyBins);
            //System.out.println("moyFit :" + moyFit);

            /*solution = bp.methodeTabou(bp.genererUnBinParItem(), 100, 3000, 10000);
            dt = System.currentTimeMillis()-t;
            System.out.println(solution.getNbBins());
            System.out.println(dt);*/
            //writer1.write(nbBins+";");
                //writer2.write(dt+";");

                /*System.out.print("> Recuit Simulé (1PB)... ");
                t = System.currentTimeMillis();
                solution = bp.recuitSimule(bp.genererUnBinParItem(), 200, 60, 150, 0.95d);
                dt = System.currentTimeMillis()-t;
                nbBins = solution.getNbBins();
                System.out.print(nbBins + "bins ");
                System.out.println("(" + dt +"ms)");
                writer1.write(nbBins+";");
                writer2.write(dt+";");*/

                //System.out.print("> Méthode Tabou (FFR)... ");
                /*for(int i = 1; i <= 100; i++) {
                    t = System.currentTimeMillis();
                    solution = bp.methodeTabou(bp.genererUnBinParItem(), 10, 100, i*100);
                    dt = System.currentTimeMillis() - t;
                    nbBins = solution.getNbBins();
                    System.out.print(nbBins + "bins ");
                    System.out.println("(" + dt + "ms)");
                }*/
                //writer1.write(nbBins+";");
                //writer2.write(dt+";");

                /*System.out.print("> Méthode Tabou (1PB)... ");
                t = System.currentTimeMillis();
                solution = bp.methodeTabou(bp.genererUnBinParItem(), 10, 3000, 500);
                dt = System.currentTimeMillis()-t;
                nbBins = solution.getNbBins();
                System.out.print(nbBins + "bins ");
                System.out.println("(" + dt +"ms)");
                writer1.write(nbBins+";\n");
                writer2.write(dt+";\n");*/
                System.out.println("\n");
            //}
            //writer1.close();
            //writer2.close();
            //writer3.close();
            //writer4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String data = "binpack1d_06.txt";
        BinPacking bp = new BinPacking(data);
        StrategieMultiple voisinage = new StrategieMultiple();
        voisinage.ajouterStrategie(new StrategieDeplacer());
        voisinage.ajouterStrategie(new StrategieEchanger());
        //StrategieVoisinage voisinage = new StrategieDeplacer();
        //StrategieVoisinage voisinage = new StrategieEchanger();
        bp.setVoisinage(voisinage);
        PackingSolution solution;
        System.out.print(bp);
        System.out.print("> FirstFitDecreasing ... ");
        solution = bp.firstFitDecreasing();
        System.out.println(solution.getNbBins() + "bins");
        //System.out.print("> Résolution programmation linéaire ... ");
        //solution = bp.linearProgrammingResolution();
        //System.out.println(solution.getNbBins() + "bins");
        System.out.print("> Recuit Simulé ... ");
        solution = bp.recuitSimule(bp.firstFitRandom(), 100, 50, 100, 0.95d);
        System.out.println(solution.getNbBins() + "bins");
        //System.out.println(">> correct ? " + bp.estValide(solution));
        System.out.print("> Méthode Tabou ... ");
        solution = bp.methodeTabou(bp.genererUnBinParItem(), 10, 10000, 500);
        System.out.println(solution.getNbBins() + "bins");
        //System.out.println(">> correct ? " + bp.estValide(solution));
        System.out.println("\n");*/

        /*for(File f : dossierData.listFiles()){
            System.out.print(f.getName()+"\t");
        }
        System.out.println("");
        for(File f : dossierData.listFiles()){
            String data = f.getName();
            BinPacking bp = new BinPacking(data);
            //solution = bp.firstFitDecreasing();
            System.out.print(bp.getBorneInferieure() + "\t");
            t = System.currentTimeMillis();
            solution = bp.methodeTabou(bp.genererUnBinParItem(), 10, 3000, 500);
            dt = System.currentTimeMillis()-t;
        }*/
    }
}

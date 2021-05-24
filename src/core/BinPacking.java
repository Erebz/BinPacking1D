package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinPacking {
    private List<Item> items;
    private List<Bin> bins;

    private int tailleBin;

    public BinPacking(){
        tailleBin = 0;
        items = new ArrayList<Item>();
        bins = new ArrayList<Bin>();
    }

    public BinPacking(String filename){
        this();
        this.initFromFile(filename);
    }

    private void initFromFile(String filename) {
        try {
            File file = new File(filename);
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




    //Algorithmes de résolution
    public void firstFitDecreasing(){
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



    @Override
    public String toString() {
        String s =  "Données : " +
                    "nItems = " + items.size() +
                    ", tailleBin = " + tailleBin +
                    ", nBins = " + bins.size() + "\n";
        s += "items : ";
        for(Item i : items){
            s += i.getTaille() + " ";
        }
        s += "\nBorne inférieuere : " + this.getBorneInferieure();
        return s;
    }
}

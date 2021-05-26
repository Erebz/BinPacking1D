package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackingSolution {
    private List<Bin> bins;

    public PackingSolution(){
        bins = new ArrayList<Bin>();
    }

    public PackingSolution(PackingSolution ps){
        this();
        for(Bin bin : ps.bins){
            Bin binCopie = new Bin(bin);
            this.ajouterBin(binCopie);
        }
    }

    public void reset() { bins.clear(); }

    public int getNbBins() {
        return bins.size();
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void ajouterBin(Bin bin) {
        bins.add(bin);
    }

    public int fitness(){
        int fitness = 0;
        for(Bin bin : bins){
            fitness += Math.pow(bin.capaciteActuelle(), 2);
        }
        return fitness;
    }

    public void deplacerItem(int idOrigine, int idArrivee, Item item){
        Bin origine = bins.get(idOrigine);
        Bin arrivee = bins.get(idArrivee);
        origine.retirerItem(item);
        arrivee.ajouterItem(item);
    }

    public void echangerItems(int idBin1, int idBin2, Item item1, Item item2){
        Bin b1 = bins.get(idBin1);
        Bin b2 = bins.get(idBin2);
        b1.retirerItem(item1);
        b2.retirerItem(item2);
        b1.ajouterItem(item2);
        b2.ajouterItem(item1);
    }

}

package core;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    private List<Item> items;
    private int taille;

    public Bin(int taille){
        this.items = new ArrayList<Item>();
        this.taille = taille;
    }

    public Bin(Bin bin) {
        this(bin.taille);
        for(Item i : bin.items){
            this.ajouterItem(i);
        }
    }

    public void ajouterItem(Item i){
        this.items.add(i);
    }

    public void retirerItem(Item i){
        this.items.remove(i);
    }

    public void retirerItem(int i){
        this.items.remove(i);
    }

    public boolean estPleine(){
        return this.capaciteActuelle() >= this.taille;
    }

    public boolean contient(Item i){
        return items.contains(i);
    }

    private int capaciteRestante(){
        return this.taille - this.capaciteActuelle();
    }

    public boolean peutAccueillir(Item i){
        return i.getTaille() <= capaciteRestante();
    }

    public int capaciteActuelle() {
        int taille = 0;
        for(Item i : this.items){
            taille += i.getTaille();
        }
        return taille;
    }

    public int getNbItems() {
        return items.size();
    }

    public List<Item> getItems(){
        return this.items;
    }

    @Override
    public String toString() {
        return items.size() + " items, ("+ capaciteActuelle() + "/" + taille +")";
    }
}

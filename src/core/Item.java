package core;

public class Item implements Comparable<Item>{
    private int taille;

    public Item(int taille){
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public int compareTo(Item i) {
        return this.taille - i.getTaille();
    }
}

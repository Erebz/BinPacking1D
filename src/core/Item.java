package core;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(taille);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Item)) return false;
        if(this == o) return true;
        return this.taille == ((Item) o).getTaille();
    }
}

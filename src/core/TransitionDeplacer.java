package core;

public class TransitionDeplacer implements Transition {

    private Bin depart;
    private Bin arrivee;
    private Item item;

    public TransitionDeplacer(Bin depart, Bin arrivee, Item item){
        this.depart = depart;
        this.arrivee = arrivee;
        this.item = item;
    }

    public TransitionDeplacer getInverse(){
        Bin newDepart = new Bin(depart);
        Bin newArrivee = new Bin(arrivee);
        newDepart.retirerItem(item);
        newArrivee.ajouterItem(item);
        return new TransitionDeplacer(newArrivee, newDepart, item);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof TransitionDeplacer)) return false;
        if(this == o) return true;
        TransitionDeplacer t = (TransitionDeplacer) o;
        return depart.equals(t.depart) && arrivee.equals(t.arrivee) && item.equals(t.item);
    }
}

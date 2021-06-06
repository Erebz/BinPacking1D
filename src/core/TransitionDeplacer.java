package core;

public class TransitionDeplacer implements Transition {

    private Bin depart;
    private Bin arrivee;
    private Item item;
    private Bin newDepart;
    private Bin newArrivee;

    public TransitionDeplacer(Bin depart, Bin arrivee, Item item, Bin newDepart, Bin newArrivee){
        this.depart = depart;
        this.arrivee = arrivee;
        this.item = item;
        this.newDepart = newDepart;
        this.newArrivee = newArrivee;
    }

    public TransitionDeplacer getInverse(){
        return new TransitionDeplacer(newArrivee, newDepart, item, arrivee, depart);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof TransitionDeplacer)) return false;
        if(this == o) return true;
        TransitionDeplacer t = (TransitionDeplacer) o;
        return depart.equals(t.depart) && arrivee.equals(t.arrivee) && item.equals(t.item)
                && newDepart.equals(t.newDepart) && newArrivee.equals(t.newArrivee);
    }
}

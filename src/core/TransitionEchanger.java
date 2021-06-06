package core;

public class TransitionEchanger implements Transition{

    private Bin depart;
    private Bin arrivee;
    private Item itemDepart;
    private Item itemArrivee;
    private Bin newDepart;
    private Bin newArrivee;

    public TransitionEchanger(Bin depart, Bin arrivee, Item itemDepart, Item itemArrivee, Bin newDepart, Bin newArrivee){
        this.depart = depart;
        this.arrivee = arrivee;
        this.itemDepart = itemDepart;
        this.itemArrivee = itemArrivee;
        this.newDepart = newDepart;
        this.newArrivee = newArrivee;
    }

    @Override
    public Transition getInverse() {
        return new TransitionEchanger(newArrivee, newArrivee, itemArrivee, itemDepart, arrivee, depart);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof TransitionEchanger)) return false;
        if(this == o) return true;
        TransitionEchanger t = (TransitionEchanger) o;
        boolean sens1 = depart.equals(t.depart) && arrivee.equals(t.arrivee)
                && itemArrivee.equals(t.itemArrivee) && itemDepart.equals(t.itemDepart)
                && newDepart.equals(t.newDepart) && newArrivee.equals(t.newArrivee);
        boolean sens2 = arrivee.equals(t.depart) && depart.equals(t.arrivee)
                && itemDepart.equals(t.itemArrivee) && itemArrivee.equals(t.itemDepart)
                && newDepart.equals(t.newArrivee) && newArrivee.equals(t.newDepart);
        return sens1 || sens2;
    }
}

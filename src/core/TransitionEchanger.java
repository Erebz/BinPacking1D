package core;

public class TransitionEchanger implements Transition{

    private Bin depart;
    private Bin arrivee;
    private Item itemDepart;
    private Item itemArrivee;

    public TransitionEchanger( Bin depart, Bin arrivee, Item itemDepart, Item itemArrivee ){
        this.depart = depart;
        this.arrivee = arrivee;
        this.itemDepart = itemDepart;
        this.itemArrivee = itemArrivee;
    }

    @Override
    public Transition getInverse() {
        Bin newDepart = new Bin(depart);
        Bin newArrivee = new Bin(arrivee);
        newDepart.retirerItem(itemDepart);
        newDepart.ajouterItem(itemArrivee);
        newArrivee.retirerItem(itemArrivee);
        newArrivee.ajouterItem(itemDepart);
        return new TransitionEchanger(newArrivee, newDepart, itemArrivee, itemDepart);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof TransitionEchanger)) return false;
        if(this == o) return true;
        TransitionEchanger t = (TransitionEchanger) o;
        boolean sens1 = depart.equals(t.depart) && arrivee.equals(t.arrivee) && itemArrivee.equals(t.itemArrivee) && itemDepart.equals(t.itemDepart);
        boolean sens2 = arrivee.equals(t.depart) && depart.equals(t.arrivee) && itemDepart.equals(t.itemArrivee) && itemArrivee.equals(t.itemDepart);
        return sens1 || sens2;
    }
}

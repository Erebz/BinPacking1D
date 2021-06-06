package core;

import java.util.*;

public class StrategieDeplacer implements StrategieVoisinage{
    @Override
    public Map<Transition, PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        Map<Transition, PackingSolution> voisins = new HashMap<>();
        int nbVoisins = 0;
        List<Bin> bins = x.getBins();
        Collections.shuffle(bins);
        //Déplacer un Item
        for (int i = 0; i < bins.size(); i++) {
            Bin b1 = bins.get(i);
            List<Item> items = b1.getItems();
            Collections.shuffle(items);
            for (Item item : items) {
                boolean moved = false;
                for (int j = 0; j < bins.size() && !moved; j++) {
                    Bin b2 = bins.get(j);
                    if (j != i && b2.peutAccueillir(item)) {
                        PackingSolution voisin = new PackingSolution(x);
                        Bin[] newBins = voisin.deplacerItem(i, j, item);
                        TransitionDeplacer t = new TransitionDeplacer(b1,b2,item,newBins[0],newBins[1]);
                        voisins.put(t, voisin);
                        nbVoisins ++;
                        if(nbVoisins >= tailleVoisinage) return voisins;
                        moved = true;
                    }
                }
                if(!moved){
                    int taille = bins.get(0).getTaille();
                    Bin b2 = new Bin(taille);
                    PackingSolution voisin = new PackingSolution(x);
                    voisin.ajouterBin(b2);
                    Bin[] newBins = voisin.deplacerItem(i, voisin.getNbBins()-1, item);
                    TransitionDeplacer t = new TransitionDeplacer(b1,b2,item,newBins[0],newBins[1]);
                    voisins.put(t, voisin);
                    nbVoisins ++;
                    if(nbVoisins >= tailleVoisinage) return voisins;
                }
            }
        }
        return voisins;
    }
}

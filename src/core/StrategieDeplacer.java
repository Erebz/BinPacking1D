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
                for (int j = 0; j < bins.size(); j++) {
                    Bin b2 = bins.get(j);
                    if (j != i && b2.peutAccueillir(item)) {
                        PackingSolution voisin = new PackingSolution(x);
                        voisin.deplacerItem(i, j, item);
                        TransitionDeplacer t = new TransitionDeplacer(b1,b2,item);
                        voisins.put(t, voisin);
                        nbVoisins ++;
                        if(nbVoisins >= tailleVoisinage) return voisins;
                    }
                }
            }
        }
        return voisins;
    }
}

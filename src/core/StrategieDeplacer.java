package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrategieDeplacer implements StrategieVoisinage{
    @Override
    public List<PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        List<PackingSolution> voisins = new ArrayList<PackingSolution>();
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
                        voisins.add(voisin);
                        nbVoisins ++;
                        if(nbVoisins >= tailleVoisinage) return voisins;
                    }
                }
            }
        }
        if(nbVoisins == 0) voisins.add(x);
        return voisins;
    }
}

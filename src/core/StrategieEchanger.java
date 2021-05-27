package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrategieEchanger implements StrategieVoisinage{
    @Override
    public List<PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        List<PackingSolution> voisins = new ArrayList<PackingSolution>();
        int nbVoisins = 0;
        List<Bin> bins = x.getBins();
        Collections.shuffle(bins);
        for (int i = 0; i < bins.size(); i++) {
            Bin b1 = bins.get(i);
            List<Item> items = b1.getItems();
            Collections.shuffle(items);
            for (Item item : items) {
                for (int j = 0; j < bins.size(); j++) {
                    Bin b2 = bins.get(j);
                    if (j != i && b2.peutAccueillir(item)) {
                        List<Item> items2 = b2.getItems();
                        Collections.shuffle(items2);
                        for (Item item2 : items2){
                            if (b2.peutAccueillirSansItem(item,item2) && b1.peutAccueillirSansItem(item2,item)) {
                                PackingSolution voisin = new PackingSolution(x);
                                voisin.echangerItems(i,j,item,item2);
                                TransitionEchanger t = new TransitionEchanger(b1,b2,item,item2);
                                voisins.put(t, voisin);
                                nbVoisins ++;
                                if(nbVoisins >= tailleVoisinage) return voisins;
                            }
                        }
                    }
                }
            }
        }
        return voisins;
    }
}

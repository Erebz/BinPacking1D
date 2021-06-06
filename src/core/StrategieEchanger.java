package core;

import java.util.*;

public class StrategieEchanger implements StrategieVoisinage{
    @Override
    public Map<Transition, PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        Map<Transition, PackingSolution> voisins = new HashMap<>();
        int nbVoisins = 0;
        List<Bin> bins = x.getBins();
        Collections.shuffle(bins);
        for (int i = 0; i < bins.size(); i++) {
            Bin b1 = bins.get(i);
            List<Item> items = b1.getItems();
            Collections.shuffle(items);
            for (Item item : items) {
                boolean swaped = false;
                for (int j = 0; j < bins.size() && !swaped; j++) {
                    Bin b2 = bins.get(j);
                    if (j != i) {
                        List<Item> items2 = b2.getItems();
                        Collections.shuffle(items2);
                        for (Item item2 : items2){
                            if(!swaped){
                                if (b2.peutEchanger(item2,item) && b1.peutEchanger(item,item2)) {
                                    PackingSolution voisin = new PackingSolution(x);
                                    Bin[] newBins = voisin.echangerItems(i,j,item,item2);
                                    TransitionEchanger t = new TransitionEchanger(b1,b2,item,item2,newBins[0],newBins[1]);
                                    voisins.put(t, voisin);
                                    nbVoisins ++;
                                    if(nbVoisins >= tailleVoisinage) return voisins;
                                    swaped = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(voisins.size() == 0){
            System.out.println("???");
        }
        return voisins;
    }
}

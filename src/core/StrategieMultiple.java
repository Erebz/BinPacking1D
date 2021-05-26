package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StrategieMultiple implements StrategieVoisinage{

    List<StrategieVoisinage> strategies;

    public StrategieMultiple(){
        this.strategies = new ArrayList<>();
    }

    public void ajouterStrategie(StrategieVoisinage strategie){
        this.strategies.add(strategie);
    }

    @Override
    public List<PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        Random r = new Random();
        int nbStrat = strategies.size();
        if(nbStrat == 0) return new ArrayList<PackingSolution>();

        if(tailleVoisinage <= nbStrat){
            int idStrategie = r.nextInt(strategies.size());
            return strategies.get(idStrategie).getVoisinage(x, tailleVoisinage);
        }else{
            List<PackingSolution> voisins = new ArrayList<>();
            int tailleSousVoisinage = (int) Math.ceil((double) tailleVoisinage / nbStrat);
            for (int i = 0; i<nbStrat-1; i++){
                List<PackingSolution> sousVoisinage = strategies.get(i).getVoisinage(x, tailleSousVoisinage);
                voisins.addAll(sousVoisinage);
            }
            List<PackingSolution> sousVoisinage = strategies.get(nbStrat).getVoisinage(x, (tailleVoisinage % nbStrat));
            voisins.addAll(sousVoisinage);

            return voisins;
        }
    }
}

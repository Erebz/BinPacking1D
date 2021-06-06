package core;

import java.util.*;

public class StrategieMultiple implements StrategieVoisinage{

    List<StrategieVoisinage> strategies;

    public StrategieMultiple(){
        this.strategies = new ArrayList<>();
    }

    public void ajouterStrategie(StrategieVoisinage strategie){
        this.strategies.add(strategie);
    }

    @Override
    public Map<Transition, PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage) {
        Random r = new Random();
        int nbStrat = strategies.size();
        if(nbStrat == 0) return new HashMap<>();

        if(tailleVoisinage <= nbStrat){
            int idStrategie = r.nextInt(strategies.size());
            return strategies.get(idStrategie).getVoisinage(x, tailleVoisinage);
        }else{
            Map<Transition, PackingSolution> voisins = new HashMap<>();
            int tailleSousVoisinage = (int) Math.ceil((double) tailleVoisinage / nbStrat);
            for (int i = 0; i<nbStrat; i++){
                Map<Transition, PackingSolution> sousVoisinage = strategies.get(i).getVoisinage(x, tailleSousVoisinage);
                voisins.putAll(sousVoisinage);
            }
            //Map<Transition, PackingSolution> sousVoisinage = strategies.get(nbStrat-1).getVoisinage(x, (tailleVoisinage % nbStrat));
            //voisins.putAll(sousVoisinage);

            return voisins;
        }
    }
}

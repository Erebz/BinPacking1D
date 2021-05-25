package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackingSolution {
    private List<Bin> bins;

    public PackingSolution(){
        bins = new ArrayList<Bin>();
    }

    public void reset() { bins.clear(); }

    public int getNbBins() {
        return bins.size();
    }

    public List<Bin> getBins() {
        return bins;
    }

    public void ajouterBin(Bin bin) {
        bins.add(bin);
    }
}

package core;

import java.util.List;

public interface StrategieVoisinage {
    public abstract List<PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage);
}

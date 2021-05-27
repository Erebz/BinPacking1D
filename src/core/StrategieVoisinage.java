package core;

import java.util.List;
import java.util.Map;

public interface StrategieVoisinage {
    public abstract Map<Transition, PackingSolution> getVoisinage(PackingSolution x, int tailleVoisinage);
}

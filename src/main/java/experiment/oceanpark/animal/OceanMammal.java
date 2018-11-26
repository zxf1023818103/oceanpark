package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class OceanMammal extends Animal {
    private static int currentId = 20000;
    public OceanMammal() {
        setId(++currentId);
    }
}

package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class Reptile extends Animal {
    private static int currentId = 30000;
    public Reptile() {
        setId(++currentId);
    }
}

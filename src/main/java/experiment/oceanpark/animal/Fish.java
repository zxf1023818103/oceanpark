package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class Fish extends Animal {
    private static int currentId = 10000;
    public Fish() {
        setId(++currentId);
    }
}

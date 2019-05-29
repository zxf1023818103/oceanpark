package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class OceanMammal extends Animal implements CanSwim {
    private static int currentId = 20000;
    public OceanMammal() {
        setId(++currentId);
    }

    @Override
    public void swim() {
        System.out.println( getKlass() + "游过……");
    }
}

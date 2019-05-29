package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class Fish extends Animal implements CanSwim {
    private static int currentId = 10000;
    public Fish() {
        setId(++currentId);
    }

    @Override
    public void swim() {
        System.out.println( getKlass() + "游过……");
    }
}

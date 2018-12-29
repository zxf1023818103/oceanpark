package experiment.oceanpark.animal;

import experiment.oceanpark.Animal;

public abstract class Bird extends Animal implements CanFly {

    private static int currentId = 40000;

    public Bird() {
        setId(++currentId);
    }

    @Override
    public void fly() {
        System.out.println(getKlass() + "飞过……");
    }

}

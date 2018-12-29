package experiment.oceanpark.staff;

import experiment.oceanpark.Staff;
import experiment.oceanpark.animal.CanSwim;

public class Mermaid extends Staff implements CanSwim {

    private static int currentId = 90000;

    public Mermaid() {
        setId(++currentId);
    }

    @Override
    public void swim() {
        System.out.println("美人鱼游过……");
    }
}

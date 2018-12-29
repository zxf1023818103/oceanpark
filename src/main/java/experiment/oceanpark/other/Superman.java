package experiment.oceanpark.other;

import experiment.oceanpark.Actor;
import experiment.oceanpark.animal.CanFly;

public class Superman extends Actor implements CanFly {

    @Override
    public void fly() {
        System.out.println("空中飞人飞过……");
    }

}

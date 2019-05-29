package experiment.oceanpark.other;

import experiment.oceanpark.Actor;
import experiment.oceanpark.animal.CanFly;

public class Airship extends Actor implements CanFly {

    @Override
    public void fly() {
        System.out.println("飞艇飞过……");
    }
}

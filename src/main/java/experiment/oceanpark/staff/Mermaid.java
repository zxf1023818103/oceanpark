package experiment.oceanpark.staff;

import experiment.oceanpark.Staff;

public class Mermaid extends Staff {

    private static int currentId = 90000;

    public Mermaid() {
        setId(++currentId);
    }

}

package experiment.oceanpark;

import com.sun.istack.internal.NotNull;

public class Consumer implements Comparable<Consumer> {

    private String name;

    private int id;

    private boolean gender;

    private static int currentId = 10001;

    public Consumer(@NotNull String name, boolean gender) {
        id = currentId++;
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean getGender() {
        return gender;
    }

    @Override
    public int compareTo(Consumer o) {
        boolean thisIsVipConsumer = this instanceof VipConsumer;
        boolean oIsVipConsumer = o instanceof VipConsumer;
        if (thisIsVipConsumer) {
            if (oIsVipConsumer)
                return ((VipConsumer)this).getNumber() - ((VipConsumer)o).getNumber();
            else
                return -1;
        }
        else {
            if (oIsVipConsumer) {
                return 1;
            }
            else return 0;
        }
    }
}

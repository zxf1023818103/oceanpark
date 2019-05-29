package experiment.oceanpark;

public abstract class Staff extends Actor {
    private String name;
    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

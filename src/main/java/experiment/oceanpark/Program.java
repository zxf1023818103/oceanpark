package experiment.oceanpark;

public abstract class Program extends Actor {
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

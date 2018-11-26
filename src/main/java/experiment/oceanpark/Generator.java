package experiment.oceanpark;

import com.sun.istack.internal.NotNull;
import experiment.oceanpark.animal.fish.ClownFish;
import experiment.oceanpark.animal.fish.FlyingFish;
import experiment.oceanpark.animal.fish.Shark;
import experiment.oceanpark.animal.oceanmammal.Dolphin;
import experiment.oceanpark.animal.oceanmammal.SeaLion;
import experiment.oceanpark.animal.oceanmammal.Seal;
import experiment.oceanpark.animal.reptile.Crocodile;
import experiment.oceanpark.animal.reptile.Lizard;
import experiment.oceanpark.program.Interactivity;
import experiment.oceanpark.program.Performance;
import experiment.oceanpark.staff.Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class Generator {

    private static List<String> animalNames, staffNames, interactivityNames, performanceNames;

    private static final String ANIMAL_NAME_FILE = "animal_names.txt";

    private static final String STAFF_NAME_FILE = "staff_names.txt";

    private static final String INTERACTIVITY_NAME_FILE = "interactivity_names.txt";

    private static final String PERFORMANCE_NAME_FILE = "performance_names.txt";

    private static final String BASE_DIR = "/";

    private static Random random = new Random(System.nanoTime());

    private static List<String> loadNames(String filename) {
        Scanner scanner = new Scanner(Main.class.getResourceAsStream(BASE_DIR + filename));
        ArrayList<String> names = new ArrayList<String>();
        while (scanner.hasNext()) {
            names.add(scanner.next());
        }
        return names;
    }

    static {
        animalNames = loadNames(ANIMAL_NAME_FILE);
        staffNames = loadNames(STAFF_NAME_FILE);
        interactivityNames = loadNames(INTERACTIVITY_NAME_FILE);
        performanceNames = loadNames(PERFORMANCE_NAME_FILE);
    }

    public static Interactivity generateInteractivity() {
        Interactivity interactivity = new Interactivity();
        interactivity.setNeedPersons(random.nextInt(9) + 1);
        interactivity.setName(interactivityNames.get(random.nextInt(interactivityNames.size())));
        return interactivity;
    }

    public static List<Interactivity> generateInteractivities(int number) {
        List<Interactivity> interactivities = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            interactivities.add(generateInteractivity());
        }
        return interactivities;
    }

    public static Performance generatePerformance() {
        Performance performance = new Performance();
        performance.setName(performanceNames.get(random.nextInt(performanceNames.size())));
        return performance;
    }

    public static List<Performance> generatePerformances(int number) {
        List<Performance> performances = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            performances.add(generatePerformance());
        }
        return performances;
    }

    public static Trainer generateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setName(staffNames.get(random.nextInt(staffNames.size())));
        return trainer;
    }

    public static Animal generateAnimal(@NotNull AnimalEnum type) {
        Animal animal;
        switch (type) {
            case CLOWN_FISH: animal = new ClownFish(); break;
            case FLYING_FISH: animal = new FlyingFish(); break;
            case SHARK: animal = new Shark(); break;
            case DOLPHIN: animal = new Dolphin(); break;
            case SEAL: animal = new Seal(); break;
            case SEA_LION: animal = new SeaLion(); break;
            case CROCODILE: animal = new Crocodile(); break;
            case LIZARD: animal = new Lizard(); break;
            default: return null;
        }
        animal.setAge(random.nextInt(9) + 1);
        animal.setFeedingMinutes(random.nextInt(19) + 1);
        animal.setGender(random.nextInt(1));
        animal.setNickname(animalNames.get(random.nextInt(animalNames.size())));
        animal.setInteractivities(generateInteractivities(random.nextInt(3) + 1));
        animal.setPerformances(generatePerformances(random.nextInt(3) + 1));
        animal.setTrainer(generateTrainer());
        return animal;
    }

}

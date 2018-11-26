package experiment.oceanpark;

import com.sun.istack.internal.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.*;


public class Main {

    private static final AnimalEnum[] animalEnums = AnimalEnum.values();

    private static ArrayList<Animal> animals = new ArrayList<>();

    private static HashMap<Integer, Animal> animalMap = new HashMap<>();

    private static Random random = new Random(System.nanoTime());

    static {
        for (AnimalEnum animalEnum : animalEnums) {
            Animal animal = Generator.generateAnimal(animalEnum);
            assert animal != null;
            animalMap.put(animal.getId(), animal);
            animals.add(animal);
        }
        Collections.shuffle(animals);
    }

    private static void generateProgramList(@NotNull String fileName) throws FileNotFoundException {
        PrintStream out = new PrintStream(fileName);
        out.println("show prepare");
        out.println("entrance start");
        out.println("entrance end");
        for (Animal animal : animals) {
            out.println(animal.getId() + " start");
        }
        out.println("show end");
        out.close();
    }

    public static void main(String... args) {
        System.out.println("请输入节目单文件名：");
        Scanner stdinScanner = new Scanner(System.in);
        Scanner fileScanner;
        LocalTime time = LocalTime.of(8, 0);
        try {
            String fileName = stdinScanner.nextLine();
            generateProgramList(fileName);
            System.out.println("已生成节目单");
            fileScanner = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (!fileScanner.nextLine().isEmpty()) {
            System.out.println(time + "\t各部门开始准备");
        }
        else {
            System.out.println("格式错误");
            return;
        }

        int preparedAnimals = 0;

        while (preparedAnimals < animalMap.size()) {
            int id;
            while (true) {
                try {
                    id = stdinScanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    stdinScanner = new Scanner(System.in);
                } catch (Exception e) {
                    System.out.println("输入错误");
                    return;
                }
            }
            Animal animal = animalMap.get(id);
            if (animal == null)
                System.out.println("编号错误");
            else {
                time = time.plusMinutes(random.nextInt(10) + 1);
                System.out.println(time + "\t" + animal.getKlass() + "喂食完毕");
                preparedAnimals++;
            }
        }

        System.out.println(time + "\t所有的动物喂食完毕，美人鱼也准备完毕，可以入场");

        if (!fileScanner.nextLine().isEmpty()) {
            time = time.plusMinutes(random.nextInt(3) + 1);
            System.out.println(time + "\t观众开始入场");
        }
        else {
            System.out.println("格式错误");
            return;
        }

        if (!fileScanner.nextLine().isEmpty()) {
            time = time.plusMinutes(random.nextInt(6) + 5);
            System.out.println(time + "\t观众入场完毕，表演即将开始，请大家赶快坐好");
        }
        else {
            System.out.println("格式错误");
            return;
        }

        time = time.plusMinutes(random.nextInt(5) + 1);
        System.out.println(time + "\t各位观众，欢迎大家的到来，表演即将开始");

        String prev = null;
        int i = 1;
        while (fileScanner.hasNextLine()) {
            int id;
            Scanner lineScanner = new Scanner(fileScanner.nextLine());
            try {
                id = lineScanner.nextInt();
            } catch (Exception e) {
                continue;
            }
            Animal animal = animalMap.get(id);
            assert animal != null;
            if (prev != null) {
                time = time.plusMinutes(random.nextInt(5) + 1);
                System.out.println(prev + "表演结束，下一个" + animal);
            }
            time = time.plusMinutes(random.nextInt(5) + 1);
            System.out.println(String.format("%s\t第%d个%s", time, i++, animal));
            time = time.plusMinutes(random.nextInt(5) + 1);
            System.out.println(String.format("%s\t%s的表演正式开始，现在表演的是%s", time, animal.getNickname(), animal.getPerformances().get(0)));
            for (int j = 1; j < animal.getPerformances().size(); j++) {
                time = time.plusMinutes(random.nextInt(5) + 1);
                System.out.println(String.format("%s\t%s表演%s", time, animal.getNickname(), animal.getPerformances().get(j)));
            }
            time = time.plusMinutes(random.nextInt(5) + 1);
            System.out.println(String.format("%s\t%s与观众互动，现在征选%d位幸运观众跟%s%s", time, animal.getNickname(), animal.getInteractivities().get(0).getNeedPersons(), animal.getNickname(), animal.getInteractivities().get(0).getName()));
            for (int j = 1; j < animal.getInteractivities().size(); j++) {
                time = time.plusMinutes(random.nextInt(5) + 1);
                System.out.println(String.format("%s\t现在我们征选%d位幸运观众跟%s%s", time, animal.getInteractivities().get(j).getNeedPersons(), animal.getNickname(), animal.getInteractivities().get(j)));
            }
            prev = animal.getNickname();
        }
        time = time.plusMinutes(random.nextInt(5) + 1);
        System.out.println(time + "\t各位观众，今天的表演至此结束，请各位有序退场，欢迎大家下次光临。");
    }

}

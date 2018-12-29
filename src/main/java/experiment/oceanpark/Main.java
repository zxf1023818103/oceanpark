package experiment.oceanpark;

import com.sun.istack.internal.NotNull;
import experiment.oceanpark.animal.CanFly;
import experiment.oceanpark.animal.CanSwim;
import experiment.oceanpark.other.Airship;
import experiment.oceanpark.other.Superman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;


public class Main {

    private static final AnimalEnum[] animalEnums = AnimalEnum.values();

    private static List<Actor> actors = new ArrayList<>();

    private static HashMap<Integer, Animal> animalMap = new HashMap<>();

    private static HashMap<Integer, Consumer> consumerMap = new HashMap<>();

    private static Random random = new Random(System.nanoTime());

    private static LocalTime time = LocalTime.of(8, 0);

    private static ActorReaderWriter readerWriter;

    private static void log(String format, Object... args) {
        System.out.println(String.format(time + "\t" + format, args));
        time = time.plusMinutes(random.nextInt(10) + 1);
    }

    static {
        for (AnimalEnum animalEnum : animalEnums) {
            Animal animal = Generator.generateAnimal(animalEnum);
            assert animal != null;
            animalMap.put(animal.getId(), animal);
            actors.add(animal);
        }
        Collections.shuffle(actors);
    }

    private static void generateProgramList(@NotNull String fileName) throws FileNotFoundException {
        PrintStream out = new PrintStream(fileName);
        out.println("show prepare");
        out.println("entrance start");
        out.println("entrance end");
        for (Actor actor : actors) {
            out.println(actor.getId() + " start");
        }
        out.println("show end");
        out.close();
    }

    private static void generateConsumers() {
        for (int i = 0; i < 29; i++) {
            Consumer consumer = Generator.generateConsumer(true);
            consumerMap.put(consumer.getId(), consumer);
        }
        for (int i = 0; i < 70; i++) {
            Consumer consumer = Generator.generateConsumer(false);
            consumerMap.put(consumer.getId(), consumer);
        }
    }

    public static void main(String... args) {
        System.out.println("请输入节目单文件名：");
        Scanner stdinScanner = new Scanner(System.in);
        Scanner fileScanner;
        try {
            String fileName = stdinScanner.nextLine();
            generateProgramList(fileName);
            System.out.println("已生成节目单");
            fileScanner = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("请输入评分结果文件名：");
        String filename = stdinScanner.nextLine();
        readerWriter = new ActorReaderWriter(filename, actors);
        readerWriter.readActors();

        generateConsumers();

        System.out.println("1. 普通游客");
        System.out.println("2. 会员");
        System.out.print("请选择：");
        int option;
        Consumer consumer;
        while (true) {
            option = stdinScanner.nextInt();
            if (option == 2) {
                System.out.print("请输入您的会员编号：");
                int id;
                while (true) {
                    id = stdinScanner.nextInt();
                    consumer = consumerMap.get(id);
                    if (consumer instanceof VipConsumer)
                        break;
                    else
                        System.out.println("您输入的编号有误，请重新输入。");
                }
                System.out.printf("您好，%s先生，您是我们的第%d号会员，可享受会员价格，原票价150元，会员价120元，请输入您要购买的张数：", consumer.getName(), ((VipConsumer) consumer).getNumber());
                int ticketNumber = 0;
                while (true) {
                    ticketNumber = stdinScanner.nextInt();
                    if (ticketNumber <= 0)
                        System.out.print("请输入正整数：");
                    else break;
                }
                int fee = ticketNumber * 120;
                while (fee > ((VipConsumer) consumer).getBalance()) {
                    System.out.print("你的余额不足，请充值：");
                    int amount = stdinScanner.nextInt();
                    ((VipConsumer) consumer).setBalance(((VipConsumer) consumer).getBalance() + amount);
                }
                ((VipConsumer) consumer).setBalance(((VipConsumer) consumer).getBalance() - 120);
                break;
            } else if (option == 1) {
                System.out.print("请输入您的姓名：");
                String name;
                while (true) {
                    name = stdinScanner.nextLine();
                    if (name.length() == 0) {
                        System.out.print("您输入的姓名为空，请重新输入：");
                        name = stdinScanner.nextLine();
                    } else break;
                }
                consumer = new Consumer(name, random.nextInt() % 2 == 0);
                consumerMap.put(consumer.getId(), consumer);
                break;
            }
            System.out.print("输入的选项有误，请重新输入：");
            option = stdinScanner.nextInt();
        }

        System.out.println("正在出票，您可以从下方的出票口拿走票，再见。");

        Collections.sort(actors);

        actors.add(0, new Airship());
        actors.add(1, new Superman());

        Iterator<Actor> iterator = actors.iterator();
        Actor actor;
        if (iterator.hasNext())
            actor = iterator.next();
        else return;

        log("巡游表演马上要开始了，请各位游客就座");

        log("巡游表演马上开始，首先进行的空中表演");
        while (actor instanceof CanFly) {
            ((CanFly) actor).fly();
            if (iterator.hasNext()) {
                actor = iterator.next();
            }
            else return;
        }

        log("下面是游泳表演");

        while (actor instanceof CanSwim) {
            ((CanSwim) actor).swim();
            if (iterator.hasNext()) {
                actor = iterator.next();
            }
            else return;
        }

        log("下面是走路表演");

        while (true) {
            System.out.println(actor.getKlass() + "飞过……");
            if (iterator.hasNext())
                actor = iterator.next();
            else break;
        }

        actors.removeIf(actor1 -> !(actor1 instanceof Animal));

        log("下面是返场表演时间，由最受欢迎的十大明星出场");

        actors.sort(Comparator.comparingInt(Actor::getScore));

        for (int i = 0; i < 10; i++) {
            if (i == 0)
                System.out.println("第一个出场的是最受欢迎的" + actors.get(i).getKlass() + "……");
            else if (i == 1)
                System.out.println("接下来出场的是" + actors.get(i).getKlass() + "……");
            else
                System.out.println("第" + i + "个出场的是" + actors.get(i).getKlass() + "……");
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
                log(animal.getKlass() + "喂食完毕");
                preparedAnimals++;
            }
        }

        log("所有的动物喂食完毕，美人鱼也准备完毕，可以入场");

        if (!fileScanner.nextLine().isEmpty()) {
            log("观众开始入场");
        } else {
            System.out.println("格式错误");
            return;
        }

        if (!fileScanner.nextLine().isEmpty()) {
            log("观众入场完毕，表演即将开始，请大家赶快坐好");
        } else {
            System.out.println("格式错误");
            return;
        }

        log("各位观众，欢迎大家的到来，表演即将开始");

        stdinScanner.nextLine();

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
                System.out.println(prev + "表演结束，下一个" + animal);
            }
            log("第%d个%s", i++, animal);
            log("%s的表演正式开始，现在表演的是%s", animal.getNickname(), animal.getPerformances().get(0));
            for (int j = 1; j < animal.getPerformances().size(); j++) {
                log("%s表演%s", animal.getNickname(), animal.getPerformances().get(j));
            }
            for (int j = 0; j < animal.getInteractivities().size(); j++) {
                int nPerson = animal.getInteractivities().get(0).getNeedPersons();
                List<Consumer> consumers = new ArrayList<>();
                consumerMap.forEach((id1, consumer1) -> consumers.add(consumer1));
                Collections.shuffle(consumers);
                log("现在我们征选%d位幸运观众跟%s%s", nPerson, animal.getNickname(), animal.getInteractivities().get(j));
                for (int k = 0; k < nPerson; k++) {
                    consumer = consumers.get(k);
                    if (consumer instanceof VipConsumer)
                        System.out.print("会员");
                    System.out.printf("%s%s，请到前面来。", consumer.getName(), consumer.getGender() ? "先生" : "女士");
                }
                System.out.println();
                System.out.printf("现在有请各位会员给%s打分：", animal.getNickname());
                int score = 0;
                while (true) {
                    try {
                        score = stdinScanner.nextInt();
                    } catch (Exception e) {
                        stdinScanner = new Scanner(System.in);
                    }
                    if (score < 1 || score > 10) {
                        System.out.print("分数为1-10，请重新输入：");
                        score = stdinScanner.nextInt();
                    }
                    else break;
                }
                animal.setScore(score);
            }
            prev = animal.getNickname();
        }
        readerWriter.writeActors();
        log("各位观众，今天的表演至此结束，请各位有序退场，欢迎大家下次光临。");
    }

}

package experiment.oceanpark;

import experiment.oceanpark.animal.fish.ClownFish;
import experiment.oceanpark.animal.fish.FlyingFish;
import experiment.oceanpark.animal.fish.Shark;
import experiment.oceanpark.animal.oceanmammal.Dolphin;
import experiment.oceanpark.animal.oceanmammal.SeaLion;
import experiment.oceanpark.animal.oceanmammal.Seal;
import experiment.oceanpark.animal.reptile.Crocodile;
import experiment.oceanpark.program.Interactivity;
import experiment.oceanpark.program.Performance;
import experiment.oceanpark.staff.Trainer;

import java.util.List;

/**
 * 动物类
 */
public abstract class Animal extends BaseObject {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private int gender;

    /**
     * 喂食时长
     */
    private int feedingMinutes;

    /**
     * 进行的表演
     */
    private List<Performance> performances;

    /**
     * 进行的互动
     */
    private List<Interactivity> interactivities;

    /**
     * 所属驯兽员
     */
    private Trainer trainer;

    @java.beans.ConstructorProperties({"nickname", "age", "gender", "feedingMinutes", "performances", "interactivities", "trainer"})
    public Animal(String nickname, int age, int gender, int feedingMinutes, List<Performance> performances, List<Interactivity> interactivities, Trainer trainer) {
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.feedingMinutes = feedingMinutes;
        this.performances = performances;
        this.interactivities = interactivities;
        this.trainer = trainer;
    }

    public Animal() {
    }

    public String getKlass() {
        String klass;
        if (this instanceof ClownFish)
            klass = "小丑鱼";
        else if (this instanceof FlyingFish)
            klass = "飞鱼";
        else if (this instanceof Shark)
            klass = "鲨鱼";
        else if (this instanceof Dolphin)
            klass = "海豚";
        else if (this instanceof Seal)
            klass = "海豹";
        else if (this instanceof SeaLion)
            klass = "海狮";
        else if (this instanceof Crocodile)
            klass = "鳄鱼";
        else
            klass = "蜥蜴";
        return klass;
    }

    @Override
    public String toString() {
        String klass = getKlass();
        return String.format("出场的是%s，今天表演的%s名叫%s，%d岁，%s性，现在有请训兽师%s带领%s出场", klass, klass, nickname, age, gender == 0 ? "雌" : "雄", trainer, nickname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer)
            return (Integer)obj == getId();
        return false;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getAge() {
        return this.age;
    }

    public int getGender() {
        return this.gender;
    }

    public int getFeedingMinutes() {
        return this.feedingMinutes;
    }

    public List<Performance> getPerformances() {
        return this.performances;
    }

    public List<Interactivity> getInteractivities() {
        return this.interactivities;
    }

    public Trainer getTrainer() {
        return this.trainer;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setFeedingMinutes(int feedingMinutes) {
        this.feedingMinutes = feedingMinutes;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public void setInteractivities(List<Interactivity> interactivities) {
        this.interactivities = interactivities;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}

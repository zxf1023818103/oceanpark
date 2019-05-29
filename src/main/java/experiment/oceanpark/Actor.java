package experiment.oceanpark;


import experiment.oceanpark.animal.CanFly;
import experiment.oceanpark.animal.CanSwim;
import experiment.oceanpark.animal.bird.Canary;
import experiment.oceanpark.animal.bird.Parrot;
import experiment.oceanpark.animal.fish.ClownFish;
import experiment.oceanpark.animal.fish.FlyingFish;
import experiment.oceanpark.animal.fish.Shark;
import experiment.oceanpark.animal.oceanmammal.Dolphin;
import experiment.oceanpark.animal.oceanmammal.SeaLion;
import experiment.oceanpark.animal.oceanmammal.Seal;
import experiment.oceanpark.animal.reptile.Crocodile;
import experiment.oceanpark.animal.reptile.Lizard;
import experiment.oceanpark.other.Airship;
import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class Actor implements Comparable<Actor>, Serializable {

    /**
     * 编号
     */
    private int id;

    /**
     * 分数
     */
    private int score;

    public int getId() {
        return this.id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        else if (this instanceof Lizard)
            klass = "蜥蜴";
        else if (this instanceof Parrot)
            klass = "鹦鹉";
        else if (this instanceof Canary)
            klass = "金丝雀";
        else if (this instanceof Airship)
            klass = "飞艇";
        else
            klass = "空中飞人";
        return klass;
    }

    private int getOrder() {
        if (this instanceof CanFly)
            return 3;
        else if (this instanceof CanSwim)
            return  2;
        else return  1;
    }

    @Override
    public int compareTo(Actor o) {
        int thisType = getOrder();
        int oType = o.getOrder();
        if (thisType == oType)
            return o.score - score;
        else return thisType - oType;
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/actors/" + getClass().getSimpleName() + ".jpg"));
    }
}

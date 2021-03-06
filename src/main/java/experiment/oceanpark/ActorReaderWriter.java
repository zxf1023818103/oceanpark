package experiment.oceanpark;

import java.io.*;

import java.util.Collections;
import java.util.List;

public class ActorReaderWriter {

    private String path;

    private List<Actor> actors;

    public ActorReaderWriter(String path, List<Actor> actors) {
        this.path = path;
        this.actors = actors;
    }

    public void readActors() {
        try {
            Object result = new ObjectInputStream(new FileInputStream(path)).readObject();
            if (result instanceof List && ((List) result).size() != 0 && ((List) result).get(0) instanceof Actor) {
                Collections.copy(actors, (List<Actor>) result);
            }
        } catch (Exception e) {
            System.out.println("不存在该文件，将新建。");
            writeActors();
        }
    }

    public void writeActors() {
        try {
            new ObjectOutputStream(new FileOutputStream(path)).writeObject(actors);
        } catch (IOException e) {
            System.out.println("文件写入失败。");
            System.exit(1);
        }
    }

}

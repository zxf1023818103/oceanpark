package experiment.oceanpark.gui.controller;

import com.sun.istack.internal.NotNull;
import experiment.oceanpark.*;
import experiment.oceanpark.other.Airship;
import experiment.oceanpark.other.Superman;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.*;

public class LoginController {

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

    static {
        for (AnimalEnum animalEnum : animalEnums) {
            Animal animal = Generator.generateAnimal(animalEnum);
            assert animal != null;
            animalMap.put(animal.getId(), animal);
            actors.add(animal);
        }
        Collections.shuffle(actors);
        generateConsumers();
        try {
            generateProgramList("programs.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public StackPane stackPanel;

    public ImageView backgroundView;

    public VBox loginPanel;

    public VBox guestLoginPanel;

    public VBox checkoutPanel;

    public VBox rechargePanel;

    public BorderPane animationPanel;

    public VBox feedingPanel;

    public VBox judgePanel;

    public HBox endingPanel;

    public Button vipLoginButton;

    public TextField guestNameField;

    public ToggleGroup genderGroup;

    public RadioButton maleRadioButton;

    public RadioButton femaleRadioButton;

    public Label guestLoginErrorMessageLabel;

    public Button guestLoginButton;

    public TextField vipIdTextField;

    public Label loginErrorMessageLabel;

    public Label checkoutErrorMessageLabel;

    public ComboBox<Integer> ticketNumberBox;

    public Button purchaseButton;

    public Button gotoRechargeButton;

    public TextField rechargeAmountField;

    public Button rechargeButton;

    public Label rechargeErrorMessageLabel;

    public ImageView actorImageView;

    public TextField feedingField;

    public Button feedingButton;

    public Label feedingErrorMessageLabel;

    public Label feedingInfoMessageLabel;

    public Label animalNameLabel;

    public ImageView animalImageView;

    public ComboBox<Integer> scoreBox;

    public Label judgeErrorMessageLabel;

    /// 栈顶始终为当前页面
    private Stack<Pane> historyPaneStack = new Stack<>();

    /**
     * 后退到上一个页面
     */
    public void locateBack(MouseEvent event) {
        if (historyPaneStack.size() <= 1)
            return;
        historyPaneStack.pop().setVisible(false);
        historyPaneStack.peek().setVisible(true);
    }

    /**
     * 跳转到指定页面
     * @param panel 要跳转到的页面
     */
    private void locateTo(Pane panel) {
        if (historyPaneStack.empty())
            historyPaneStack.push(loginPanel);
        historyPaneStack.peek().setVisible(false);
        panel.setVisible(true);
        historyPaneStack.push(panel);
    }

    private Consumer consumer = null;

    private String guestName = null;

    public void vipLogin(MouseEvent event) {
        locateTo(checkoutPanel);
    }

    public void gotoGuestLogin(MouseEvent mouseEvent) {
        locateTo(guestLoginPanel);
    }

    public void guestLogin(MouseEvent event) {
        consumer = new Consumer(guestName, Boolean.parseBoolean((String)genderGroup.getSelectedToggle().getUserData()));
        consumerMap.put(consumer.getId(), consumer);
        gotoRechargeButton.setVisible(false);
        locateTo(checkoutPanel);
    }

    public void validateVipId(Event keyEvent) {
        int id;
        if (vipIdTextField.getText().isEmpty()) {
            loginErrorMessageLabel.setText("请输入会员号。");
            vipLoginButton.setDisable(true);
            return;
        }
        try {
            id = Integer.parseInt(vipIdTextField.getText());
        } catch (Exception e) {
            loginErrorMessageLabel.setText("格式错误，请重新输入。");
            vipLoginButton.setDisable(true);
            return;
        }
        consumer = consumerMap.get(id);
        if (!(consumer instanceof VipConsumer)) {
            loginErrorMessageLabel.setText("您输入的编号有误或不是会员，请重新输入。");
            vipLoginButton.setDisable(true);
            return;
        }
        loginErrorMessageLabel.setText(null);
        vipLoginButton.setDisable(false);
    }

    public void validateGuestName(KeyEvent keyEvent) {
        guestLoginButton.setDisable(guestNameField.getText().isEmpty());
    }

    public void validateRechargeAmount(KeyEvent event) {
        String text = rechargeAmountField.getText();
        if (text.isEmpty()) {
            rechargeErrorMessageLabel.setText("请输入充值金额");
            rechargeButton.setDisable(true);
            return;
        }
        try {
            int amount = Integer.parseInt(text);
        } catch (Exception e) {
            rechargeErrorMessageLabel.setText("格式错误");
            rechargeButton.setDisable(true);
            return;
        }
        rechargeErrorMessageLabel.setText(null);
        rechargeButton.setDisable(false);
    }

    public void purchase(MouseEvent mouseEvent) {
        Integer ticketNumber = ticketNumberBox.valueProperty().get();
        if (ticketNumber == null) {
            checkoutErrorMessageLabel.setText("请选择购买数量。");
            return;
        }
        int fee = ticketNumber * 120;
        if (consumer instanceof VipConsumer) {
            if (((VipConsumer) consumer).getBalance() < fee) {
                checkoutErrorMessageLabel.setText("您的余额不足，请充值。");
                return;
            }
        }
        ((VipConsumer) consumer).setBalance(((VipConsumer) consumer).getBalance() - fee);
        locateTo(animationPanel);
        playAll();
    }

    public void recharge(MouseEvent mouseEvent) {
        if (consumer instanceof VipConsumer) {
            rechargeButton.setDisable(true);
        }
    }

    public void gotoRecharge(MouseEvent mouseEvent) {
        locateTo(rechargePanel);
        rechargeButton.setDisable(false);
    }

    private Iterator<Actor> iterator = null;

    private void playNext() {
        if (iterator == null)
            iterator = actors.iterator();
        if (iterator.hasNext()) {
            /// 获取下一个 Actor
            Actor actor = iterator.next();
            /// 设置下一个图像
            actorImageView.setImage(actor.getImage());
            actorImageView.xProperty().setValue(-actorImageView.getFitWidth());
            actorImageView.setVisible(true);
            /// 初始化动画
            KeyValue keyValue = new KeyValue(actorImageView.xProperty(), stackPanel.getWidth());
            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.setOnFinished(e -> {
                if (iterator.hasNext())
                    playNext();
                else {
                    actors.removeIf(item -> !(item instanceof Animal));
                    //backgroundView.setVisible(true);
                    locateTo(feedingPanel);
                }
            });

            timeline.play();
        }
    }

    private void playAll() {
        backgroundView.setVisible(false);

        Collections.sort(actors);

        actors.add(0, new Airship());
        actors.add(1, new Superman());
        playNext();
    }

    private Set<Integer> feedAnimalIdSet = new HashSet<>();

    private Iterator<Map.Entry<Integer, Animal>> animalIterator;

    private Animal animal;

    public void feed(MouseEvent event) {
        int id = 0;
        try {
            String text = feedingField.getText();
            Scanner scanner = new Scanner(text);
            id = scanner.nextInt();
        } catch (Exception e) {
            feedingErrorMessageLabel.setText("格式错误，请重新输入。");
            return;
        }
        Animal animal = animalMap.get(id);
        if (animal == null) {
            feedingErrorMessageLabel.setText("输入的编号有误，请重新输入。");
            return;
        }
        if (feedAnimalIdSet.contains(animal.getId())) {
            feedingErrorMessageLabel.setText("该动物已经喂食过，请重新输入。");
            return;
        }
        feedAnimalIdSet.add(animal.getId());
        feedingErrorMessageLabel.setText(null);
        feedingInfoMessageLabel.setText(animal.getKlass() + "喂食完毕。");
        feedingField.clear();
        if (animalMap.size() == feedAnimalIdSet.size()) {
            locateTo(judgePanel);
            animalIterator = animalMap.entrySet().iterator();
            this.animal = animalIterator.next().getValue();
            animalImageView.setImage(animal.getImage());
            animalNameLabel.setText(animal.getNickname());
        }
    }

    public void submitScoreAndLoadNext(MouseEvent event) {
        Integer score = scoreBox.valueProperty().get();
        if (score == null) {
            judgeErrorMessageLabel.setText("请打分。");
            return;
        }
        judgeErrorMessageLabel.setText(null);
        animal.setScore(score);
        if (animalIterator.hasNext()) {
            animal = animalIterator.next().getValue();
            animalImageView.setImage(animal.getImage());
            animalNameLabel.setText(animal.getNickname());
        }
        else {
            //readerWriter.writeActors();
            locateTo(endingPanel);
        }
    }

}

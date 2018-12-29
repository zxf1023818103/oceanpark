package experiment.oceanpark;

import com.sun.istack.internal.NotNull;

public class VipConsumer extends Consumer {

    private static int currentNumber = 1;

    private int number;

    private int balance;

    public VipConsumer(@NotNull String name, boolean gender, int balance) {
        super(name, gender);
        this.number = currentNumber++;
        this.balance = balance;
    }

    public int getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

package geektrust.ledger.domain;

public class Payment {

    private final int amount;
    private final int elapsedMonths;

    public Payment(int amount, int elapsedMonths) {
        this.amount = amount;
        this.elapsedMonths = elapsedMonths;
    }

    public int getAmount() {
        return amount;
    }

    public int getElapsedMonths() {
        return elapsedMonths;
    }
}

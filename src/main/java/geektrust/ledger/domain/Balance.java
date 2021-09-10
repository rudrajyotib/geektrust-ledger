package geektrust.ledger.domain;

public class Balance {

    private final int totalRepayment;
    private final int emiTenureLeft;

    public Balance(int totalRepayment, int emiTenureLeft) {
        this.totalRepayment = totalRepayment;
        this.emiTenureLeft = emiTenureLeft;
    }

    public int getTotalRepayment() {
        return totalRepayment;
    }

    public int getEmiTenureLeft() {
        return emiTenureLeft;
    }

    @Override
    public String toString() {
        return String.format("%d %d", totalRepayment, emiTenureLeft);
    }
}

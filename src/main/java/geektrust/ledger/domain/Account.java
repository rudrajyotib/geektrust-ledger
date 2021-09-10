package geektrust.ledger.domain;

import java.util.LinkedList;
import java.util.List;

public class Account {

    private final List<Payment> payments;
    private final int emiAmount;
    private final int totalPaymentToMake;

    public Account(int loanAmount, int loanTenureInYears, int interestRate) {
        int loanTenureInMonths = loanTenureInYears * 12;
        this.totalPaymentToMake = loanAmount +
                (int) (Math.ceil((loanAmount * interestRate * loanTenureInYears) / 100f));
        this.emiAmount = (int) Math.ceil((float) this.totalPaymentToMake / loanTenureInMonths);
        this.payments = new LinkedList<>();
    }

    public void postPayment(int elapsedMonths, int amount) {
        this.payments.add(new Payment(amount, elapsedMonths));
    }

    public Balance enquireBalance(int queryAtMonth) {
        int paymentsPosted = this.payments
                .stream()
                .filter(payment -> payment.getElapsedMonths() <= queryAtMonth)
                .mapToInt(Payment::getAmount)
                .sum();
        int repaymentAtMonth = queryAtMonth * emiAmount + paymentsPosted;
        if (repaymentAtMonth >= this.totalPaymentToMake) {
            return new Balance(this.totalPaymentToMake, 0);
        }
        int totalRemainingPayment = this.totalPaymentToMake - repaymentAtMonth;
        return new Balance(repaymentAtMonth,
                (int) Math.ceil((float) totalRemainingPayment / emiAmount));
    }
}

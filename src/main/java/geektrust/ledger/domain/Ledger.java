package geektrust.ledger.domain;

import java.util.HashMap;
import java.util.Map;

public class Ledger {

    private final Map<String, Bank> banks;

    public Ledger() {
        banks = new HashMap<>();
    }

    public void addLoanAccount(String bankName, String accountHolder, int loanAmount, int interestRate, int loanTenureInYears) {
        if (!banks.containsKey(bankName)) {
            banks.put(bankName, new Bank());
        }
        banks.get(bankName).createLoanAccount(accountHolder, loanAmount, interestRate, loanTenureInYears);
    }

    public void postPayment(String bankName, String accountName, int amount, int elapsedMonth) {
        if (!banks.containsKey(bankName))
        {
            throw new IllegalArgumentException("Bank does not exist");
        }
        banks.get(bankName).postPayment(accountName, amount, elapsedMonth);
    }

    public CustomerBalanceEnquiry enquire(String bankName, String accountName, int elapsedMonth) {
        if (!banks.containsKey(bankName))
        {
            throw new IllegalArgumentException("Bank does not exist");
        }
        return new CustomerBalanceEnquiry(bankName, accountName, banks.get(bankName).queryBalance(accountName, elapsedMonth));
    }

}

package geektrust.ledger.domain;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createLoanAccount(String accountHolder, int loanAmount, int interestRate, int loanTenureInYears)
    {
        if (accounts.containsKey(accountHolder))
        {
            throw new IllegalArgumentException("Account already exists for this customer");
        }
        accounts.put(accountHolder, new Account(loanAmount, loanTenureInYears, interestRate));
    }

    public void postPayment(String accountHolder, int paymentAmount, int elapsedMonths)
    {
        if (!accounts.containsKey(accountHolder))
        {
            throw new IllegalArgumentException("Account does not exist. Cannot post payment");
        }
        accounts.get(accountHolder).postPayment(elapsedMonths, paymentAmount);
    }

    public Balance queryBalance(String accountHolder, int elapsedMonths)
    {
        if (!accounts.containsKey(accountHolder))
        {
            throw new IllegalArgumentException("Account does not exist. Cannot post payment");
        }
        return accounts.get(accountHolder).enquireBalance(elapsedMonths);
    }
}

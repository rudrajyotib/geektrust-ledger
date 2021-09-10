package geektrust.ledger.domain;

public class CustomerBalanceEnquiry {

    private final String bankName;
    private final String accountHolder;
    private final Balance balance;

    public CustomerBalanceEnquiry(String bankName, String accountHolder, Balance balance) {
        this.bankName = bankName;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",this.bankName, this.accountHolder, this.balance.toString());
    }
}

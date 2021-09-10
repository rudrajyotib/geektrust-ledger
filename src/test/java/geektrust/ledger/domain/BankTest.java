package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankTest {

    @Test
    public void shouldCreateAndMaintainAccounts() {
        Bank bank = new Bank();
        bank.createLoanAccount("A1", 1000, 2, 1);
        bank.createLoanAccount("A2", 1000, 2, 1);
        bank.postPayment("A1", 200, 3);
        bank.postPayment("A2", 300, 4);
        Balance balanceA1After4Months = bank.queryBalance("A1", 4);
        Balance balanceA2After6Months = bank.queryBalance("A2", 6);

        assertEquals(810, balanceA2After6Months.getTotalRepayment());
        assertEquals(3, balanceA2After6Months.getEmiTenureLeft());

        assertEquals(540, balanceA1After4Months.getTotalRepayment());
        assertEquals(6, balanceA1After4Months.getEmiTenureLeft());
    }

    @Test
    public void shouldReportDuplicateAccount() {
        Bank bank = new Bank();
        bank.createLoanAccount("A1", 100, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> bank.createLoanAccount("A1", 100, 1, 1));
    }

    @Test
    public void shouldReportAccountDoesNotExistDuringPayment() {
        Bank bank = new Bank();
        bank.createLoanAccount("A1", 100, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> bank.postPayment("A2", 100, 1));
    }

    @Test
    public void shouldReportAccountDoesNotExistInBalanceEnquiry() {
        Bank bank = new Bank();
        bank.createLoanAccount("A1", 100, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> bank.queryBalance("A2", 1));
    }


}
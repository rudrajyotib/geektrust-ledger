package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void shouldCreateAccountAndReportRemainingEmisWithoutLumpSumPayment() {
        Account account = new Account(5000, 1, 6);
        Balance balance = account.enquireBalance(3);
        assertEquals(1326, balance.getTotalRepayment());
        assertEquals(9, balance.getEmiTenureLeft());
    }

    @Test
    public void shouldCreateAccountAndReportRemainingEmisWithSingleLumpSumPayment()
    {
        Account account = new Account(5000, 2, 6);
        account.postPayment(5, 500);
        Balance balanceAt3Months = account.enquireBalance(3);
        assertEquals(702, balanceAt3Months.getTotalRepayment());
        assertEquals(21, balanceAt3Months.getEmiTenureLeft());
        Balance balanceAt6Months = account.enquireBalance(6);
        assertEquals(1904, balanceAt6Months.getTotalRepayment());
        assertEquals(16, balanceAt6Months.getEmiTenureLeft());
    }

    @Test
    public void shouldCreateAccountAndReportNothingRemainingWhenEnquiredAfterFullyRepaid()
    {
        Account account = new Account(5000, 2, 6);
        account.postPayment(5, 500);
        Balance balanceAt3Months = account.enquireBalance(3);
        assertEquals(702, balanceAt3Months.getTotalRepayment());
        assertEquals(21, balanceAt3Months.getEmiTenureLeft());
        Balance balanceAfterRepayInFull = account.enquireBalance(22);
        assertEquals(5600, balanceAfterRepayInFull.getTotalRepayment());
        assertEquals(0, balanceAfterRepayInFull.getEmiTenureLeft());
    }
}
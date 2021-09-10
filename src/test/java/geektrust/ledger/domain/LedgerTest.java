package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LedgerTest {

    @Test
    public void shouldCreateLoanAccounts()
    {
        Ledger ledger = new Ledger();
        ledger.addLoanAccount("B1", "A1", 1000, 2, 1);
        ledger.addLoanAccount("B1", "A2", 1000, 2, 1);
        ledger.addLoanAccount("B2", "A1", 1000, 2, 1);
        ledger.postPayment("B1", "A1", 200, 3);
        ledger.postPayment("B2", "A1", 300, 4);

        CustomerBalanceEnquiry enquiryB1A1 = ledger.enquire("B1", "A1", 4);
        CustomerBalanceEnquiry enquiryB1A2 = ledger.enquire("B1", "A2", 4);
        CustomerBalanceEnquiry enquiryB2A1 = ledger.enquire("B2", "A1", 6);

        assertEquals("B1", enquiryB1A1.getBankName());
        assertEquals("A1", enquiryB1A1.getAccountHolder());
        assertEquals(540, enquiryB1A1.getBalance().getTotalRepayment());
        assertEquals(6, enquiryB1A1.getBalance().getEmiTenureLeft());

        assertEquals("B1", enquiryB1A2.getBankName());
        assertEquals("A2", enquiryB1A2.getAccountHolder());
        assertEquals(340, enquiryB1A2.getBalance().getTotalRepayment());
        assertEquals(8, enquiryB1A2.getBalance().getEmiTenureLeft());

        assertEquals("B2", enquiryB2A1.getBankName());
        assertEquals("A1", enquiryB2A1.getAccountHolder());
        assertEquals(810, enquiryB2A1.getBalance().getTotalRepayment());
        assertEquals(3, enquiryB2A1.getBalance().getEmiTenureLeft());
    }

    @Test
    public void shouldReportPostingPaymentForBankThatDoesNotExist()
    {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.postPayment("X", "Y", 1, 1));
    }

    @Test
    public void shouldReportEnquiryForBankThatDoesNotExist()
    {
        Ledger ledger = new Ledger();
        assertThrows(IllegalArgumentException.class, () -> ledger.enquire("X", "Y", 1));
    }




}
package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBalanceEnquiryTest {

    @Test
    public void shouldCreateCustomerBalance()
    {
        CustomerBalanceEnquiry customerBalanceEnquiry
                = new CustomerBalanceEnquiry("B", "A", new Balance(100, 1));
        assertEquals("B", customerBalanceEnquiry.getBankName());
        assertEquals("A", customerBalanceEnquiry.getAccountHolder());
        assertEquals(100, customerBalanceEnquiry.getBalance().getTotalRepayment());
        assertEquals("B A 100 1", customerBalanceEnquiry.toString());
    }


}
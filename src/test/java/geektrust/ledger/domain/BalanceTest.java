package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {

    @Test
    public void shouldCreateBalance()
    {
        Balance balance = new Balance(1000, 5);
        assertEquals(1000, balance.getTotalRepayment());
        assertEquals(5, balance.getEmiTenureLeft());
        assertEquals("1000 5", balance.toString());
    }

}
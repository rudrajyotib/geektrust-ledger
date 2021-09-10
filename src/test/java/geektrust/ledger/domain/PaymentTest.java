package geektrust.ledger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    public void shouldCreatePayment()
    {
        Payment payment = new Payment(100, 1);
        assertEquals(100, payment.getAmount());
        assertEquals(1, payment.getElapsedMonths());
    }

}
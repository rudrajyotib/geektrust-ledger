package geektrust.ledger.api;

import geektrust.ledger.domain.Balance;
import geektrust.ledger.domain.CustomerBalanceEnquiry;
import geektrust.ledger.domain.Ledger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @RegisterExtension
    private final Mockery mockery = new JUnit5Mockery() {
        {
            setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
        }
    };

    private Ledger mockLedger;

    @BeforeEach
    public void setUp() {
        mockLedger = mockery.mock(Ledger.class);
    }

    @Test
    public void shouldCreateLoanAccount() {
        mockery.checking(new Expectations() {
            {
                exactly(1).of(mockLedger).addLoanAccount(with("B1"), with("A1"),
                        with(1000), with(6), with(1));
            }
        });
        Command.LOAN.execute(mockLedger, new String[]{"LOAN", "B1", "A1", "1000", "1", "6"});
    }

    @Test
    public void shouldPostPayment() {
        mockery.checking(new Expectations() {
            {
                exactly(1).of(mockLedger).postPayment(with("B1"), with("A1"),
                        with(1000), with(1));
            }
        });
        Command.PAYMENT.execute(mockLedger, new String[]{"PAYMENT", "B1", "A1", "1000", "1"});
    }

    @Test
    public void shouldEnquire() {
        mockery.checking(new Expectations() {
            {
                exactly(1).of(mockLedger).enquire(with("B1"), with("A1"),
                        with(6));
                will(returnValue(new CustomerBalanceEnquiry("B1", "A1", new Balance(100, 1))));
            }
        });
        Optional<String> result = Command.BALANCE.execute(mockLedger, new String[]{"PAYMENT", "B1", "A1", "6"});
        assertTrue(result.isPresent());
        assertEquals("B1 A1 100 1", result.get() );
    }

}
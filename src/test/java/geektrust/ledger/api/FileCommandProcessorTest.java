package geektrust.ledger.api;

import geektrust.ledger.domain.Balance;
import geektrust.ledger.domain.CustomerBalanceEnquiry;
import geektrust.ledger.domain.Ledger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileCommandProcessorTest {

    @RegisterExtension
    private final Mockery mockery = new JUnit5Mockery() {
        {
            setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
        }
    };

    private Ledger mockLedger;

    @BeforeEach
    public void setUp()
    {
        mockLedger = mockery.mock(Ledger.class);
    }

    @Test
    public void shouldProcessFormattedCommands() throws IOException {
        final States ledgerState = mockery.states("ledger-state").startsAs("Initiated");

        mockery.checking(new Expectations()
        {
            {
                exactly(1).of(mockLedger).addLoanAccount(with("IDIDI"),
                        with("Dale"), with(5000), with(6), with(1));
                when(ledgerState.is("Initiated"));
                then(ledgerState.is("DaleAccountCreated"));

                exactly(1).of(mockLedger).addLoanAccount(with("MBI"),
                        with("Harry"), with(10000), with(7), with(3));
                when(ledgerState.is("DaleAccountCreated"));
                then(ledgerState.is("HarryAccountCreated"));

                exactly(1).of(mockLedger).addLoanAccount(with("UON"),
                        with("Shelly"), with(15000), with(9), with(2));
                when(ledgerState.is("HarryAccountCreated"));
                then(ledgerState.is("ShellyAccountCreated"));

                exactly(1).of(mockLedger).postPayment(with("IDIDI"), with("Dale"),
                        with(1000), with(5));
                when(ledgerState.is("ShellyAccountCreated"));
                then(ledgerState.is("DalePaymentPosted"));

                exactly(1).of(mockLedger).postPayment(with("MBI"), with("Harry"),
                        with(5000), with(10));
                when(ledgerState.is("DalePaymentPosted"));
                then(ledgerState.is("HarryPaymentPosted"));

                exactly(1).of(mockLedger).postPayment(with("UON"), with("Shelly"),
                        with(7000), with(12));
                when(ledgerState.is("HarryPaymentPosted"));
                then(ledgerState.is("ShellyPaymentPosted"));

                exactly(1).of(mockLedger).enquire(with("IDIDI"), with("Dale"), with(3));
                when(ledgerState.is("ShellyPaymentPosted"));
                will(returnValue(new CustomerBalanceEnquiry("IDIDI", "Dale", new Balance(1326, 9))));
                then(ledgerState.is("DaleEnquiredMonth3"));

                exactly(1).of(mockLedger).enquire(with("IDIDI"), with("Dale"), with(6));
                when(ledgerState.is("DaleEnquiredMonth3"));
                will(returnValue(new CustomerBalanceEnquiry("IDIDI", "Dale", new Balance(3652, 4))));
                then(ledgerState.is("DaleEnquiredMonth6"));

                exactly(1).of(mockLedger).enquire(with("UON"), with("Shelly"), with(12));
                when(ledgerState.is("DaleEnquiredMonth6"));
                will(returnValue(new CustomerBalanceEnquiry("UON", "Shelly", new Balance(15856, 3))));
                then(ledgerState.is("ShellyEnquiredMonth12"));

                exactly(1).of(mockLedger).enquire(with("MBI"), with("Harry"), with(12));
                when(ledgerState.is("ShellyEnquiredMonth12"));
                will(returnValue(new CustomerBalanceEnquiry("MBI", "Harry", new Balance(9044, 10))));
                then(ledgerState.is("HarryEnquiredMonth12"));
            }
        });

        Path commandFile = Paths.get("src", "test", "resources", "command-success");
        FileCommandProcessor fileCommandProcessor
                = new FileCommandProcessor(commandFile.toFile().getAbsolutePath(), mockLedger);
        List<String> result = fileCommandProcessor.process();
        assertEquals(4, result.size());
        assertEquals("IDIDI Dale 1326 9", result.get(0));
        assertEquals("IDIDI Dale 3652 4", result.get(1));
        assertEquals("UON Shelly 15856 3", result.get(2));
        assertEquals("MBI Harry 9044 10", result.get(3));

    }

    @Test
    public void shouldReportInvalidPath()
    {
        FileCommandProcessor fileCommandProcessor
                = new FileCommandProcessor("Non existent path", mockLedger);
        assertThrows(IllegalArgumentException.class, fileCommandProcessor::process);
    }

    @Test
    public void shouldReportWhenFileSpecifiedIsDirectory()
    {
        Path commandFile = Paths.get("src", "test", "resources");
        FileCommandProcessor fileCommandProcessor
                = new FileCommandProcessor(commandFile.toFile().getAbsolutePath(), mockLedger);
        assertThrows(IllegalArgumentException.class, fileCommandProcessor::process);
    }




}
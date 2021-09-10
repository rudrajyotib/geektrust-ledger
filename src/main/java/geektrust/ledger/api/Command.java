package geektrust.ledger.api;

import geektrust.ledger.domain.Ledger;

import java.util.Optional;
import java.util.function.BiFunction;

public enum Command {

    LOAN((ledger, strings) -> {
        ledger.addLoanAccount(strings[1], strings[2], Integer.parseInt(strings[3]), Integer.parseInt(strings[5]), Integer.parseInt(strings[4]));
        return Optional.empty();
    }),
    PAYMENT((ledger, strings) -> {
        ledger.postPayment(strings[1], strings[2], Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
        return Optional.empty();
    }),
    BALANCE((ledger, strings) -> {
        return Optional.of(ledger.enquire(strings[1], strings[2], Integer.parseInt(strings[3])).toString());
    });

    private final BiFunction<Ledger, String[], Optional<String>> processor;

    Command(BiFunction<Ledger, String[], Optional<String>> processor) {
        this.processor = processor;
    }

    public Optional<String> execute(Ledger ledger, String[] params)
    {
        return this.processor.apply(ledger, params);
    }
}

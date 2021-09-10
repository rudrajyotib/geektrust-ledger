package geektrust.ledger;

/*
https://www.geektrust.in/coding-problem/backend/ledger-co
 */

import geektrust.ledger.api.FileCommandProcessor;
import geektrust.ledger.domain.Ledger;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Ledger ledger = new Ledger();
        FileCommandProcessor fileCommandProcessor
                = new FileCommandProcessor(args[0], ledger);
        fileCommandProcessor.process().forEach(System.out::println);
    }

}

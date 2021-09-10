package geektrust.ledger.api;

import geektrust.ledger.domain.Ledger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCommandProcessor {

    private final String filePath;
    private final Ledger ledger;

    public FileCommandProcessor(String filePath, Ledger ledger) {
        this.filePath = filePath;
        this.ledger = ledger;
    }

    public List<String> process() throws IOException {
        File commandFile = new File(this.filePath);
        if (!commandFile.exists()) {
            throw new IllegalArgumentException("File path does not exist");
        }
        if (!commandFile.isFile()) {
            throw new IllegalArgumentException("Not a file");
        }
        try (Stream<String> commandStream = Files.lines(Paths.get(this.filePath))) {
            return commandStream
                    .map(s -> s.split(" "))
                    .map(strings -> Command.valueOf(strings[0]).execute(ledger, strings))
                    .map(s -> s.orElse(""))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
    }
}

package ru.hse.se.ucheck.cli;

import ru.hse.se.ucheck.UCheck;
import ru.hse.se.ucheck.UCheckRamImpl;

import java.nio.charset.Charset;
import java.util.Scanner;

import picocli.CommandLine;

import static picocli.CommandLine.*;

@Command(name = "ucheck", subcommands = {
        AddCheckCommand.class,
        GetStoreRatingCommand.class
})
public class App {

    public UCheck ucheck = new UCheckRamImpl();

    // add --cf=res/check.json --rf=res/review1.json
    // rating --sf=res/store.json

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new App());
        Scanner sc = new Scanner(System.in, Charset.defaultCharset().name());
        while (sc.hasNext()) {
            String line = sc.nextLine();
            int code = cli.execute(line.split(" "));
            if (code != 0) {
                System.exit(code);
            }
        }
    }

}


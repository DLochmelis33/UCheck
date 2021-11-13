package ru.hse.se.ucheck.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import ru.hse.se.ucheck.UCheck;
import ru.hse.se.ucheck.UCheckRamImpl;
import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.base.Store;
import ru.hse.se.ucheck.models.rating.Rating;
import ru.hse.se.ucheck.models.rating.Review;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "add")
class AddCheckCommand implements Callable<Integer> {

    @ParentCommand
    private App parent;

    @Option(names = "--cf", required = true)
    private File checkFile;

    @Option(names = "--rf", required = true)
    private File reviewFile;

    @Override
    public Integer call() throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Check check = objectMapper.readValue(checkFile, Check.class);
            Review review = objectMapper.readValue(reviewFile, Review.class);
            parent.ucheck.addCheck(check, review);
        } catch (Exception e) {
            e.printStackTrace();
            return 5;
        }
        return 0;
    }
}

@Command(name = "rating")
class GetStoreRatingCommand implements Callable<Integer> {

    @ParentCommand
    private App parent;

    @Option(names = "--sf", required = true)
    private File storeFile;

    @Override
    public Integer call() throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Store store = objectMapper.readValue(storeFile, Store.class);
            Rating rating = parent.ucheck.getStoreRating(store);
            System.out.println(rating.getAverage());
        } catch (Exception e) {
            e.printStackTrace();
            return 5;
        }
        return 0;
    }

}

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
            if(code != 0) {
                System.exit(code);
            }
        }
    }

}


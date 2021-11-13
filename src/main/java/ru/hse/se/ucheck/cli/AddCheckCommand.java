package ru.hse.se.ucheck.cli;

import ru.hse.se.ucheck.models.base.Check;
import ru.hse.se.ucheck.models.rating.Review;

import java.io.File;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public Integer call() {
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
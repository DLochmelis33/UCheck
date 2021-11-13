package ru.hse.se.ucheck.cli;

import ru.hse.se.ucheck.models.base.Store;
import ru.hse.se.ucheck.models.rating.Rating;

import java.io.File;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.ObjectMapper;

import static picocli.CommandLine.*;

@Command(name = "rating")
class GetStoreRatingCommand implements Callable<Integer> {

    @ParentCommand
    private App parent;

    @Option(names = "--sf", required = true)
    private File storeFile;

    @Override
    public Integer call() {
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


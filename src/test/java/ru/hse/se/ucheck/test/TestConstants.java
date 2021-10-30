package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.models.*;

import java.time.ZonedDateTime;
import java.util.List;

public class TestConstants {

    public static final Item cocaCola = new Item(1, "Coca-Cola 0.5L", 50.0, Measure.PIECE);
    public static final Item fanta = new Item(2, "Fanta 1L", 90.0, Measure.PIECE);
    public static final Item meat = new Item(3, "Porkchop", 359.9, Measure.KILOGRAM);

    public static final Store perekrestok = new Store("Moscow, Kremlin", "Perekrestok");
    public static final Store karusel = new Store("St.Petersburg, Hermitage", "Karusel'");

    public static final Check singleItemCheck = new Check(List.of(cocaCola),
            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"), perekrestok);
    public static final Check multiItemCheck = new Check(List.of(cocaCola, fanta, meat),
            ZonedDateTime.parse("2021-01-01T00:00:00+01:00[Europe/Paris]"), karusel);

    public static final ItemInStore cocaColaInPerekrestok = new ItemInStore(cocaCola.getPrice(), perekrestok, 0.0);

}

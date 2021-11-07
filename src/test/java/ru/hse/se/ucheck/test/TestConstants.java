package ru.hse.se.ucheck.test;

import ru.hse.se.ucheck.models.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class TestConstants {

    public static final Item cocaCola = new Item(1, "Coca-Cola 0.5L", 50.0, Measure.PIECE);
    public static final Item cocaColaExpensive = new Item(1, "Coca-Cola 0.5L", 150.0, Measure.PIECE);
    public static final Item fanta = new Item(2, "Fanta 1L", 90.0, Measure.PIECE);
    public static final Item meat = new Item(3, "Porkchop", 359.9, Measure.KILOGRAM);
    public static final Item coneForest = new Item(4, "Cone Forest Water 1L", 60.0, Measure.PIECE);

    public static final Store karusel = new Store("St.Petersburg, Hermitage", "Karusel'",
            new Coordinates(59.9397392, 30.3140793));
    public static final Store perekrestok = new Store("Moscow, Kremlin", "Perekrestok",
            new Coordinates(55.751694, 37.617218));
    public static final Store premiumPerekrestok = new Store("Paris, Louvre", perekrestok.getOutlet(),
            new Coordinates(48.86102, 2.337985));


    public static final Check singleItemCheck = new Check(List.of(cocaCola),
            ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"), perekrestok);
    public static final Check multiItemCheck = new Check(List.of(cocaCola, fanta, meat),
            ZonedDateTime.parse("2021-01-01T00:00:00+01:00[Europe/Paris]"), karusel);

    public static final Check karuselCheck = new Check(List.of(cocaCola),
            ZonedDateTime.parse("2021-01-01T00:00:00+01:00[Europe/Paris]"), karusel);
    public static final Check perekrestokCheapCheck = new Check(List.of(cocaCola),
            ZonedDateTime.parse("2021-01-01T00:00:00+01:00[Europe/Paris]"), perekrestok);
    public static final Check perekrestokExpensiveCheck = new Check(List.of(cocaColaExpensive),
            ZonedDateTime.parse("2021-01-01T00:00:00+01:00[Europe/Paris]"), premiumPerekrestok);

    public static final ItemInStore cocaColaInPerekrestok = new ItemInStore(cocaCola.getPrice(), perekrestok, 0.0);
    public static final ItemInStore cocaColaInKarusel = new ItemInStore(cocaCola.getPrice(), karusel, 0.0);
    public static final ItemInStore cocaColaInPremiumPerekrestok = new ItemInStore(
            cocaColaExpensive.getPrice(), premiumPerekrestok, 0.0);

    public static final ItemInCart oneCocaCola = new ItemInCart(cocaCola.getCode(), 1);
    public static final ItemInCart twoCocaColas = new ItemInCart(cocaCola.getCode(), 2);
    public static final ItemInCart fiveCocaColas = new ItemInCart(cocaCola.getCode(), 5);

    public static final ItemInCart oneFanta = new ItemInCart(fanta.getCode(), 1);
    public static final ItemInCart oneMeat = new ItemInCart(meat.getCode(), 1);

    public static final CartInStore singleItemCart = new CartInStore(
            Map.of(oneCocaCola, cocaCola.getPrice()), perekrestok, 0.0);
    public static final CartInStore multiItemCart = new CartInStore(
            Map.of(
                    twoCocaColas, cocaCola.getPrice(),
                    oneFanta, fanta.getPrice(),
                    oneMeat, meat.getPrice()
            ), karusel, 0.0);

    public static final CartInStore perekrestokCart = new CartInStore(
            Map.of(fiveCocaColas, cocaColaInPerekrestok.getPrice()),
            perekrestok, 0.0);
    public static final CartInStore karuselCart = new CartInStore(
            Map.of(fiveCocaColas, cocaColaInKarusel.getPrice()),
            karusel, 0.0);
    public static final CartInStore premiumPerekrestokCart = new CartInStore(
            Map.of(fiveCocaColas, cocaColaInPremiumPerekrestok.getPrice()),
            karusel, 0.0);

    public static final List<Tag> cocaColaDefaultTags = List.of(Tag.CARBONATED_DRINK, Tag.DRINK);
    public static final List<Tag> cocaColaUpdatedTags = List.of(Tag.CARBONATED_DRINK, Tag.DRINK, Tag.PARTY);

}

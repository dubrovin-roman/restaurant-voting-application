package ru.javaops.bootjava.web.dish;

import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.to.DishTo;
import ru.javaops.bootjava.util.DishesUtil;
import ru.javaops.bootjava.web.MatcherFactory;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingEqualsComparator(DishTo.class);

    public static final int SOUP_ID = 1;
    public static final int STEAK_ID = 2;
    public static final int FISH_ID = 3;
    public static final int SALAD_ID = 4;
    public static final int DESSERT_ID = 5;
    public static final int DISH_NOT_FOUND_ID = 1000;


    public static final Dish soup = new Dish(SOUP_ID,
            "Soup",
            new BigDecimal("560.23"),
            RestaurantTestData.astoria,
            LocalDate.now());
    public static final Dish steak = new Dish(STEAK_ID,
            "Steak",
            new BigDecimal("1750.30"),
            RestaurantTestData.astoria,
            LocalDate.now());
    public static final Dish fish = new Dish(FISH_ID,
            "Fish",
            new BigDecimal("1200.52"),
            RestaurantTestData.astoria,
            LocalDate.now());
    public static final Dish salad = new Dish(SALAD_ID,
            "Salad",
            new BigDecimal("450.55"),
            RestaurantTestData.astoria,
            LocalDate.now());
    public static final Dish dessert = new Dish(DESSERT_ID,
            "Dessert",
            new BigDecimal("780.00"),
            RestaurantTestData.astoria,
            LocalDate.now());

    public static final int SOUP_OLD_ID = 6;
    public static final int STEAK_OLD_ID = 7;
    public static final int FISH_OLD_ID = 8;
    public static final String DATE_OLD_MENU = "2024-07-01";
    public static final String DATE_NO_DISHES = "2024-07-02";

    public static final Dish soupOld = new Dish(SOUP_OLD_ID,
            "Soup Old",
            new BigDecimal("490.23"),
            RestaurantTestData.astoria,
            LocalDate.parse(DATE_OLD_MENU));
    public static final Dish steakOld = new Dish(STEAK_OLD_ID,
            "Steak Old",
            new BigDecimal("1700.30"),
            RestaurantTestData.astoria,
            LocalDate.parse(DATE_OLD_MENU));
    public static final Dish fishOld = new Dish(FISH_OLD_ID,
            "Fish Old",
            new BigDecimal("1130.52"),
            RestaurantTestData.astoria,
            LocalDate.parse(DATE_OLD_MENU));

    public static final int HAMBURGER_HEIGHT_ID = 9;
    public static final int STEAK_HEIGHT_ID = 10;
    public static final int SALAD_HEIGHT_ID = 11;

    public static final Dish hamburgerHeight = new Dish(HAMBURGER_HEIGHT_ID,
            "Hamburger",
            new BigDecimal("750.23"),
            RestaurantTestData.height,
            LocalDate.now());
    public static final Dish steakHeight = new Dish(STEAK_HEIGHT_ID,
            "Steak",
            new BigDecimal("2752.30"),
            RestaurantTestData.height,
            LocalDate.now());
    public static final Dish saladHeight = new Dish(SALAD_HEIGHT_ID,
            "Salad",
            new BigDecimal("550.55"),
            RestaurantTestData.height,
            LocalDate.now());

    public static final int SALAD_OLD_PANCAKES_ID = 12;

    public static final Dish saladPancakes = new Dish(SALAD_OLD_PANCAKES_ID,
            "Salad Old",
            new BigDecimal("120.52"),
            RestaurantTestData.pancakes,
            LocalDate.parse(DATE_OLD_MENU));

    public static final List<Dish> dishesByDateNowAstoria = List.of(salad, soup, dessert, fish, steak);
    public static final List<Dish> dishesByDateNowHeight = List.of(saladHeight, hamburgerHeight, steakHeight);
    public static final List<Dish> dishesByOldDate = List.of(soupOld, fishOld, steakOld);
    public static final List<Dish> dishesByAstoria = List.of(salad, soup, dessert, fish, steak, soupOld, fishOld, steakOld);

    public static Dish getNew() {
        return new Dish(null, "New Dish", new BigDecimal("325.00"), null, LocalDate.now());
    }

    public static Dish getUpdated() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return new Dish(STEAK_ID, "Steak Updated", new BigDecimal("325.00"), null, tomorrow);
    }

    public static List<DishTo> getToOnToday() {
        return Stream.concat(DishesUtil.createListTo(dishesByDateNowAstoria).stream(),
                        DishesUtil.createListTo(dishesByDateNowHeight).stream())
                .toList();
    }
}

package ru.javaops.bootjava.web.dish;

import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.web.MatcherFactory;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int SOUP_ID = 1;
    public static final int STEAK_ID = 2;
    public static final int FISH_ID = 3;
    public static final int SALAD_ID = 4;
    public static final int DESSERT_ID = 5;


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

    public static final List<Dish> dishesByDateNow = List.of(salad, soup, dessert, fish, steak);
    public static final List<Dish> dishesByOldDate = List.of(soupOld, fishOld, steakOld);
}

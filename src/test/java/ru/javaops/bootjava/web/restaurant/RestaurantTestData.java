package ru.javaops.bootjava.web.restaurant;

import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuList");

    public static final int ASTORIA_ID = 1;
    public static final int PANCAKES_ID = 2;
    public static final int NOT_FOUND_ID = 100;
    public static final String ASTORIA_ADDRESS = "RUSSIA, MOSCOW, PETROVKA 38";
    public static final String PANCAKES_ADDRESS = "RUSSIA, MOSCOW, TKACHEVKA 451";

    public static final Restaurant ASTORIA = new Restaurant(ASTORIA_ID, "Astoria", ASTORIA_ADDRESS);
    public static final Restaurant PANCAKES = new Restaurant(PANCAKES_ID, "Pancakes", PANCAKES_ADDRESS);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", "new address");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(ASTORIA_ID, "Updated Restaurant", ASTORIA_ADDRESS);
    }
}

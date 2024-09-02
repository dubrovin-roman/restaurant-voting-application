package ru.javaops.bootjava.web.restaurant;

import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuList");

    public static final int ASTORIA_ID = 1;
    public static final int PANCAKES_ID = 2;
    public static final int HEIGHT_ID = 3;
    public static final int NOT_FOUND_ID = 100;
    public static final String ASTORIA_NAME = "Astoria";
    public static final String PANCAKES_NAME = "Pancakes";
    public static final String HEIGHT_NAME = "Height 5642";
    public static final String ASTORIA_ADDRESS = "RUSSIA, MOSCOW, PETROVKA 38";
    public static final String PANCAKES_ADDRESS = "RUSSIA, MOSCOW, TKACHEVKA 451";
    public static final String HEIGHT_ADDRESS = "RUSSIA, KISLOVODSK, KURORTNY BOULEVARD 13A";

    public static final Restaurant astoria = new Restaurant(ASTORIA_ID, ASTORIA_NAME, ASTORIA_ADDRESS);
    public static final Restaurant pancakes = new Restaurant(PANCAKES_ID, PANCAKES_NAME, PANCAKES_ADDRESS);
    public static final Restaurant height = new Restaurant(HEIGHT_ID, HEIGHT_NAME, HEIGHT_ADDRESS);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", "new address");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(ASTORIA_ID, "Updated Restaurant", ASTORIA_ADDRESS);
    }
}

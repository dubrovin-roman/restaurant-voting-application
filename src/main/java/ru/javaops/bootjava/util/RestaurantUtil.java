package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress());
    }
}

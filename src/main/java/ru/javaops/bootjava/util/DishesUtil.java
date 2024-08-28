package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.to.DishTo;

import java.util.List;

@UtilityClass
public class DishesUtil {
    public static DishTo createTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getRestaurant().id(), dish.getDateOfMenu());
    }

    public static List<DishTo> createListTo(List<Dish> dishes) {
        return dishes.stream()
                .map(DishesUtil::createTo)
                .toList();
    }
}

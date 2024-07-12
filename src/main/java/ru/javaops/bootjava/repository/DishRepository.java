package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.dateOfMenu = :dateOfMenu ORDER BY d.price ASC")
    List<Dish> getAllByRestaurantIdAndDateOfMenu(@Param("restaurantId") int restaurantId, @Param("dateOfMenu") LocalDate dateOfMenu);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.id = :dishId")
    Optional<Dish> getByRestaurantIdAndDishId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);
}

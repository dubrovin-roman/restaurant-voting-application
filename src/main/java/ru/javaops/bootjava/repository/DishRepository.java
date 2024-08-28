package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.dateOfMenu = :dateOfMenu ORDER BY d.price ASC")
    List<Dish> getAllByRestaurantIdAndDateOfMenu(@Param("restaurantId") int restaurantId, @Param("dateOfMenu") LocalDate dateOfMenu);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :id ORDER BY d.dateOfMenu DESC, d.price ASC")
    List<Dish> getByRestaurantId(int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.id = :dishId")
    Optional<Dish> getByRestaurantIdAndDishId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT d FROM Dish d WHERE d.dateOfMenu = :dateOfMenu ORDER BY d.restaurant.id, d.price ASC")
    List<Dish> getAllByDateOfMenuWithRestaurant(@Param("dateOfMenu") LocalDate dateOfMenu);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.restaurant.id = :restaurantId AND d.id = :dishId")
    int deleteByRestaurantIdAndDishId(@Param("restaurantId") int restaurantId, @Param("dishId") int dishId);

    default void deleteExistedByRestaurantIdAndDishId(int restaurantId, int dishId) {
        if (deleteByRestaurantIdAndDishId(restaurantId, dishId) == 0) {
            throw new NotFoundException("Dish with id=" + dishId + " in Restaurant with id=" + restaurantId + " not found");
        }
    }

    default Dish getExistedByRestaurantIdAndDishId(int restaurantId, int dishId) {
        return getByRestaurantIdAndDishId(restaurantId, dishId).orElseThrow(
                () -> new NotFoundException("Dish with id=" + dishId + " in Restaurant with id=" + restaurantId + " not found"));
    }
}

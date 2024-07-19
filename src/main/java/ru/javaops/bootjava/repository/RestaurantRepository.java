package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findRestaurantByAddressIgnoreCase(String address);

    @Query("SELECT r FROM Restaurant r RIGHT JOIN Dish d ON r.id = d.restaurant.id WHERE d.dateOfMenu = :dateOfMenu ORDER BY r.name, r.address ASC")
    List<Restaurant> findRestaurantByDateWithDishes(@Param("dateOfMenu") LocalDate dateOfMenu);

    @Transactional
    default Restaurant prepareAndSave(Restaurant restaurant) {
        restaurant.setAddress(restaurant.getAddress().trim().toUpperCase());
        return save(restaurant);
    }

    boolean existsRestaurantById(int id);

    default void isPresentByIdOrElseThrowNotFound(int id) {
        if (!existsRestaurantById(id)) {
            throw new NotFoundException("Restaurant with id=" + id + " not found");
        }
    }
}

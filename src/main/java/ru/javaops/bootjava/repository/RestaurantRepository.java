package ru.javaops.bootjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findRestaurantByAddressIgnoreCase(String address);

    @Transactional
    default Restaurant prepareAndSave(Restaurant restaurant) {
        restaurant.setAddress(restaurant.getAddress().trim().toUpperCase());
        return save(restaurant);
    }
}

package ru.javaops.bootjava.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.javaops.bootjava.web.RestValidation.assureIdConsistent;
import static ru.javaops.bootjava.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin";

    protected DishRepository dishRepository;
    protected RestaurantRepository restaurantRepository;

    @PostMapping(value = "/restaurants/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @CacheEvict(value = {"restaurants", "dishes", "dishesTo"}, allEntries = true)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("createWithLocation {}", dish);
        checkNew(dish);
        Restaurant restaurant = restaurantRepository.getExisted(id);
        dish.setRestaurant(restaurant);
        Dish createdDish = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/restaurants/{id}/dishes/{id}")
                .buildAndExpand(id, createdDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdDish);
    }

    @GetMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("get {}", dishId);
        return dishRepository.getExistedByRestaurantIdAndDishId(restaurantId, dishId);
    }

    @GetMapping(value = "/restaurants/{id}/dishes")
    @Transactional(readOnly = true)
    public List<Dish> getAllByRestaurantId(@PathVariable int id) {
        log.info("getAllByRestaurantId {}", id);
        restaurantRepository.isPresentByIdOrElseThrowNotFound(id);
        return dishRepository.getByRestaurantId(id);
    }

    @DeleteMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"restaurants", "dishes", "dishesTo"}, allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("delete {}", dishId);
        dishRepository.deleteExistedByRestaurantIdAndDishId(restaurantId, dishId);
    }

    @PutMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = {"restaurants", "dishes", "dishesTo"}, allEntries = true)
    public void update(@PathVariable int restaurantId,
                       @PathVariable int dishId,
                       @Valid @RequestBody Dish dish) {
        log.info("update {} with id = {}", dish, dishId);
        assureIdConsistent(dish, dishId);
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        checkDishBelongsToRestaurant(restaurantId, dishId);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }

    private void checkDishBelongsToRestaurant(int restaurantId, int dishId) {
        dishRepository.getByRestaurantIdAndDishId(restaurantId, dishId)
                .orElseThrow(() -> new IllegalRequestDataException("dish with id = "
                        + dishId
                        + " does not belong to the restaurant with id = "
                        + restaurantId));
    }
}

package ru.javaops.bootjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.util.JsonUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.restaurant.RestaurantController.REST_URL;
import static ru.javaops.bootjava.web.restaurant.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.javaops.bootjava.web.restaurant.RestaurantTestData.getNew;
import static ru.javaops.bootjava.web.user.UserTestData.ADMIN_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.getId();
        newRestaurant.setId(newId);
        newRestaurant.setAddress(newRestaurant.getAddress().trim().toUpperCase());
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(newId), newRestaurant);
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}
package ru.javaops.bootjava.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.util.JsonUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.restaurant.RestaurantController.REST_URL;
import static ru.javaops.bootjava.web.restaurant.RestaurantTestData.*;
import static ru.javaops.bootjava.web.restaurant.UniqueAddressValidator.EXCEPTION_DUPLICATE_ADDRESS;
import static ru.javaops.bootjava.web.user.UserTestData.ADMIN_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(APPLICATION_JSON)
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
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant expected = new Restaurant(null, "New Restaurant", ASTORIA_ADDRESS);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_ADDRESS)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null, "new");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
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
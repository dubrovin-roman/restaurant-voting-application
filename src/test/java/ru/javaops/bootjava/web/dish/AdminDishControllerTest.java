package ru.javaops.bootjava.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.repository.DishRepository;
import ru.javaops.bootjava.util.JsonUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.dish.DishTestData.*;
import static ru.javaops.bootjava.web.user.UserTestData.ADMIN_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL_FORMAT_WITH_ONE_ID = AdminDishController.REST_URL + "/restaurants/%d/dishes";
    private static final String REST_URL_FORMAT_WITH_TWO_ID = AdminDishController.REST_URL + "/restaurants/%d/dishes/%d";

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.PANCAKES_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocationOnTomorrow() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Dish newDish = getNew();
        newDish.setDateOfMenu(tomorrow);

        ResultActions action = perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.PANCAKES_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "n", new BigDecimal("-100.23"), null, LocalDate.now());
        perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.PANCAKES_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void creatWithNotExistingRestaurant() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.NOT_FOUND_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, SOUP_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(soup));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.NOT_FOUND_ID, SOUP_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFoundDish() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, DISH_NOT_FOUND_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.ASTORIA_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesByAstoria));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByNotFoundRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(String.format(REST_URL_FORMAT_WITH_ONE_ID, RestaurantTestData.NOT_FOUND_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, DESSERT_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DESSERT_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, DISH_NOT_FOUND_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, STEAK_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.getExisted(STEAK_ID), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(null, "n", new BigDecimal("-100.23"), null, LocalDate.now());
        perform(MockMvcRequestBuilders.put(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.ASTORIA_ID, STEAK_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateRestaurantNotFound() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.NOT_FOUND_ID, STEAK_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDishNotBelongsToRestaurant() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(String.format(REST_URL_FORMAT_WITH_TWO_ID, RestaurantTestData.PANCAKES_ID, STEAK_ID))
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
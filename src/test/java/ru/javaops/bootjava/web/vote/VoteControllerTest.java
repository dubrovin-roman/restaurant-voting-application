package ru.javaops.bootjava.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.util.JsonUtil;
import ru.javaops.bootjava.util.VotesUtil;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.*;
import static ru.javaops.bootjava.web.vote.VoteController.EXCEPTION_VOTE_EXISTS;
import static ru.javaops.bootjava.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_ON_TODAY = VoteController.REST_URL + "/on-today";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVoteTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(VotesUtil.createTo(voteRepository.getExisted(newId)), newVoteTo);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWhenExists() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(EXCEPTION_VOTE_EXISTS)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithRestaurantNotExist() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNew();
        newVoteTo.setRestaurantId(RestaurantTestData.NOT_FOUND_ID);
        perform(MockMvcRequestBuilders.post(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(VoteController.REST_URL + "/" + VoteTestData.VOTE_ADMIN_ID_ON_TODAY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(vote_to_admin_on_today));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotBelong() throws Exception {
        perform(MockMvcRequestBuilders.get(VoteController.REST_URL + "/" + VoteTestData.VOTE_ADMIN_ID_ON_TODAY))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("not belong User")));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVoteSuccess() throws Exception {
        VoteTo reVoteTo = new VoteTo(null, LocalDate.now(), RestaurantTestData.ASTORIA_ID, 2);
        VotesUtil.prepareEndVoteTimeForPassTests();
        perform(MockMvcRequestBuilders.put(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(reVoteTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote vote = voteRepository.getExisted(VOTE_ADMIN_ID_ON_TODAY);
        assertEquals(RestaurantTestData.ASTORIA_ID, vote.getRestaurant().id());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void reVoteFail() throws Exception {
        VoteTo reVoteTo = new VoteTo(null, LocalDate.now(), RestaurantTestData.ASTORIA_ID, 2);
        VotesUtil.prepareEndVoteTimeForFailTests();
        perform(MockMvcRequestBuilders.put(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(reVoteTo)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(VoteController.EXCEPTION_VOTING_ERROR)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void reVoteNotExist() throws Exception {
        VoteTo reVoteTo = new VoteTo(null, LocalDate.now(), RestaurantTestData.ASTORIA_ID, 1);
        perform(MockMvcRequestBuilders.put(VoteController.REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(reVoteTo)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(VoteController.EXCEPTION_VOTE_NOT_EXISTS)));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_ON_TODAY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(vote_to_guest_on_today));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getOnTodayNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_ON_TODAY))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(VoteController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(voteToListByUser));
    }
}
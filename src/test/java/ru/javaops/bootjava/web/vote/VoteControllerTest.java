package ru.javaops.bootjava.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.GUEST_MAIL;
import static ru.javaops.bootjava.web.user.UserTestData.USER_MAIL;
import static ru.javaops.bootjava.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_ON_TODAY = VoteController.REST_URL + "/on-today";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getVoteOnToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_ON_TODAY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(vote_to_guest_on_today));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteOnTodayNotFound() throws Exception {
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
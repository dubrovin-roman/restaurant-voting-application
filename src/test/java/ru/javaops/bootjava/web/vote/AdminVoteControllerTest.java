package ru.javaops.bootjava.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.web.AbstractControllerTest;
import ru.javaops.bootjava.web.user.UserTestData;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.bootjava.web.vote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminVoteController.REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(voteToList));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminVoteController.REST_URL + "/by-user")
                .param("userId", String.valueOf(UserTestData.USER_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(voteToListByUser));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(AdminVoteController.REST_URL + "/" + VOTE_GUEST_ID_ON_TODAY))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(voteRepository.findById(VOTE_GUEST_ID_ON_TODAY).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(AdminVoteController.REST_URL + "/" + VOTE_ID_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void get() {
    }

    @Test
    void getByUserIdAndDate() {
    }
}
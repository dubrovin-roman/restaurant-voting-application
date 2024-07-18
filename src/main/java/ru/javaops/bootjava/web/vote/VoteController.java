package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.web.AuthUser;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/profile/votes";

    private VoteRepository voteRepository;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toVote(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        voteRepository.createVote(authUser.id(), id);
    }
}

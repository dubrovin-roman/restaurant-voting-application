package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository voteRepository;

    @GetMapping()
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "date_voting"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete by id: {}", id);
        voteRepository.deleteExisted(id);
    }

    @GetMapping("/{id}")
    public int get(@PathVariable int id) {
        log.info("get by id: {}", id);
        Integer restaurantId = voteRepository.getRestaurantIdByVoteId(id);
        if (restaurantId == null) {
            throw new NotFoundException("Vote with id=" + id + " not found");
        }
        return restaurantId;
    }
}

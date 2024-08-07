package ru.javaops.bootjava.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.util.VotesUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminVoteController {
    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository voteRepository;

    @GetMapping()
    @Cacheable("votes")
    public List<VoteTo> getAll() {
        log.info("getAll");
        return VotesUtil.createListTo(voteRepository.getAllWithEntityGraphAndOrderByDateVotingDesc());
    }

    @GetMapping("/by-user")
    @Cacheable("votes")
    public List<VoteTo> getAllByUserId(@RequestParam int userId) {
        log.info("getAllByUserId {}", userId);
        return VotesUtil.createListTo(voteRepository.getByUserId(userId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "votes", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete by id: {}", id);
        voteRepository.deleteExisted(id);
    }

    @GetMapping("/{id}")
    @Cacheable("votes")
    public VoteTo get(@PathVariable int id) {
        log.info("get by id: {}", id);
        return VotesUtil.createTo(voteRepository.getByVoteIdOrElseThrowNotFound(id));
    }

    @GetMapping("/by-user-date")
    @Cacheable("votes")
    public VoteTo getByUserIdAndDate(@RequestParam int userId,
                                     @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("getByUserIdAndDate: userId={}, date={}", userId, date);
        if (date == null) {
            date = LocalDate.now();
        }
        return VotesUtil.createTo(voteRepository.getByUserIdAndDateVotingOrElseThrowNotFound(userId, date));
    }
}

package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO vote (restaurant_id, user_id) VALUES (:restaurantId, :userId)")
    void createVoteToday(@Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.dateVoting = :date AND v.user.id = :userId")
    Optional<Vote> findByDateVotingAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);
}

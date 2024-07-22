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
    void createVote(@Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.restaurant.id = :restaurantId WHERE v.user.id = :userId AND v.dateVoting = :date")
    void updateVote(@Param("userId") int userId, @Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.dateVoting = :date AND v.user.id = :userId")
    Optional<Vote> findByDateVotingAndUserId(@Param("date") LocalDate date, @Param("userId") int userId);
}

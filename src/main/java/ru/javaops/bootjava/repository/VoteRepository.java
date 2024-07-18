package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO vote (restaurant_id, user_id) VALUES (:userId, :restaurantId)")
    void createVote(@Param("userId") int userId, @Param("restaurantId") int restaurantId);
}

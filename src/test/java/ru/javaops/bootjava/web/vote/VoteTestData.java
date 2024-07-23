package ru.javaops.bootjava.web.vote;

import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.web.MatcherFactory;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;
import ru.javaops.bootjava.web.user.UserTestData;

import java.time.LocalDate;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_ADMIN_ID = 1;

    public static final Vote vote_admin = new Vote(VOTE_ADMIN_ID, LocalDate.now(), RestaurantTestData.height, UserTestData.admin);
}

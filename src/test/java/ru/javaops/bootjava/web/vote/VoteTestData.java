package ru.javaops.bootjava.web.vote;

import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.web.MatcherFactory;
import ru.javaops.bootjava.web.restaurant.RestaurantTestData;
import ru.javaops.bootjava.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE_ADMIN_ID_ON_TODAY = 1;
    public static final int VOTE_GUEST_ID_ON_TODAY = 2;
    public static final int VOTE_ADMIN_ID_ON_2024_07_27 = 3;
    public static final int VOTE_ADMIN_ID_ON_2024_07_26 = 4;
    public static final int VOTE_ADMIN_ID_ON_2024_07_25 = 5;
    public static final int VOTE_USER_ID_ON_2024_07_27 = 6;
    public static final int VOTE_USER_ID_ON_2024_07_26 = 7;
    public static final int VOTE_USER_ID_ON_2024_07_25 = 8;
    public static final int VOTE_ID_NOT_FOUND = 400;

    public static final String DATE_2024_07_27 = "2024-07-27";
    public static final String DATE_2024_07_26 = "2024-07-26";
    public static final String DATE_2024_07_25 = "2024-07-25";

    public static final Vote vote_admin_on_today = new Vote(VOTE_ADMIN_ID_ON_TODAY, LocalDate.now(), RestaurantTestData.height, UserTestData.admin);
    public static final Vote vote_guest_on_today = new Vote(VOTE_GUEST_ID_ON_TODAY, LocalDate.now(), RestaurantTestData.pancakes, UserTestData.guest);
    public static final Vote vote_admin_on_2024_07_27 = new Vote(VOTE_ADMIN_ID_ON_2024_07_27, LocalDate.parse(DATE_2024_07_27), RestaurantTestData.height, UserTestData.admin);
    public static final Vote vote_admin_on_2024_07_26 = new Vote(VOTE_ADMIN_ID_ON_2024_07_26, LocalDate.parse(DATE_2024_07_26), RestaurantTestData.astoria, UserTestData.admin);
    public static final Vote vote_admin_on_2024_07_25 = new Vote(VOTE_ADMIN_ID_ON_2024_07_25, LocalDate.parse(DATE_2024_07_25), RestaurantTestData.height, UserTestData.admin);
    public static final Vote vote_user_on_2024_07_27 = new Vote(VOTE_USER_ID_ON_2024_07_27, LocalDate.parse(DATE_2024_07_27), RestaurantTestData.height, UserTestData.user);
    public static final Vote vote_user_on_2024_07_26 = new Vote(VOTE_USER_ID_ON_2024_07_26, LocalDate.parse(DATE_2024_07_26), RestaurantTestData.astoria, UserTestData.user);
    public static final Vote vote_user_on_2024_07_25 = new Vote(VOTE_USER_ID_ON_2024_07_25, LocalDate.parse(DATE_2024_07_25), RestaurantTestData.astoria, UserTestData.user);


    public static final VoteTo vote_to_admin_on_today = new VoteTo(VOTE_ADMIN_ID_ON_TODAY, LocalDate.now(), RestaurantTestData.HEIGHT_ID, UserTestData.ADMIN_ID);
    public static final VoteTo vote_to_guest_on_today = new VoteTo(VOTE_GUEST_ID_ON_TODAY, LocalDate.now(), RestaurantTestData.PANCAKES_ID, UserTestData.GUEST_ID);
    public static final VoteTo vote_to_admin_on_2024_07_27 = new VoteTo(VOTE_ADMIN_ID_ON_2024_07_27, LocalDate.parse(DATE_2024_07_27), RestaurantTestData.HEIGHT_ID, UserTestData.ADMIN_ID);
    public static final VoteTo vote_to_admin_on_2024_07_26 = new VoteTo(VOTE_ADMIN_ID_ON_2024_07_26, LocalDate.parse(DATE_2024_07_26), RestaurantTestData.ASTORIA_ID, UserTestData.ADMIN_ID);
    public static final VoteTo vote_to_admin_on_2024_07_25 = new VoteTo(VOTE_ADMIN_ID_ON_2024_07_25, LocalDate.parse(DATE_2024_07_25), RestaurantTestData.HEIGHT_ID, UserTestData.ADMIN_ID);
    public static final VoteTo vote_to_user_on_2024_07_27 = new VoteTo(VOTE_USER_ID_ON_2024_07_27, LocalDate.parse(DATE_2024_07_27), RestaurantTestData.HEIGHT_ID, UserTestData.USER_ID);
    public static final VoteTo vote_to_user_on_2024_07_26 = new VoteTo(VOTE_USER_ID_ON_2024_07_26, LocalDate.parse(DATE_2024_07_26), RestaurantTestData.ASTORIA_ID, UserTestData.USER_ID);
    public static final VoteTo vote_to_user_on_2024_07_25 = new VoteTo(VOTE_USER_ID_ON_2024_07_25, LocalDate.parse(DATE_2024_07_25), RestaurantTestData.ASTORIA_ID, UserTestData.USER_ID);

    public static final List<VoteTo> voteToList = List.of(vote_to_admin_on_today, vote_to_guest_on_today, vote_to_admin_on_2024_07_27,
            vote_to_user_on_2024_07_27, vote_to_admin_on_2024_07_26, vote_to_user_on_2024_07_26, vote_to_admin_on_2024_07_25,
            vote_to_user_on_2024_07_25);
    public static final List<VoteTo> voteToListByUser = List.of(vote_to_user_on_2024_07_27, vote_to_user_on_2024_07_26, vote_to_user_on_2024_07_25);
}

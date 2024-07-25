package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.to.VoteTo;

import java.time.LocalTime;
import java.util.List;

@UtilityClass
public class VotesUtil {
    private static LocalTime endVoteTime = LocalTime.of(11, 0);

    public static boolean isCanNotReVote() {
        return LocalTime.now().isAfter(endVoteTime);
    }

    public static void prepareEndVoteTimeForPassTests() {
        endVoteTime = LocalTime.now().plusMinutes(30);
    }

    public static void prepareEndVoteTimeForFailTests() {
        endVoteTime = LocalTime.now().minusMinutes(30);
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDateVoting(), vote.getRestaurant().id(), vote.getUser().id());
    }

    public static List<VoteTo> createListTo(List<Vote> votes) {
        return votes.stream()
                .map(VotesUtil::createTo)
                .toList();
    }
}

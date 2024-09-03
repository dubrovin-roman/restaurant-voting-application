package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.to.VoteTo;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class VotesUtil {
    private static LocalDateTime endVoteDateTime = LocalDateTime.now().withHour(11).withMinute(0);

    public static boolean isCanNotReVote() {
        return LocalDateTime.now().isAfter(endVoteDateTime);
    }

    public static void prepareEndVoteTimeForPassTests() {
        endVoteDateTime = LocalDateTime.now().plusMinutes(30);
    }

    public static void prepareEndVoteTimeForFailTests() {
        endVoteDateTime = LocalDateTime.now().minusMinutes(30);
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDateVoting(), vote.getRestaurant().id(), vote.getUser().id());
    }

    public static List<VoteTo> createListTo(List<Vote> votes) {
        return votes.stream()
                .map(VotesUtil::createTo)
                .toList();
    }

    public static List<VoteTo> createListToWithUserId(List<Vote> votes, int userId) {
        return votes.stream()
                .map(vote -> new VoteTo(vote.id(), vote.getDateVoting(), vote.getRestaurant().id(), userId))
                .toList();
    }
}

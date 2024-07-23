package ru.javaops.bootjava.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class VoteUtil {
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
}

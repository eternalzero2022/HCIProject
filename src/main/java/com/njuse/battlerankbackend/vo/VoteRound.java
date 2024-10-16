package com.njuse.battlerankbackend.vo;

import lombok.Data;

import java.util.List;

/**
 * The VoteRound class represents a single round in a voting process.
 * In each round, participants (items) are compared, and a winner is determined.
 * This class holds information about the round's identifier, the list of participants,
 * and the winner of the round.
 *
 * This is a non-persistent data structure. It exists in memory
 * only during the voting session.
 */
@Data
public class VoteRound {

    private Integer roundId;

    // List of items participating in this round of voting, length is 2
    private List<ItemVO> participants;

    // participants.at(winner) is the winner, it must be 0 or 1
    private Integer winner;
}

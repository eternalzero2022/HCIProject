package com.njuse.battlerankbackend.vo;

import lombok.Data;

import java.util.List;

/**
 * The VoteSession class represents a voting session for a user.
 * It tracks the current state of the voting process, including
 * the session identifier, the user participating in the session,
 * the current round of voting, and the list of rounds within the session.
 *
 * This is a non-persistent data structure. It exists in memory
 * only during the voting session.
 */
@Data
public class VoteSession {

    private Integer sessionId;

    private Integer userId;

    private CollectionVO collectionVO;

    // ID of the current round being voted on
    private Integer currentRoundId;

    // List of rounds that are part of this voting session
    private List<VoteRound> roundList;
}

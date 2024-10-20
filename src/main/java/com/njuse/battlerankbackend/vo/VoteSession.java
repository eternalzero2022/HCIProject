package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.serviceImpl.selectionStrategy.SelectionStrategy;
import lombok.Data;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private User user;

    private CollectionVO collectionVO;

    // ID of the current round being voted on
    private Integer currentRoundId;

    // List of rounds that are part of this voting session
    private List<VoteRound> roundList;

    private AtomicInteger waitForSubmit;

    private SelectionStrategy selectionStrategy;
}

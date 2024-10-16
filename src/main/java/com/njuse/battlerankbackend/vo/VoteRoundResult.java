package com.njuse.battlerankbackend.vo;

import lombok.Data;

@Data
public class VoteRoundResult {

    private Integer sessionId;

    private Integer roundId;

    private Integer winnerId;
}

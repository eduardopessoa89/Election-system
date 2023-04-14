package com.teste.pautateste.utils;

import com.teste.pautateste.service.VotingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FinishVotingTask implements Runnable{

    private Integer votingId;

    private VotingService votingService;

    public FinishVotingTask(Integer votingId, VotingService votingService) {
        this.votingId = votingId;
        this.votingService = votingService;
    }

    @Override
    public void run() {
        this.votingService.finishVoting(this.votingId);
    }
}

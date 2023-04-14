package com.teste.pautateste.service;

import com.teste.pautateste.model.Vote;
import com.teste.pautateste.model.VoteID;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.repository.VoteRepository;
import com.teste.pautateste.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository repository;

    private final VotingService votingService;

    public void validateVote(Vote vote) throws BusinessException {
        Voting voting = votingService.getVotingById(vote.getId().getVotingID());
        boolean userAlreadyVoted = !repository.findAllById(vote.getId()).isEmpty();
        if(voting.isFinished()) {
            throw new BusinessException("Voting Already end");
        } else {
            if(userAlreadyVoted) {
                throw new BusinessException("User Already voted");
            }
        }
    }

    public void voteFor(Vote vote) throws BusinessException {
        validateVote(vote);
        this.repository.save(vote);
    }

    public List<Vote> getVotesByVotingID(VoteID id) {
        return this.repository.findAllById(id);
    }
}

package com.teste.pautateste.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teste.pautateste.model.Stave;
import com.teste.pautateste.model.Vote;
import com.teste.pautateste.model.VoteID;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.repository.VoteRepository;
import com.teste.pautateste.exception.BusinessException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VoteService.class})
@ExtendWith(SpringExtension.class)
class VoteServiceTest {
    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @MockBean
    private VotingService votingService;

    @Test
    void testValidateVote() throws BusinessException {
        when(this.votingService.getVotingById((Integer) any())).thenReturn(new Voting());
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        Voting voting = new Voting();
        vote.setVoting(voting);
        this.voteService.validateVote(vote);
        verify(this.votingService).getVotingById((Integer) any());
        verify(this.voteRepository).findAllById((VoteID) any());
        assertSame(voteID, vote.getId());
        assertSame(voting, vote.getVoting());
        assertTrue(vote.getValue());
    }

    @Test
    void testValidateVote2() throws BusinessException {
        Stave stave = new Stave();
        LocalDateTime startedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime finishedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        when(this.votingService.getVotingById((Integer) any()))
                .thenReturn(new Voting(1, stave, true, startedAt, finishedAt, new ArrayList<>()));
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.validateVote(vote));
        verify(this.votingService).getVotingById((Integer) any());
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testValidateVote4() throws BusinessException {
        Voting voting = mock(Voting.class);
        when(voting.isFinished()).thenReturn(true);
        when(this.votingService.getVotingById((Integer) any())).thenReturn(voting);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.validateVote(vote));
        verify(this.votingService).getVotingById((Integer) any());
        verify(voting).isFinished();
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testValidateVote5() throws BusinessException {
        Voting voting = mock(Voting.class);
        when(voting.isFinished()).thenReturn(true);
        when(this.votingService.getVotingById((Integer) any())).thenReturn(voting);

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());

        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(voteList);

        VoteID voteID1 = new VoteID();
        voteID1.setUserID(1);
        voteID1.setVotingID(1);

        Vote vote1 = new Vote();
        vote1.setId(voteID1);
        vote1.setValue(true);
        vote1.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.validateVote(vote1));
        verify(this.votingService).getVotingById((Integer) any());
        verify(voting).isFinished();
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testVoteFor() throws BusinessException {
        when(this.votingService.getVotingById((Integer) any())).thenReturn(new Voting());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID1 = new VoteID();
        voteID1.setUserID(1);
        voteID1.setVotingID(1);

        Vote vote1 = new Vote();
        vote1.setId(voteID1);
        vote1.setValue(true);
        Voting voting = new Voting();
        vote1.setVoting(voting);
        this.voteService.voteFor(vote1);
        verify(this.votingService).getVotingById((Integer) any());
        verify(this.voteRepository).save((Vote) any());
        verify(this.voteRepository).findAllById((VoteID) any());
        assertEquals(voteID, vote1.getId());
        assertSame(voting, vote1.getVoting());
        assertTrue(vote1.getValue());
    }

    @Test
    void testVoteFor2() throws BusinessException {
        Stave stave = new Stave();
        LocalDateTime startedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime finishedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        when(this.votingService.getVotingById((Integer) any()))
                .thenReturn(new Voting(1, stave, true, startedAt, finishedAt, new ArrayList<>()));

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID1 = new VoteID();
        voteID1.setUserID(1);
        voteID1.setVotingID(1);

        Vote vote1 = new Vote();
        vote1.setId(voteID1);
        vote1.setValue(true);
        vote1.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.voteFor(vote1));
        verify(this.votingService).getVotingById((Integer) any());
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testVoteFor4() throws BusinessException {
        Voting voting = mock(Voting.class);
        when(voting.isFinished()).thenReturn(true);
        when(this.votingService.getVotingById((Integer) any())).thenReturn(voting);

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(new ArrayList<>());

        VoteID voteID1 = new VoteID();
        voteID1.setUserID(1);
        voteID1.setVotingID(1);

        Vote vote1 = new Vote();
        vote1.setId(voteID1);
        vote1.setValue(true);
        vote1.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.voteFor(vote1));
        verify(this.votingService).getVotingById((Integer) any());
        verify(voting).isFinished();
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testVoteFor5() throws BusinessException {
        Voting voting = mock(Voting.class);
        when(voting.isFinished()).thenReturn(true);
        when(this.votingService.getVotingById((Integer) any())).thenReturn(voting);

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());

        VoteID voteID1 = new VoteID();
        voteID1.setUserID(1);
        voteID1.setVotingID(1);

        Vote vote1 = new Vote();
        vote1.setId(voteID1);
        vote1.setValue(true);
        vote1.setVoting(new Voting());

        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote1);
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(voteList);

        VoteID voteID2 = new VoteID();
        voteID2.setUserID(1);
        voteID2.setVotingID(1);

        Vote vote2 = new Vote();
        vote2.setId(voteID2);
        vote2.setValue(true);
        vote2.setVoting(new Voting());
        assertThrows(BusinessException.class, () -> this.voteService.voteFor(vote2));
        verify(this.votingService).getVotingById((Integer) any());
        verify(voting).isFinished();
        verify(this.voteRepository).findAllById((VoteID) any());
    }

    @Test
    void testGetVotesByVotingID() {
        ArrayList<Vote> voteList = new ArrayList<>();
        when(this.voteRepository.findAllById((VoteID) any())).thenReturn(voteList);

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);
        List<Vote> actualVotesByVotingID = this.voteService.getVotesByVotingID(voteID);
        assertSame(voteList, actualVotesByVotingID);
        assertTrue(actualVotesByVotingID.isEmpty());
        verify(this.voteRepository).findAllById((VoteID) any());
    }
}


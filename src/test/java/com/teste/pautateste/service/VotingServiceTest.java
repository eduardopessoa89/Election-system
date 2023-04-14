package com.teste.pautateste.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teste.pautateste.dto.NewVotingDto;
import com.teste.pautateste.dto.StaveDto;
import com.teste.pautateste.dto.VotingResultDto;
import com.teste.pautateste.exception.BusinessException;
import com.teste.pautateste.model.Stave;
import com.teste.pautateste.model.Vote;
import com.teste.pautateste.model.VoteID;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.repository.VotingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VotingService.class, ThreadPoolTaskScheduler.class})
@ExtendWith(SpringExtension.class)
class VotingServiceTest {
    @MockBean
    private StaveService staveService;

    @MockBean
    private VotingRepository votingRepository;

    @Autowired
    private VotingService votingService;

    @Test
    void testValidateInsert() throws BusinessException {
        when(this.votingRepository.existsVotingByStave_Id((Integer) any())).thenReturn(true);

        NewVotingDto newVotingDto = new NewVotingDto();
        newVotingDto.setDuration(1);
        newVotingDto.setStaveID(1);
        assertThrows(BusinessException.class, () -> this.votingService.validateInsert(newVotingDto));
        verify(this.votingRepository).existsVotingByStave_Id((Integer) any());
    }

    @Test
    void testValidateInsert2() throws BusinessException {
        when(this.votingRepository.existsVotingByStave_Id((Integer) any())).thenReturn(false);

        NewVotingDto newVotingDto = new NewVotingDto();
        newVotingDto.setDuration(1);
        newVotingDto.setStaveID(1);
        this.votingService.validateInsert(newVotingDto);
        verify(this.votingRepository).existsVotingByStave_Id((Integer) any());
        assertEquals(1, newVotingDto.getDuration().intValue());
        assertEquals(1, newVotingDto.getStaveID().intValue());
    }

    @Test
    void testStart() throws BusinessException {
        when(this.votingRepository.existsVotingByStave_Id((Integer) any())).thenReturn(true);
        when(this.votingRepository.save((Voting) any())).thenReturn(new Voting());

        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());
        when(this.staveService.getById((Integer) any())).thenReturn(stave);

        NewVotingDto newVotingDto = new NewVotingDto();
        newVotingDto.setDuration(1);
        newVotingDto.setStaveID(1);
        assertThrows(BusinessException.class, () -> this.votingService.start(newVotingDto));
        verify(this.votingRepository).existsVotingByStave_Id((Integer) any());
        verify(this.staveService).getById((Integer) any());
    }

    @Test
    void testFinishVoting() {
        when(this.votingRepository.save((Voting) any())).thenReturn(new Voting());
        when(this.votingRepository.findById((Integer) any())).thenReturn(Optional.of(new Voting()));
        this.votingService.finishVoting(123);
        verify(this.votingRepository).save((Voting) any());
        verify(this.votingRepository).findById((Integer) any());
    }

    @Test
    void testFinishVoting2() {
        Voting voting = mock(Voting.class);
        doNothing().when(voting).setFinished(anyBoolean());
        doNothing().when(voting).setFinishedAt((java.time.LocalDateTime) any());
        Optional<Voting> ofResult = Optional.of(voting);
        when(this.votingRepository.save((Voting) any())).thenReturn(new Voting());
        when(this.votingRepository.findById((Integer) any())).thenReturn(ofResult);
        this.votingService.finishVoting(123);
        verify(this.votingRepository).save((Voting) any());
        verify(this.votingRepository).findById((Integer) any());
        verify(voting).setFinished(anyBoolean());
        verify(voting).setFinishedAt((java.time.LocalDateTime) any());
    }

    @Test
    void testGetVotingById() {
        Voting voting = new Voting();
        when(this.votingRepository.findById((Integer) any())).thenReturn(Optional.of(voting));
        assertSame(voting, this.votingService.getVotingById(1));
        verify(this.votingRepository).findById((Integer) any());
    }

    @Test
    void testGetVotingResult2() {
        Stave stave = new Stave();
        LocalDateTime startedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime finishedAt = LocalDateTime.of(1, 1, 1, 1, 1);
        when(this.votingRepository.findById((Integer) any()))
                .thenReturn(Optional.of(new Voting(1, stave, true, startedAt, finishedAt, new ArrayList<>())));
        VotingResultDto actualVotingResult = this.votingService.getVotingResult(1);
        assertEquals(0, actualVotingResult.getAgainst().intValue());
        assertEquals(0, actualVotingResult.getTotal().intValue());
        assertEquals(0, actualVotingResult.getInFavor().intValue());
        StaveDto stave1 = actualVotingResult.getStave();
        assertNull(stave1.getName());
        assertNull(stave1.getId());
        verify(this.votingRepository).findById((Integer) any());
    }

    @Test
    void testGetVotingResult3() {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());
        Voting voting = mock(Voting.class);
        when(voting.getStave()).thenReturn(stave);
        when(voting.getVotes()).thenReturn(new ArrayList<>());
        Optional<Voting> ofResult = Optional.of(voting);
        when(this.votingRepository.findById((Integer) any())).thenReturn(ofResult);
        VotingResultDto actualVotingResult = this.votingService.getVotingResult(1);
        assertEquals(0, actualVotingResult.getAgainst().intValue());
        assertEquals(0, actualVotingResult.getTotal().intValue());
        assertEquals(0, actualVotingResult.getInFavor().intValue());
        StaveDto stave1 = actualVotingResult.getStave();
        assertEquals("Name", stave1.getName());
        assertEquals(1, stave1.getId().intValue());
        verify(this.votingRepository).findById((Integer) any());
        verify(voting).getStave();
        verify(voting, atLeast(1)).getVotes();
    }

    @Test
    void testGetVotingResult4() {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);

        Vote vote = new Vote();
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());

        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
        Voting voting = mock(Voting.class);
        when(voting.getStave()).thenReturn(stave);
        when(voting.getVotes()).thenReturn(voteList);
        Optional<Voting> ofResult = Optional.of(voting);
        when(this.votingRepository.findById((Integer) any())).thenReturn(ofResult);
        VotingResultDto actualVotingResult = this.votingService.getVotingResult(1);
        assertEquals(0, actualVotingResult.getAgainst().intValue());
        assertEquals(1, actualVotingResult.getTotal().intValue());
        assertEquals(1, actualVotingResult.getInFavor().intValue());
        StaveDto stave1 = actualVotingResult.getStave();
        assertEquals("Name", stave1.getName());
        assertEquals(1, stave1.getId().intValue());
        verify(this.votingRepository).findById((Integer) any());
        verify(voting).getStave();
        verify(voting, atLeast(1)).getVotes();
    }

    @Test
    void testGetVotingResult6() {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());

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
        voteList.add(vote);
        Voting voting = mock(Voting.class);
        when(voting.getStave()).thenReturn(stave);
        when(voting.getVotes()).thenReturn(voteList);
        Optional<Voting> ofResult = Optional.of(voting);
        when(this.votingRepository.findById((Integer) any())).thenReturn(ofResult);
        VotingResultDto actualVotingResult = this.votingService.getVotingResult(1);
        assertEquals(0, actualVotingResult.getAgainst().intValue());
        assertEquals(2, actualVotingResult.getTotal().intValue());
        assertEquals(2, actualVotingResult.getInFavor().intValue());
        StaveDto stave1 = actualVotingResult.getStave();
        assertEquals("Name", stave1.getName());
        assertEquals(1, stave1.getId().intValue());
        verify(this.votingRepository).findById((Integer) any());
        verify(voting).getStave();
        verify(voting, atLeast(1)).getVotes();
    }

    @Test
    void testGetVotingResult7() {
        Stave stave = new Stave();
        stave.setId(1);
        stave.setName("Name");
        stave.setVoting(new Voting());

        VoteID voteID = new VoteID();
        voteID.setUserID(1);
        voteID.setVotingID(1);
        Vote vote = mock(Vote.class);
        when(vote.getValue()).thenReturn(false);
        doNothing().when(vote).setId((VoteID) any());
        doNothing().when(vote).setValue((Boolean) any());
        doNothing().when(vote).setVoting((Voting) any());
        vote.setId(voteID);
        vote.setValue(true);
        vote.setVoting(new Voting());

        ArrayList<Vote> voteList = new ArrayList<>();
        voteList.add(vote);
        Voting voting = mock(Voting.class);
        when(voting.getStave()).thenReturn(stave);
        when(voting.getVotes()).thenReturn(voteList);
        Optional<Voting> ofResult = Optional.of(voting);
        when(this.votingRepository.findById((Integer) any())).thenReturn(ofResult);
        VotingResultDto actualVotingResult = this.votingService.getVotingResult(1);
        assertEquals(1, actualVotingResult.getAgainst().intValue());
        assertEquals(1, actualVotingResult.getTotal().intValue());
        assertEquals(0, actualVotingResult.getInFavor().intValue());
        StaveDto stave1 = actualVotingResult.getStave();
        assertEquals("Name", stave1.getName());
        assertEquals(1, stave1.getId().intValue());
        verify(this.votingRepository).findById((Integer) any());
        verify(voting).getStave();
        verify(voting, atLeast(1)).getVotes();
        verify(vote).getValue();
        verify(vote).setId((VoteID) any());
        verify(vote).setValue((Boolean) any());
        verify(vote).setVoting((Voting) any());
    }
}


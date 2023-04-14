package com.teste.pautateste.service;

import com.teste.pautateste.dto.NewVotingDto;
import com.teste.pautateste.dto.StaveDto;
import com.teste.pautateste.dto.VotingResultDto;
import com.teste.pautateste.model.Stave;
import com.teste.pautateste.model.Voting;
import com.teste.pautateste.repository.VotingRepository;
import com.teste.pautateste.exception.BusinessException;
import com.teste.pautateste.utils.FinishVotingTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.teste.pautateste.utils.MapperUtil.convert;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository repository;

    private final StaveService staveService;

    private final ThreadPoolTaskScheduler scheduler;

    public void validateInsert(NewVotingDto newVotingDto) throws BusinessException {
        Boolean existsVoting = this.repository.existsVotingByStave_Id(newVotingDto.getStaveID());
        if (existsVoting) {
            throw new BusinessException("Voting already exists");
        }
    }

    public Voting start(NewVotingDto newVotingDto) throws BusinessException {
        Stave stave = staveService.getById(newVotingDto.getStaveID());
        Voting voting = Voting.builder()
                .stave(stave)
                .build();
        this.validateInsert(newVotingDto);
        voting = repository.save(voting);
        this.scheduler.schedule(new FinishVotingTask(voting.getId(), this),
                Instant.now().plus(newVotingDto.getDuration(), ChronoUnit.SECONDS));
        return voting;
    }

    public void finishVoting(Integer votingId) {
        Voting voting = this.repository.findById(votingId).orElseThrow();
        voting.setFinishedAt(LocalDateTime.now());
        voting.setFinished(true);
        this.repository.save(voting);
    }

    public Voting getVotingById(Integer votingID) {
        return this.repository.findById(votingID).orElseThrow();
    }

    public VotingResultDto getVotingResult(Integer id) {
        AtomicReference<Integer> inFavor = new AtomicReference<>(0);
        AtomicReference<Integer> against = new AtomicReference<>(0);
        Voting voting = this.repository.findById(id).orElseThrow();
        voting.getVotes().forEach(vote -> {
            if(vote.getValue()) {
                inFavor.updateAndGet(v -> v + 1);
            } else {
                against.updateAndGet(v -> v + 1);
            }
        });
        return VotingResultDto.builder()
                .against(against.get())
                .inFavor(inFavor.get())
                .total(voting.getVotes().size())
                .stave(convert(voting.getStave(), StaveDto.class))
                .build();
    }
}

package com.teste.pautateste.repository;

import com.teste.pautateste.model.Voting;
import org.springframework.data.repository.CrudRepository;

public interface VotingRepository extends CrudRepository<Voting, Integer> {

    Boolean existsVotingByStave_Id(Integer id);
}

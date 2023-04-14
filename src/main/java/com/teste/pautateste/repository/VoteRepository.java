package com.teste.pautateste.repository;

import com.teste.pautateste.model.Vote;
import com.teste.pautateste.model.VoteID;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, VoteID> {

    List<Vote> findAllById(VoteID voteID);
}

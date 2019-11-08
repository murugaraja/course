package com.cognizant.course.repositories;

import com.cognizant.course.domain.Participant;

import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Integer>{
}

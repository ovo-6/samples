package com.ovo6.poll.repo;

import com.ovo6.poll.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}

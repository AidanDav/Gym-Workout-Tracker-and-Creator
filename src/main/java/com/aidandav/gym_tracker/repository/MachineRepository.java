package com.aidandav.gym_tracker.repository;

import com.aidandav.gym_tracker.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//Machine is the entity type, Long is the type of its @Id field. 
//This one line alone gives you save(), findAll(), findById(), deleteById(), and more, with zero code written
public interface MachineRepository extends JpaRepository<Machine, Long> {
    //this is Spring Data JPA's "query derivation" feature: it parses the method name itself and builds the SQL query from it.
    List<Machine> findByNameContainingIgnoreCase(String name);
}
package com.aidandav.gym_tracker.repository;

import com.aidandav.gym_tracker.entity.Gym;
import com.aidandav.gym_tracker.entity.Machine;
import com.aidandav.gym_tracker.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GymRepository extends JpaRepository<Gym, Long> {
//walks the many-to-many relationship from Gym to its Machines
//then walks each machine's muscle groups (the @ElementCollection that is set up)
    @Query("SELECT DISTINCT m FROM Gym g JOIN g.machines m JOIN m.muscleGroups mg " +
           "WHERE g.id = :gymId AND mg IN :muscleGroups")
           //@Param — binds your Java method parameters to the :gymId and :muscleGroups placeholders in the query string, safely
    List<Machine> findAvailableMachines(@Param("gymId") Long gymId,
                                         @Param("muscleGroups") Set<MuscleGroup> muscleGroups);
}

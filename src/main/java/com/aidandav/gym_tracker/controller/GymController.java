package com.aidandav.gym_tracker.controller;

import com.aidandav.gym_tracker.entity.Gym;
import com.aidandav.gym_tracker.entity.Machine;
import com.aidandav.gym_tracker.entity.MuscleGroup;
import com.aidandav.gym_tracker.repository.GymRepository;
import com.aidandav.gym_tracker.repository.MachineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/gyms")
public class GymController {

    private final GymRepository gymRepository;
    private final MachineRepository machineRepository;

    public GymController(GymRepository gymRepository, MachineRepository machineRepository) {
        this.gymRepository = gymRepository;
        this.machineRepository = machineRepository;
    }

    @GetMapping
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    @PostMapping
    public Gym createGym(@RequestBody Gym gym) {
        return gymRepository.save(gym);
    }

    // This is what the "checkbox" UI calls when a user checks a machine for their gym
    @PostMapping("/{gymId}/machines/{machineId}")
    public Gym addMachineToGym(@PathVariable Long gymId, @PathVariable Long machineId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found: " + gymId));
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Machine not found: " + machineId));
        gym.addMachine(machine);
        return gymRepository.save(gym);
    }
    //@PathVariable — pulls values straight out of the URL path (e.g. {gymId} in /api/gyms/3/machines/7 becomes gymId = 3L)
    @DeleteMapping("/{gymId}/machines/{machineId}")
    public Gym removeMachineFromGym(@PathVariable Long gymId, @PathVariable Long machineId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found: " + gymId));
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new IllegalArgumentException("Machine not found: " + machineId));
        gym.removeMachine(machine);
        return gymRepository.save(gym);
    }

    // This is the workout-plan generator entry point
    @GetMapping("/{gymId}/available-machines")
    //directly calls the JPQL query you wrote in GymRepository, and it's the real entry point for 
    //"give me machines at this gym for these muscle groups." This is your workout-plan generator's foundation.
    public List<Machine> getAvailableMachines(@PathVariable Long gymId,
                                               @RequestParam Set<MuscleGroup> muscleGroups) {
        return gymRepository.findAvailableMachines(gymId, muscleGroups);
    }
}

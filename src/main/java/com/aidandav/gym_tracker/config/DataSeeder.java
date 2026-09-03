package com.aidandav.gym_tracker.config;

import com.aidandav.gym_tracker.entity.EquipmentType;
import com.aidandav.gym_tracker.entity.Machine;
import com.aidandav.gym_tracker.entity.MuscleGroup;
import com.aidandav.gym_tracker.repository.MachineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.aidandav.gym_tracker.entity.EquipmentType.*;
import static com.aidandav.gym_tracker.entity.MuscleGroup.*;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MachineRepository machineRepository;

    public DataSeeder(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public void run(String... args) {
        if (machineRepository.count() > 0) {
            return; // already seeded, don't duplicate on every restart
        }
        //Chest and Arms
        seed("Chest Press Machine", MACHINE, CHEST, TRICEPS, SHOULDERS);
        seed("Chest Fly Machine", MACHINE, CHEST);
        seed("Bench Press", FREE_WEIGHT, CHEST, TRICEPS, SHOULDERS);
        seed("Incline Bench Press", FREE_WEIGHT, CHEST, BICEPS, SHOULDERS);
        seed("Decline Bench Press", FREE_WEIGHT, TRICEPS, SHOULDERS, CHEST);
        seed("Cable Crossover Machine", CABLE, CHEST, SHOULDERS, BACK);
        seed("Seated Dip Machine", MACHINE, TRICEPS);
        seed("Preacher Curl Bench", BENCH, BICEPS);
        seed("Arm Curl Machine", MACHINE, BICEPS);
        seed("Arm Extension Machine", MACHINE, TRICEPS);
        seed("Tricep Press Machine", MACHINE, TRICEPS);
        seed("Tricep Extension Machine", MACHINE, TRICEPS);

        // Shoulders
        seed("Shoulder Press Machine", MACHINE, TRICEPS, SHOULDERS);
        seed("Overhead Press Machine", MACHINE, TRICEPS, SHOULDERS);
        seed("Lateral Raise Machine", MACHINE, SHOULDERS);

        // Back
        seed("Back Extension Machine", MACHINE, BACK, CORE);
        seed("Cable Row Machine", CABLE, BACK, BICEPS);
        seed("Lat Pulldown Machine", CABLE, BACK, BICEPS);
        seed("GHD Machine", MACHINE, CORE, HAMSTRINGS, GLUTES);
        seed("Front Pull Down Machine", CABLE, BACK, BICEPS);

        // Core
        seed("Ab Crunch Machine", MACHINE, CORE);
        seed("Rotary Torso Machine", MACHINE, CORE);

        // Legs
        seed("Leg Press Machine", MACHINE, QUADS, GLUTES, HAMSTRINGS, CALVES);
        seed("Leg Extension Machine", MACHINE, QUADS);
        seed("Leg Curl Machine", MACHINE, HAMSTRINGS);
        seed("Leg Abduction Machine", MACHINE, GLUTES);
        seed("Seated Calf Machine", MACHINE, CALVES);
        seed("Standing Calf Machine", MACHINE, CALVES);
        seed("Calf Press Machine", MACHINE, CALVES);
        seed("Hack Squat Machine", MACHINE, QUADS, GLUTES, HAMSTRINGS);
        seed("Reverse Hyper Machine", MACHINE, BACK, GLUTES, HAMSTRINGS);

        // Home gym / multi-station
        seed("Smith Machine", MACHINE, BACK, GLUTES, HAMSTRINGS, QUADS, CHEST, TRICEPS);
        seed("Functional Trainer", CABLE, CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, CORE);
    }

    private void seed(String name, EquipmentType type, MuscleGroup... groups) {
        machineRepository.save(new Machine(name, type, Set.of(groups)));
    }
}

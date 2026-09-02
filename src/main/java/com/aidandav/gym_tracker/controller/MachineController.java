package com.aidandav.gym_tracker.controller;

import com.aidandav.gym_tracker.entity.Machine;
import com.aidandav.gym_tracker.repository.MachineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//combines @Controller + @ResponseBody, meaning every method's return value gets 
//serialized straight to JSON in the HTTP response, rather than resolving to an HTML view
@RestController
//base path for every endpoint in this class Constructor injection
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineRepository machineRepository;

    public MachineController(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }
    //maps to GET /api/machines, with an optional ?search= query param wired to the repository method you just built
    @GetMapping
    public List<Machine> getAllMachines(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return machineRepository.findByNameContainingIgnoreCase(search);
        }
        return machineRepository.findAll();
    }
    //maps to POST /api/machines; @RequestBody tells Spring to deserialize the incoming JSON request body directly into a Machine object
    @PostMapping
    public Machine createMachine(@RequestBody Machine machine) {
        return machineRepository.save(machine);
    }
}
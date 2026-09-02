package com.aidandav.gym_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gym")
@Getter
@Setter
@NoArgsConstructor
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;
    //@JoinTable explicitly defines the gym_machine table: joinColumns is this entity's foreign key (gym_id), 
    // inverseJoinColumns is the other entity's foreign key (machine_id). Hibernate creates this table automatically based on this annotation
    @ManyToMany
    @JoinTable(
            name = "gym_machine",
            joinColumns = @JoinColumn(name = "gym_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private Set<Machine> machines = new HashSet<>();
    //Instead of every controller doing gym.getMachines().add(machine) directly, 
    //these give you one clean place to add logic later (e.g. validation, logging) if needed.
    public void addMachine(Machine machine) {
        machines.add(machine);
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
    }
}
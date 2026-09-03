package com.aidandav.gym_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


//tells Hibernate/JPA this class maps to a database table
@Entity
//explicit table name (otherwise it'd default to the class name)
@Table(name = "machine")
//Lombok annotations that auto-generate getters, setters, 
//and a no-arg constructor at compile time, saving you from writing that boilerplate by hand
@Getter
@Setter
@NoArgsConstructor
public class Machine {
    //primary key, auto-incremented by the DB (Postgres's SERIAL/BIGSERIAL under the hood)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //enforces at the DB level that every machine has a name and no two machines share one
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    //since a Set<MuscleGroup> isn't itself an entity (just a collection of enum values), this tells JPA to create a separate side table
    //(machine_muscle_group) to store the one-to-many relationship between a machine and its muscle groups
    @ElementCollection(targetClass = MuscleGroup.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "machine_muscle_group", joinColumns = @JoinColumn(name = "machine_id"))
    @Column(name = "muscle_group")
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;

    public Machine(String name, EquipmentType equipmentType, Set<MuscleGroup> muscleGroups) {
        this.name = name;
        this.equipmentType = equipmentType;
        this.muscleGroups = muscleGroups;
    }
}



package com.arturfrimu.exercisesback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "type")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    private ExerciseConfigurationEntity exerciseConfiguration;

    public TypeEntity(String type, ExerciseConfigurationEntity exerciseConfiguration) {
        this.type = type;
        this.exerciseConfiguration = exerciseConfiguration;
    }
}

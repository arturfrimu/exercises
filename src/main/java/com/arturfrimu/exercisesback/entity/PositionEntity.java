package com.arturfrimu.exercisesback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "position")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String position;

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    private ExerciseConfigurationEntity exerciseConfiguration;

    public PositionEntity(String position, ExerciseConfigurationEntity exerciseConfiguration) {
        this.position = position;
        this.exerciseConfiguration = exerciseConfiguration;
    }
}

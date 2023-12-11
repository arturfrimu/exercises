package com.arturfrimu.exercisesback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "exercise_configuration")
public class ExerciseConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int min;
    private int max;

    @OneToMany(mappedBy = "exerciseConfiguration", cascade = CascadeType.ALL)
    private List<PositionEntity> positions;

    @OneToMany(mappedBy = "exerciseConfiguration", cascade = CascadeType.ALL)
    private List<TypeEntity> types;

    public ExerciseConfigurationEntity(int id, int min, int max) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.positions = new ArrayList<>();
        this.types = new ArrayList<>();
    }
}

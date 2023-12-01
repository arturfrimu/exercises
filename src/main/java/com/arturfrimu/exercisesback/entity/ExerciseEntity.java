package com.arturfrimu.exercisesback.entity;

import com.arturfrimu.exercisesback.enumeration.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "exercises")
public class ExerciseEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "expression")
    private String expression;
    @Column(name = "result")
    private String result;
    @Column(name = "status")
    private Status status;

    public ExerciseEntity(UUID exerciseId, String expression, String result,Status status) {
        this.id = exerciseId;
        this.expression = expression;
        this.result = result;
        this.status = status;
    }
}

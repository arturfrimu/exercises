package com.arturfrimu.exercisesback.entity;

import com.arturfrimu.exercisesback.enumeration.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean verify(final String requestResult) {
        return this.result.equals(requestResult);
    }
    public ExerciseEntity markAs(Status status) {
        return new ExerciseEntity(id, expression, result, status);
    }
}

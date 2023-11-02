package com.arturfrimu.exercisesback.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Setter
@Repository
public class AbcRepository {

    private Abc abc = new Abc(List.of("sum"), List.of("center"), new Range(1, 10));

    public record Abc(
            List<String> type,
            List<String> position,
            Range range
    ) {
    }

    public record CurrentAbc(
            String type,
            String position,
            Integer min,
            Integer max
    ) {
    }

    public record Range(int min, int max) {
    }
}

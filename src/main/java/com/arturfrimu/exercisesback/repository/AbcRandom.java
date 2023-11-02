package com.arturfrimu.exercisesback.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AbcRandom {

    private final AbcRepository abcRepository;

    public AbcRepository.CurrentAbc getCurrentAbc() {
        AbcRepository.Abc abc = abcRepository.getAbc();

        String type = abc.type().get(new Random().nextInt(0, abc.type().size()));
        String position = abc.position().get(new Random().nextInt(0, abc.position().size()));
        int min = abc.range().min();
        int max = abc.range().max();

        return new AbcRepository.CurrentAbc(type, position, min, max);
    }
}

package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOInterface;
import com.arturfrimu.exercisesback.controller.enumeration.ExerciseComparison;
import com.arturfrimu.exercisesback.controller.enumeration.ExerciseSumPosition;
import com.arturfrimu.exercisesback.controller.enumeration.ExerciseType;
import com.arturfrimu.exercisesback.controller.enumeration.Status;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

import static com.arturfrimu.exercisesback.controller.enumeration.Status.*;
import static java.math.MathContext.DECIMAL32;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExerciseGenerationServiceImpl implements ExerciseGenerationService {
    private final RandomNumberGenerator<Integer> randomIntGenerator;

    private final AbcRepository abcRepository;

    @Override
    public void setConfiguration(AbcRepository.Abc newConfiguration) {
        abcRepository.setAbc(newConfiguration);
    }

    // TODO: 31.10.2023  Aceasta mapa la moment ne inlocuieste baza de date pentru exercitiile noastre
    // TODO: 31.10.2023 Nu este okay ca ea sa fie create in interiorul serviciului pentur ca vrem s-o putem apela si din alte servicii
    // TODO: 31.10.2023 Trebuie mutata in alta parte cum ar fi un Repository sau DAO pe care sa-l puntem injecta in servicii si sa folosim aceasta mapa
    private static final Map<UUID, Exercise> exercises = new HashMap<>();
    @Autowired
    private ExerciseDAOInterface exerciseDAO;

    private final AbcRandom abcRandom;

    @Override
    public ExerciseResponse generateExercise() {
        AbcRepository.CurrentAbc abc = abcRandom.getCurrentAbc();

        int min = abc.min();
        int max = abc.max();

        if (min > max) throw new RuntimeException("Min nu poate fi mai mare ca max");

        int first = randomIntGenerator.generate(min, max);
        int second = randomIntGenerator.generate(min, max);

        UUID exerciseId = UUID.randomUUID();

        while (exerciseDAO.getExercise(exerciseId) != null) {
            exerciseId = UUID.randomUUID();
        }

        int temp;

        if (ExerciseType.SUM.name().equalsIgnoreCase(abc.type())) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(abc.position())) {

                String sum = first + " + " + second + " = ?";
                String result = String.valueOf(first + second);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(abc.position())) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + ? = " + second;
                String result = String.valueOf(second - first);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(abc.position())) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = "? + " + first + " = " + second;
                String result = String.valueOf(second - first);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + abc.position());

            }

        } else if (ExerciseType.DIFFERENCE.name().equalsIgnoreCase(abc.type())) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(abc.position())) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - " + second + " = ?";
                String result = String.valueOf(first - second);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(abc.position())) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - ? = " + second;
                String result = String.valueOf(first - second);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(abc.position())) {

                String difference = "? - " + first + " = " + second;
                String result = String.valueOf(first + second);
                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + abc.position());

            }
        } else if (ExerciseType.MULTIPLICATION.name().equalsIgnoreCase(abc.type())) {

            String multiplication = first + " * " + second + " = ?";
            String result = String.valueOf(first * second);
            exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, multiplication, result, UNSOLVED));
            log.info("Exercise {} {} {}", exerciseId, multiplication, result);
            log.info("exercises {}", exerciseDAO);
            return new ExerciseResponse(exerciseId, multiplication, UNSOLVED);

        } else if (ExerciseType.DIVISION.name().equalsIgnoreCase(abc.type())) {

            if (first < second) {
                temp = first;
                first = second;
                second = temp;
            }

            String division = first + " / " + second + " = ?";
            String result = String.valueOf(first / second);
            exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, division, result, UNSOLVED));
            log.info("Exercise {} {} {}", exerciseId, division, result);
            log.info("exercises {}", exerciseDAO);
            return new ExerciseResponse(exerciseId, division, UNSOLVED);

        } else if (ExerciseType.COMPARISON.name().equalsIgnoreCase(abc.type())) {

            if (ExerciseComparison.ONE.name().equalsIgnoreCase(abc.position())) {
                String comparison = first + " ? " + second;

                String result = "=";

                if (first > second) {
                    result = ">";
                } else if (first < second) {
                    result = "<";
                }

                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, comparison, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, comparison, result);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, comparison, UNSOLVED);


            } else if (ExerciseComparison.TWO.name().equalsIgnoreCase(abc.position())) {

                int third = randomIntGenerator.generate(min, max);
                String comparison = first + " ? " + second + " ? " + third;

                String result1 = "=";
                String result2 = "=";

                if (first > second) {
                    result1 = ">";
                } else if (first < second) {
                    result1 = "<";
                }

                if (second > third) {
                    result2 = ">";
                } else if (second < third) {
                    result2 = "<";
                }

                String combinedResult = result1 + "|" + result2;

                exerciseDAO.putExercise(exerciseId, new Exercise(exerciseId, comparison, combinedResult, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, comparison, combinedResult);
                log.info("exercises {}", exerciseDAO);
                return new ExerciseResponse(exerciseId, comparison, UNSOLVED);
            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + abc.position());

            }
        } else {
            throw new RuntimeException("Poziția specificată nu este recunoscută: " + abc.position());

        }
    }

    @Override
    public boolean verifyExercise(VerifyRequest verifyRequest) {
        log.info("verifyRequest {}", verifyRequest);
        log.info("exercises {}", exerciseDAO);

        Exercise exercise = exerciseDAO.getExercise(verifyRequest.id());

        if (Objects.isNull(exercise)) {
            throw new ResourceNotFoundException("Exercise not found with id: %s".formatted(verifyRequest.id()));
        }

        boolean isCorrect = exercise.verify(verifyRequest.userInput());

        if (isCorrect) {
            Exercise correctExercise = exercise.markAs(CORRECT);
            exerciseDAO.putExercise(correctExercise.id(), correctExercise);
        } else {
            Exercise errorExercise = exercise.markAs(ERROR);
            exerciseDAO.putExercise(errorExercise.id(), errorExercise);
        }
        return isCorrect;
    }

    @Override
    public List<ExerciseResponse> getAllExercises() {
        List<Exercise> allExercises = exerciseDAO.getAllExercises();
        return allExercises.stream()
                .map(exercise -> new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()))
                .toList();
    }

    @Override
    public ExerciseResponse getExerciseById(UUID id) {
        Exercise exercise = exerciseDAO.getExercise(id);
        return new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status());
    }

    @Override
    public PercentageResponse getPercentage() {
        int exercisesSize = exerciseDAO.size();

        if (exercisesSize == 0) {
            return new PercentageResponse("0", "0", "0");
        }

        Map<Status, List<Exercise>> percentage = exerciseDAO.values()
                .stream()
                .collect(Collectors.groupingBy(
                        Exercise::status,
                        Collectors.mapping(
                                exercise -> exercise,
                                Collectors.toList())));

        BigDecimal totalPercentage = BigDecimal.valueOf(100);
        BigDecimal correctPercentage = BigDecimal.ZERO;
        BigDecimal errorPercentage = BigDecimal.ZERO;

        if (percentage.containsKey(CORRECT)) {
            int totalCorrectExercises = percentage.get(CORRECT).size();
            correctPercentage = BigDecimal.valueOf(totalCorrectExercises)
                    .multiply(totalPercentage)
                    .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);
        }

        if (percentage.containsKey(ERROR)) {
            int totalErrorExercises = percentage.get(ERROR).size();
            errorPercentage = BigDecimal.valueOf(totalErrorExercises)
                    .multiply(totalPercentage)
                    .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);
        }

        String correctExercisesPercent = correctPercentage.toString();
        String errorExercisesPercent = errorPercentage.toString();
        String unsolvedPercentage = totalPercentage.subtract(correctPercentage).subtract(errorPercentage).toString();

        return new PercentageResponse(correctExercisesPercent, errorExercisesPercent, unsolvedPercentage);
    }

    @Override
    // TODO: 30.10.2023 V-om sterge asta cand v-om adauga baza de date
    public void put(Map<UUID, Exercise> map) {
        exerciseDAO.putAll(map);
    }

    // TODO: 30.10.2023 V-om sterge asta cand v-om adauga baza de date
    @Override
    public void clear() {
        exerciseDAO.clear();
    }
}


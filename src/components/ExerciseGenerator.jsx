import React, {useState} from "react";
import {AdditionExerciseGenerator} from "./AdditionExerciseGenerator";

const ExerciseGenerator = () => {
    const [input, setInput] = useState("");
    const [result, setResult] = useState("");
    const [exercise, setExercise] = useState("");
    const [expectedResult, setExpectedResult] = useState(null);
    const [additionLevel, setAdditionLevel] = useState(1);
    const [subtractionLevel, setSubtractionLevel] = useState(1);

    const maxRandomNumber = 10;
    const minRandomNumber = 5;

    const handleChange = (event) => {
        setInput(event.target.value);
    }

    const changeLevelRandom = () => {
        const randomLevel = Math.floor(Math.random() * 2) + 1;
        setAdditionLevel(randomLevel);
        setSubtractionLevel(randomLevel);
    }

    const onSuccess = () => {
        setResult(`Correct! ${exercise.replace("X", input)}`);
        generateExercise()
        setInput("");

        // Change levels random
        changeLevelRandom()
    }

    const onError = () => {
        setResult(`Incorrect. ${exercise.replace("X", expectedResult)} ≠ ${input}. Try again.`);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        expectedResult === Number(input) ? onSuccess() : onError();
    }

    const getRandomNumber = () => {
        return Math.floor(Math.random() * (maxRandomNumber - minRandomNumber + 1) + minRandomNumber);
    }

    const addition = () => {
        const expected = new AdditionExerciseGenerator().addition(additionLevel);
        setExercise(expected.exercise)
        setExpectedResult(expected.result);
    }

    // ==================== SUBTRACTION ====================

    const subtractionLevel1 = () => {
        let randomNumber1 = getRandomNumber();
        let randomNumber2 = getRandomNumber();
        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1;
            randomNumber1 = randomNumber2;
            randomNumber2 = temp;
        }
        setExercise(`${randomNumber1} - ${randomNumber2} = X`);
        setExpectedResult(randomNumber1 - randomNumber2);
    }

    const subtractionLevel2 = () => {
        let randomNumber1 = getRandomNumber();
        let randomNumber2 = getRandomNumber();
        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1;
            randomNumber1 = randomNumber2;
            randomNumber2 = temp;
        }
        setExercise(`X = ${randomNumber1} - ${randomNumber2}`);
        setExpectedResult(randomNumber1 - randomNumber2);
    }

    const subtractionLevel3 = () => {
        let randomNumber1 = getRandomNumber();
        let randomNumber2 = getRandomNumber();
        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1;
            randomNumber1 = randomNumber2;
            randomNumber2 = temp;
        }
        setExercise(`${randomNumber1} - X = ${randomNumber2}`);
        setExpectedResult(randomNumber1 - randomNumber2);
    }

    const subtraction = () => {
        const subtractionLevels = [
            subtractionLevel1,
            subtractionLevel2,
            subtractionLevel3
        ];

        subtractionLevels[subtractionLevel]();
    }

    const generateExercise = () => {
        const idx = Math.floor(Math.random() * exercisesList.length);
        exercisesList[idx]();
    }

    const exercisesList = [addition, subtraction];

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <button onClick={generateExercise}>Generate Exercise</button>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{exercise}</div>
            <form style={{display: "flex", justifyContent: "center"}}
                  onSubmit={(e) => handleSubmit(e)}>
                <input type="text" value={input} onChange={handleChange} style={{margin: "0 10px"}}/>
                <button type="submit">Submit</button>
            </form>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{result}</div>
        </div>
    );
};

export default ExerciseGenerator;

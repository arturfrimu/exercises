import React, {useState} from "react";
import {AdditionExerciseGenerator} from "./AdditionExerciseGenerator";
import {SubtractionExerciseGenerator} from "./SubtractionExerciseGenerator";

const ExerciseGenerator = () => {
    const [input, setInput] = useState("");
    const [result, setResult] = useState("");
    const [exercise, setExercise] = useState("");
    const [expectedResult, setExpectedResult] = useState(null);
    const [additionLevel, setAdditionLevel] = useState(1);
    const [subtractionLevel, setSubtractionLevel] = useState(1);

    const handleChange = (event) => {
        setInput(event.target.value);
    }

    const changeLevelRandom = () => {
        const randomLevel = Math.floor(Math.random() * 3);
        console.log(randomLevel)
        setAdditionLevel(randomLevel);
        setSubtractionLevel(randomLevel);
    }

    const onSuccess = () => {
        setResult(`Correct! ${exercise.replace("X", input)}`);
        generateExercise()
        setInput("");

        changeLevelRandom()
    }

    const onError = () => {
        setResult(`Incorrect. ${exercise.replace("X", expectedResult)} ≠ ${input}. Try again.`);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        expectedResult === Number(input) ? onSuccess() : onError();
    }

    const addition = () => {
        const expected = new AdditionExerciseGenerator().addition(additionLevel);
        setExercise(expected.exercise)
        setExpectedResult(expected.result);
    }

    const subtraction = () => {
        const expected = new SubtractionExerciseGenerator().subtraction(subtractionLevel);
        setExercise(expected.exercise)
        setExpectedResult(expected.result);
    }

    const generateExercise = () => {
        changeLevelRandom()
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

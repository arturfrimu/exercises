import React, {useState} from "react";

const ExerciseGenerator = () => {
    let [input, setInput] = useState("");
    let [result, setResult] = useState("");
    let [exercise, setExercise] = useState("");
    let [expectedResult, setExpectedResult] = useState(null);

    function handleChange(event) {
        setInput(event.target.value);
    }

    function handleSubmit(event) {
        event.preventDefault();
        if (expectedResult === Number(input)) {
            setResult(`Correct! ${exercise.replace("X", input)}`);
            generateExercise()
            setInput("");
        } else {
            setResult(
                `Incorrect. ${exercise.replace(
                    "X",
                    expectedResult
                )} ≠ ${input}. Try again.`
            );
        }
    }

    function generateExercise() {
        let randomNumber1 = Math.floor(Math.random() * 10) + 1;
        let randomNumber2 = Math.floor(Math.random() * 10) + 1;
        let randomNumber3 = Math.floor(Math.random() * 10) + 1;
        let format = Math.floor(Math.random() * 3) + 1;

        switch (format) {
            case 1:
                setExercise(`${randomNumber1} + ${randomNumber2} = X`);
                setExpectedResult(randomNumber1 + randomNumber2);
                break;
            case 2:
                setExercise(`X = ${randomNumber2} + ${randomNumber3}`);
                setExpectedResult(randomNumber2 + randomNumber3);
                break;
            case 3:
                setExercise(`${randomNumber2} - ${randomNumber3} = X`);
                setExpectedResult(randomNumber2 - randomNumber3);
                break;
            default:
                setResult(`Error: Invalid format`);
        }
    }

    return (
        <div>
            <button onClick={generateExercise}>Generate Exercise</button>
            <div style={{fontSize: "20px", margin: "10px 0"}}>{exercise}</div>
            <form onSubmit={handleSubmit}>
                <input type="text" value={input} onChange={handleChange}/>
                <button type="submit">Submit</button>
            </form>
            <div style={{fontSize: "20px", margin: "10px 0"}}>{result}</div>
        </div>
    );
};

export default ExerciseGenerator;

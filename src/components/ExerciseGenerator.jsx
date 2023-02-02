import React, {useEffect, useState} from "react"
import {AdditionExerciseGenerator} from "./AdditionExerciseGenerator"
import {SubtractionExerciseGenerator} from "./SubtractionExerciseGenerator"
import {MultiplyExerciseGenerator} from "./MultiplyExerciseGenerator"
import {DivisionExerciseGenerator} from "./DivisionExerciseGenerator"

const ExerciseGenerator = () => {
    const [input, setInput] = useState("")
    const [result, setResult] = useState("")
    const [exercise, setExercise] = useState("")
    const [expectedResult, setExpectedResult] = useState(null)
    const [additionLevel, setAdditionLevel] = useState(1)
    const [subtractionLevel, setSubtractionLevel] = useState(1)
    const [multiplyLevel, setMultiplyLevel] = useState(1)
    const [divisionLevel, setDivisionLevel] = useState(1)
    const [resolvedExercises, setResolvedExercises] = useState(0)
    const [selectedLevel, setSelectedLevel] = useState("1")

    const changeLevelHandler = (event) => {
        setSelectedLevel(event.target.value)
    }

    useEffect(() => {
        const resolvedExercises = localStorage.getItem("resolvedExercises")
        if (resolvedExercises) {
            setResolvedExercises(parseInt(resolvedExercises, 10))
        }
    }, [])

    useEffect(() => {
        localStorage.setItem("resolvedExercises", resolvedExercises)
    }, [resolvedExercises])

    useEffect(() => {
        generateExercise()
    }, [])

    const handleChange = (event) => {
        setInput(event.target.value)
    }

    const changeLevelRandom = () => {
        const randomLevel = Math.floor(Math.random() * 3)
        setAdditionLevel(randomLevel)
        setSubtractionLevel(randomLevel)
        setMultiplyLevel(randomLevel)
        setDivisionLevel(randomLevel)
    }

    const onSuccess = () => {
        setResult(`Correct! ${exercise.replace("X", input)}`)
        generateExercise()
        setInput("")
        incrementResolvedExercises()
    }

    const onError = () => {
        setResult(`Incorrect. ${exercise.replace("X", expectedResult)} ≠ ${input}. Try again.`)
        decrementResolvedExercises()
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        expectedResult === Number(input) ? onSuccess() : onError()
    }

    // ============= STORAGE =============

    const incrementResolvedExercises = () => {
        setResolvedExercises(resolvedExercises + 1)
    }

    const decrementResolvedExercises = () => {
        if (resolvedExercises === 0) return
        setResolvedExercises(resolvedExercises - 1)
    }

    // ============= OPERATIONS =============

    const addition = () => {
        const expected = new AdditionExerciseGenerator().addition(additionLevel)
        setExercise(expected.exercise)
        setExpectedResult(expected.result)
    }

    const subtraction = () => {
        const expected = new SubtractionExerciseGenerator().subtraction(subtractionLevel)
        setExercise(expected.exercise)
        setExpectedResult(expected.result)
    }

    const multiplication = () => {
        const expected = new MultiplyExerciseGenerator().multiply(multiplyLevel)
        setExercise(expected.exercise)
        setExpectedResult(expected.result)
    }

    const division = () => {
        const expected = new DivisionExerciseGenerator().division(divisionLevel)
        setExercise(expected.exercise)
        setExpectedResult(expected.result)
    }

    const generateExercise = () => {
        if (parseInt(selectedLevel) === 1) {
            exercisesList[0]()
        } else if (parseInt(selectedLevel) === 2) {
            exercisesList[1]()
        } else if (parseInt(selectedLevel) === 3) {
            const idx = Math.floor(Math.random() * 2)
            exercisesList[idx]()
        } else if (parseInt(selectedLevel) === 4) {
            exercisesList[2]()
        } else if (parseInt(selectedLevel) === 5) {
            exercisesList[3]()
        } else if (parseInt(selectedLevel) === 6) {
            const idx = Math.floor(Math.random() * (3 - 2 + 1) + 2)
            exercisesList[idx]()
        }

        changeLevelRandom()
    }

    const exercisesList = [addition, subtraction, multiplication, division]

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <div style={{textAlign: 'center'}}>
                <label style={{fontWeight: 'bold', marginRight: '10px'}}>
                    Select a level:
                    <select value={selectedLevel} onChange={changeLevelHandler} style={{
                        padding: '10px',
                        fontSize: '16px',
                        marginBottom: '20px',
                    }}>
                        <option value="1">Adunarea</option>
                        <option value="2">Scaderea</option>
                        <option value="3">Adunarea / Scaderea</option>
                        <option value="4">Inmultirea</option>
                        <option value="5">Impartirea</option>
                        <option value="6">Inmultirea / Impartirea</option>
                    </select>
                </label>
            </div>
            <p style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>Exercitii
                corecte: {resolvedExercises}</p>
            <button onClick={generateExercise}>Generate Exercise</button>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{exercise}</div>
            <form style={{display: "flex", justifyContent: "center"}}
                  onSubmit={(e) => handleSubmit(e)}>
                <input type="text" value={input} onChange={handleChange} style={{margin: "0 10px"}} required/>
                <button type="submit">Submit</button>
            </form>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{result}</div>
        </div>
    )
}

export default ExerciseGenerator

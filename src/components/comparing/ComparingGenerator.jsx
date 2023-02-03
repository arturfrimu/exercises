import React, {useEffect, useState} from "react"

const ExerciseGenerator = () => {
    const [input, setInput] = useState("")
    const [result, setResult] = useState("")
    const [exercise, setExercise] = useState("")
    const [expectedResult, setExpectedResult] = useState(null)

    useEffect(() => {
        randomExercise()
    }, [])

    const handleChange = (value) => {
        setInput(value)
    }

    const onSuccess = () => {
        setResult(`Correct! ${exercise.replace("?", input)}`)
        randomExercise()
        setInput("")
    }

    const onError = () => {
        setResult(`Incorrect. ${exercise.replace("?", expectedResult)}`)
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        expectedResult === input ? onSuccess() : onError()
    }

    const randomExercise = () => {
        const randomNumber1 = Math.floor(Math.random() * (100) + 1)
        const randomNumber2 = Math.floor(Math.random() * (100) + 1)

        setExercise(`${randomNumber1} ? ${randomNumber2}`)
        if (randomNumber1 < randomNumber2) {
            setExpectedResult("<")
        } else if (randomNumber1 > randomNumber2) {
            setExpectedResult(">")
        } else if (randomNumber1 === randomNumber2) {
            setExpectedResult("=")
        }
    }

    const possibleAnswers = ["<", "=", ">"];

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <button onClick={randomExercise}>Generate Exercise</button>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{exercise}</div>
            <form style={{display: "flex", justifyContent: "center"}} onSubmit={(e) => handleSubmit(e)}>
                {possibleAnswers.map((value, index) => (
                    <button
                        style={{margin: "5px"}}
                        key={index}
                        onClick={() => handleChange(value)}
                    >
                        {value}
                    </button>
                ))}
            </form>
            <div style={{fontSize: "30px", margin: "10px 0", textAlign: "center"}}>{result}</div>
        </div>
    )
}

export default ExerciseGenerator

import React, { useEffect, useState } from "react"

const ExerciseGenerator = () => {
  const [input, setInput] = useState("")
  const [result, setResult] = useState("")
  const [exercise, setExercise] = useState("")
  const [expectedResult, setExpectedResult] = useState(null)

  useEffect(() => {
    randomExercise()
  }, [])

  const handleChange = value => {
    setInput(value)
  }

  const onSuccess = () => {
    setResult(`Correct! ${exercise.replace("?", input)}`)
    randomExercise()
    setInput("")
  }

  const onError = () => {
    setResult(`Incorrect! ${exercise.replace("?", expectedResult)}`)
  }

  const handleSubmit = event => {
    event.preventDefault()
    expectedResult === input ? onSuccess() : onError()
  }

  const randomExercise = () => {
    const randomNumber1 = Math.floor(Math.random() * 100 + 1)
    const randomNumber2 = Math.floor(Math.random() * 100 + 1)

    setExercise(`${randomNumber1} ? ${randomNumber2}`)
    if (randomNumber1 < randomNumber2) {
      setExpectedResult("<")
    } else if (randomNumber1 > randomNumber2) {
      setExpectedResult(">")
    } else if (randomNumber1 === randomNumber2) {
      setExpectedResult("=")
    }
  }

  const possibleAnswers = ["<", "=", ">"]

  return (
    <div className="d-flex flex-column align-items-center pt-4">
      <button onClick={randomExercise} className="btn btn-primary">
        Generate Exercise
      </button>
      <p className="lead display-6 my-3">{exercise}</p>
      <form onSubmit={e => handleSubmit(e)} className="d-flex gap-2">
        {possibleAnswers.map((value, index) => (
          <button
            className={`btn btn-${index === 1 ? "warning" : "primary"}`}
            key={index}
            onClick={() => handleChange(value)}
          >
            {value}
          </button>
        ))}
      </form>
      {result !== "" && (
        <div
          className={`alert alert-${
            result.includes("Correct") ? "success" : "danger"
          } mt-3`}
        >
          {result}
        </div>
      )}
    </div>
  )
}

export default ExerciseGenerator

import React, { useState } from "react"

const question = "Ce figura este ?"
const imagesAndCorrectAnswers = [
  {
    image: <div id="square" />,
    answer: "PATRAT",
  },
  {
    image: <div id="rectangle" />,
    answer: "DREPTUNGHI",
  },
  {
    image: <div id="circle" />,
    answer: "CERC",
  },
  {
    image: <div id="rhombus" />,
    answer: "ROMB",
  },
  {
    image: <div id="oval" />,
    answer: "OVAL",
  },
  {
    image: <div id="parallelogram" />,
    answer: "PARALELOGRAM",
  },
  {
    image: <div id="triangle" />,
    answer: "TRIUNGHI",
  },
]

const FigureGenerator = () => {
  const [ques, setQuest] = useState(imagesAndCorrectAnswers[0])

  const [selectedAnswer, setSelectedAnswer] = useState(null)
  const [isCorrect, setIsCorrect] = useState(null)

  const handleAnswerClick = answer => {
    setSelectedAnswer(answer)
    setIsCorrect(answer === ques.answer)
    if (answer === ques.answer) {
      let newImageAndCorrectAnswer =
        imagesAndCorrectAnswers[
          Math.floor(Math.random() * imagesAndCorrectAnswers.length)
        ]
      while (newImageAndCorrectAnswer.answer === answer) {
        newImageAndCorrectAnswer =
          imagesAndCorrectAnswers[
            Math.floor(Math.random() * imagesAndCorrectAnswers.length)
          ]
      }
      setQuest(newImageAndCorrectAnswer)
    }
  }

  return (
    <div className="d-flex flex-column align-items-center pt-4">
      {ques.image}
      <p className="lead my-2">{question}</p>

      <div className="d-flex gap-2">
        {imagesAndCorrectAnswers.map(({ answer }, index) => (
          <button
            key={index}
            onClick={() => handleAnswerClick(answer)}
            className="btn btn-primary"
          >
            {answer}
          </button>
        ))}
      </div>
      {selectedAnswer !== null && (
        <div
          // style={{ color: isCorrect ? "green" : "red" }}
          className={`alert alert-${isCorrect ? "success" : "danger"} mt-3`}
        >
          Răspunsul tău {isCorrect ? "este corect" : "este gresit"}.
        </div>
      )}
    </div>
  )
}

export default FigureGenerator

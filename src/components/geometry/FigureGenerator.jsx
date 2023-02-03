import React, {useState} from "react";
import PATRAT from "./foto/PATRAT.png";
import DREPTUNGHI from "./foto/DREPTUNGHI.jpeg";
import CERC from "./foto/CERC.png";
import ROMB from "./foto/ROMB.png";
import TRAPEZ from "./foto/TRAPEZ.png";

const question = "Ce figura este ?";
const imagesAndCorrectAnswers = [
    {
        image: PATRAT,
        answer: "PATRAT"
    },
    {
        image: DREPTUNGHI,
        answer: "DREPTUNGHI"
    },
    {
        image: CERC,
        answer: "CERC"
    },
    {
        image: ROMB,
        answer: "ROMB"
    },
    {
        image: TRAPEZ,
        answer: "TRAPEZ"
    },
];

const FigureGenerator = () => {
    const [ques, setQuest] = useState({
        image: PATRAT,
        answer: "PATRAT"
    });

    const [selectedAnswer, setSelectedAnswer] = useState(null);
    const [isCorrect, setIsCorrect] = useState(null);

    const handleAnswerClick = (answer) => {
        setSelectedAnswer(answer);
        setIsCorrect(answer === ques.answer);
        if (answer === ques.answer) {
            let newImageAndCorrectAnswer = imagesAndCorrectAnswers[Math.floor(Math.random() * 5)];
            while (newImageAndCorrectAnswer.answer === answer) {
                newImageAndCorrectAnswer = imagesAndCorrectAnswers[Math.floor(Math.random() * 5)];
            }
            setQuest(newImageAndCorrectAnswer)
        }
    };

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <img src={ques.image} alt="My Image" style={{width: "200px", height: "200px"}}/>
            <p>{question}</p>
            <div>
                {imagesAndCorrectAnswers.map(({answer}, index) => (
                    <button
                        style={{margin: "5px"}}
                        key={index}
                        onClick={() => handleAnswerClick(answer)}
                    >
                        {answer}
                    </button>
                ))}
            </div>
            {selectedAnswer !== null && (
                <h1 style={{color: isCorrect ? "green" : "red"}}>
                    Răspunsul tău {isCorrect ? "este corect" : "este incorect"}.
                </h1>
            )}
        </div>
    );
};

export default FigureGenerator;

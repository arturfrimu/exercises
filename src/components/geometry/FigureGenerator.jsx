import React, {useState} from "react";

const question = "Ce figura este ?";
const imagesAndCorrectAnswers = [
    {
        image: <div id="square"/>,
        answer: "PATRAT"
    },
    {
        image: <div id="rectangle"/>,
        answer: "DREPTUNGHI"
    },
    {
        image: <div id="circle"/>,
        answer: "CERC"
    },
    {
        image: <div id="rhombus"/>,
        answer: "ROMB"
    },
    {
        image: <div id="oval"/>,
        answer: "OVAL"
    },
    {
        image: <div id="parallelogram"/>,
        answer: "PARALELOGRAM"
    },
    {
        image: <div id="triangle"/>,
        answer: "TRIUNGHI"
    },
];

const FigureGenerator = () => {
    const [ques, setQuest] = useState(imagesAndCorrectAnswers[0]);

    const [selectedAnswer, setSelectedAnswer] = useState(null);
    const [isCorrect, setIsCorrect] = useState(null);

    const handleAnswerClick = (answer) => {
        setSelectedAnswer(answer);
        setIsCorrect(answer === ques.answer);
        if (answer === ques.answer) {
            let newImageAndCorrectAnswer = imagesAndCorrectAnswers[Math.floor(Math.random() * imagesAndCorrectAnswers.length)];
            while (newImageAndCorrectAnswer.answer === answer) {
                newImageAndCorrectAnswer = imagesAndCorrectAnswers[Math.floor(Math.random() * imagesAndCorrectAnswers.length)];
            }
            setQuest(newImageAndCorrectAnswer)
        }
    };


    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            {ques.image}
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

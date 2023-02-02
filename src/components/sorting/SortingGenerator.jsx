import React, {useEffect, useState} from "react";

const SortingGenerator = () => {
    const [numbers, setNumbers] = useState([]);
    const [draggedIndex, setDraggedIndex] = useState();
    const [sortDirection, setSortDirection] = useState(0);

    const changeSortDirectionHandler = (event) => {
        setSortDirection(parseInt(event.target.value))
    }

    useEffect(() => {
        generateNumbers()
    }, [])

    const generateNumbers = () => {
        setNumbers([
            Math.floor(Math.random() * 11),
            Math.floor(Math.random() * 11),
            Math.floor(Math.random() * 11),
            Math.floor(Math.random() * 11),
            Math.floor(Math.random() * 11),
            Math.floor(Math.random() * 11)
        ])
    }

    const onDragStart = (index) => {
        setDraggedIndex(index);
    };

    const onDragOver = (index) => {
        const draggedOverIndex = index;
        if (draggedOverIndex === draggedIndex) {
            return;
        }
        const newNumbers = [...numbers];
        const temp = newNumbers[draggedIndex];
        newNumbers[draggedIndex] = newNumbers[draggedOverIndex];
        newNumbers[draggedOverIndex] = temp;
        setNumbers(newNumbers);
        setDraggedIndex(draggedOverIndex);
    };


    const checkSortAscending = () => {
        for (let i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                return false;
            }
        }
        return true;
    };

    const checkSortDescending = () => {
        for (let i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] < numbers[i + 1]) {
                return false;
            }
        }
        return true;
    };

    const checkSort = () => {
        return sort[sortDirection]();
    }

    const sort = [checkSortAscending, checkSortDescending];

    return (
        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <h1>Numere generate:</h1>
            <div style={{textAlign: 'center'}}>
                <label style={{fontWeight: 'bold', marginRight: '10px'}}>
                    Selecteaza directia:
                    <select value={sortDirection} onChange={changeSortDirectionHandler} style={{
                        padding: '10px',
                        fontSize: '16px',
                        marginBottom: '20px',
                    }}>
                        <option value="0">Ascendent</option>
                        <option value="1">Descendent</option>
                    </select>
                </label>
            </div>
            <div style={{display: "flex", alignItems: "center", fontSize: "30px"}}>
                {numbers.map((number, index) => (
                    <div style={{
                        margin: "5px",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        textAlign: "center",
                        fontSize: "30px",
                        transition: "all 3s",
                        padding: "10px",
                        width: "30px",
                        height: "30px",
                        background: "lightblue"
                    }}
                         key={index}
                         draggable
                         onDragStart={() => onDragStart(index)}
                         onDragOver={() => onDragOver(index)}
                    >
                        {number}
                    </div>
                ))}
            </div>
            <button onClick={generateNumbers}>Genereaza alt sir de numere</button>
            <h2 style={{color: checkSort() ? "green" : "red"}}>
                {checkSort()
                    ? "Numerele sunt aranjate corect"
                    : "Numerele nu sunt aranjate corect"}
            </h2>
        </div>
    );
}

export default SortingGenerator;

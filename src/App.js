import React, {useState} from 'react';
import './App.css';
import ExerciseGenerator from "./components/mathematics/ExerciseGenerator";
import SortingGenerator from "./components/sorting/SortingGenerator";
import FigureGenerator from "./components/geometry/FigureGenerator";
import ComparingGenerator from "./components/comparing/ComparingGenerator";


function App() {

    const arr = [
        <ExerciseGenerator/>,
        <SortingGenerator/>,
        <FigureGenerator/>,
        <ComparingGenerator/>,
    ];

    const [select, setSelect] = useState(2);

    const changeSortDirectionHandler = (event) => {
        setSelect(parseInt(event.target.value))
    }

    return (
        <div>
            <div style={{textAlign: 'center'}}>
                <label style={{fontWeight: 'bold', marginRight: '10px'}}>
                    Selecteaza directia:
                    <select value={select} onChange={changeSortDirectionHandler} style={{
                        padding: '10px',
                        fontSize: '16px',
                        marginBottom: '20px',
                    }}>
                        <option value="0">Exercitii matematice</option>
                        <option value="1">Sortare</option>
                        <option value="2">Figuri geometrice</option>
                        <option value="3">Comparatii</option>
                    </select>
                </label>
            </div>
            {arr[select]}
        </div>
    )
}

export default App;

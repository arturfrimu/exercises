import React from 'react';
import './App.css';
import ExerciseGenerator from "./components/ExerciseGenerator";
import SortingGenerator from "./components/sorting/SortingGenerator";


function App() {
    return (
        <div>
            <ExerciseGenerator/>
            <SortingGenerator/>
        </div>
    )
}

export default App;

import React, { useState } from "react"
import ExerciseGenerator from "./components/mathematics/ExerciseGenerator"
import SortingGenerator from "./components/sorting/SortingGenerator"
import FigureGenerator from "./components/geometry/FigureGenerator"
import ComparingGenerator from "./components/comparing/ComparingGenerator"
import Menu from "./components/menu/Menu"

function App() {
  const [select, setSelect] = useState(0)

  const changeSortDirectionHandler = event => {
    setSelect(parseInt(event.target.value))
  }

  return (
    <div className="container">
      <div className="row">
        <div className="col-6">
          <Menu
            onChangeSortDirectionHandler={changeSortDirectionHandler}
            val={select}
          />
        </div>
        <div className="col-6">
          {select === 0 && <ExerciseGenerator />}
          {select === 1 && <SortingGenerator />}
          {select === 2 && <FigureGenerator />}
          {select === 3 && <ComparingGenerator />}
        </div>
      </div>
    </div>
  )
}

export default App

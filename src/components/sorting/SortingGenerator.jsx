import React, { useEffect, useState } from "react"

const SortingGenerator = () => {
  const [numbers, setNumbers] = useState([])
  const [draggedIndex, setDraggedIndex] = useState()
  const [sortDirection, setSortDirection] = useState(0)

  const changeSortDirectionHandler = event => {
    setSortDirection(parseInt(event.target.value))
  }

  useEffect(() => {
    generateNumbers()
  }, [])

  const generateNumbers = () => {
    const randomNums = []
    for (let i = 0; i <= 5; i++) {
      randomNums.push(Math.floor(Math.random() * 100))
    }
    setNumbers(randomNums)
  }

  const onDragStart = index => {
    setDraggedIndex(index)
  }

  const onDragOver = index => {
    const draggedOverIndex = index
    if (draggedOverIndex === draggedIndex) {
      return
    }
    const newNumbers = [...numbers]
    const temp = newNumbers[draggedIndex]
    newNumbers[draggedIndex] = newNumbers[draggedOverIndex]
    newNumbers[draggedOverIndex] = temp
    setNumbers(newNumbers)
    setDraggedIndex(draggedOverIndex)
  }

  const checkSortAscending = () => {
    for (let i = 0; i < numbers.length - 1; i++) {
      if (numbers[i] > numbers[i + 1]) {
        return false
      }
    }
    return true
  }

  const checkSortDescending = () => {
    for (let i = 0; i < numbers.length - 1; i++) {
      if (numbers[i] < numbers[i + 1]) {
        return false
      }
    }
    return true
  }

  const checkSort = () => {
    return sort[sortDirection]()
  }

  const sort = [checkSortAscending, checkSortDescending]

  return (
    <div className="d-flex flex-column align-items-center">
      <h1 className="lead display-6 mb-3">Numere generate:</h1>
      <div style={{ textAlign: "center" }}>
        <label htmlFor="select-direction" className="lead mb-2">
          Selecteaza directia:
        </label>
        <select
          value={sortDirection}
          onChange={changeSortDirectionHandler}
          id="select-direction"
          className="form-select"
        >
          <option value="0">Ascendent</option>
          <option value="1">Descendent</option>
        </select>
      </div>
      <div className="d-flex gap-2 my-4">
        {numbers.map((number, index) => (
          <div
            style={{ width: 50, height: 50, backgroundColor: "#bde0fe" }}
            className="d-flex justify-content-center  fs-3"
            key={index}
            draggable
            onDragStart={() => onDragStart(index)}
            onDragOver={() => onDragOver(index)}
          >
            {number}
          </div>
        ))}
      </div>
      <button onClick={generateNumbers} className="btn btn-primary mb-2">
        Genereaza alt sir de numere
      </button>
      <div className={`alert alert-${checkSort() ? "success" : "danger"}`}>
        {checkSort()
          ? "Numerele sunt aranjate corect"
          : "Numerele nu sunt aranjate corect"}
      </div>
    </div>
  )
}

export default SortingGenerator

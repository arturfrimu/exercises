import React from "react"

const Menu = ({ onChangeSortDirectionHandler, val }) => {
  return (
    <div className="d-flex flex-column align-items-center">
      <label htmlFor="select-direction" className="lead mb-2">
        Selecteaza directia:
      </label>
      <select
        className="form-select w-50"
        id="select-direction"
        value={val}
        onChange={onChangeSortDirectionHandler}
      >
        <option value="0">Exercitii matematice</option>
        <option value="1">Sortare</option>
        <option value="2">Figuri geometrice</option>
        <option value="3">Comparatii</option>
      </select>
    </div>
  )
}

export default Menu

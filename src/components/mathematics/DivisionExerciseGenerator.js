const min = 2
const max = 10

let randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
let randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

export class DivisionExerciseGenerator {

    divisionLevel1() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        return {
            exercise: `${(randomNumber1 * randomNumber2)} / ${randomNumber1} = X`,
            result: (randomNumber1 * randomNumber2) / randomNumber1
        }
    }

    divisionLevel2() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        return {
            exercise: `X = ${(randomNumber1 * randomNumber2)} / ${randomNumber2}`,
            result: (randomNumber1 * randomNumber2) / randomNumber2
        }
    }

    divisionLevel3() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1
            randomNumber1 = randomNumber2
            randomNumber2 = temp
        }

        return {
            exercise: `${randomNumber1} = X / ${randomNumber2}`,
            result: randomNumber1 * randomNumber2
        }
    }

    division(divisionLevel) {
        const divisionLevels = [
            this.divisionLevel1,
            this.divisionLevel2,
            this.divisionLevel3
        ]

        return divisionLevels[divisionLevel]()
    }
}
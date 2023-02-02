const min = 2
const max = 10

let randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
let randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

export class SubtractionExerciseGenerator {

    subtractionLevel1() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1
            randomNumber1 = randomNumber2
            randomNumber2 = temp
        }

        return {
            exercise: `${randomNumber1} - ${randomNumber2} = X`,
            result: randomNumber1 - randomNumber2
        }
    }

    subtractionLevel2() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1
            randomNumber1 = randomNumber2
            randomNumber2 = temp
        }

        return {
            exercise: `X = ${randomNumber1} - ${randomNumber2}`,
            result: randomNumber1 - randomNumber2
        }
    }

    subtractionLevel3() {
        randomNumber1 = Math.floor(Math.random() * (max - min + 1) + min)
        randomNumber2 = Math.floor(Math.random() * (max - min + 1) + min)

        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1
            randomNumber1 = randomNumber2
            randomNumber2 = temp
        }

        return {
            exercise: `${randomNumber1} - X = ${randomNumber2}`,
            result: randomNumber1 - randomNumber2
        }
    }

    subtraction(subtractionLevel) {
        const subtractionLevels = [
            this.subtractionLevel1,
            this.subtractionLevel2,
            this.subtractionLevel3
        ]

        return subtractionLevels[subtractionLevel]()
    }
}
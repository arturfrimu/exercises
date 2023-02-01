export class AdditionExerciseGenerator {

    additionLevel1() {
        const randomNumber1 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        const randomNumber2 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        return {
            exercise: `${randomNumber1} + ${randomNumber2} = X`,
            result: randomNumber1 + randomNumber2
        }
    }

    additionLevel2() {
        const randomNumber1 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        const randomNumber2 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        return {
            exercise: `X = ${randomNumber1} + ${randomNumber2}`,
            result: randomNumber1 + randomNumber2
        }
    }

    additionLevel3() {
        let randomNumber1 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        let randomNumber2 = Math.floor(Math.random() * (10 - 5 + 1) + 5);
        if (randomNumber1 - randomNumber2 < 0) {
            let temp = randomNumber1;
            randomNumber1 = randomNumber2;
            randomNumber2 = temp;
        }
        return {
            exercise: `${randomNumber1} = X + ${randomNumber2}`,
            result: randomNumber1 - randomNumber2
        }
    }


    addition(additionLevel) {
        const additionLevels = [
            this.additionLevel1,
            this.additionLevel2,
            this.additionLevel3
        ];

        return additionLevels[additionLevel]();
    }
}
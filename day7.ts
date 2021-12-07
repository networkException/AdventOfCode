import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const crabs = readFileSync('input/day7').toString().split(',').map(Number).sort((a, b) => a - b);

{
    const median = (values: Array<number>): number => {
        const half = Math.floor(values.length / 2);

        if (values.length % 2)
            return values[half];

        return (values[half - 1] + values[half]) / 2;
    };

    const desiredPosition = median(crabs);
    const fuel = crabs.map(crab => Math.abs(crab - desiredPosition)).reduce((a, b) => a + b, 0);

    logResult `day7/a: They must spend ${fuel} fuel to align to that position`;
}

{
    const triangularNumber = (value: number): number => {
        let sum = 0;

        for (let i = 0; i <= value; i++)
            sum += i;

        return sum;
    };

    const fuelList = new Array<number>();
    const max = (crabs.at(-1) ?? 0);

    for (let i = crabs[0]; i <= max; i++) {
        let fuel = 0;

        for (const crab of crabs)
            fuel += triangularNumber(Math.abs(crab - i));

        fuelList.push(fuel);
    }

    logResult `day7/b: They must spend ${fuelList.reduce((a, b) => a < b ? a : b)} fuel to align to that position`;
}

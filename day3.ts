import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const lines = readFileSync('input/day3').toString().split('\n');

{
    const gamma = lines[0].split('').map((_, character) => {
        const ones = lines.reduce((previous, line) => line[character] === '1' ? previous + 1 : previous, 0);
        const zeros = lines.length - ones;

        return ones > zeros ? '1' : '0';
    }).join('');

    const epsilon = gamma.split('').map(character => character === '1' ? '0' : '1').join('');

    const gammaDecimal = parseInt(gamma, 2);
    const epsilonDecimal = parseInt(epsilon, 2);

    logResult `day03/a: ${gammaDecimal * epsilonDecimal} is the power consumption of the submarine`;
}

{
    const getMatching = (values: Array<string>, character: number, type: 'oxygen' | 'co2'): Array<string> => values.filter(value => {
        const ones = values.reduce((previous, value) => value[character] === '1' ? previous + 1 : previous, 0);
        const zeros = values.length - ones;

        const bit = value[character];

        if (zeros === ones)
            return bit === (type === 'oxygen' ? '1' : '0');

        if (zeros > ones)
            return bit === (type === 'oxygen' ? '0' : '1');

        return bit === (type === 'oxygen' ? '1' : '0');
    });

    let oxygenValues = lines;
    let co2Values = lines;

    for (let character = 0; character < lines[0].length; character++) {
        if (oxygenValues.length !== 1)
            oxygenValues = getMatching(oxygenValues, character, 'oxygen');

        if (co2Values.length !== 1)
            co2Values = getMatching(co2Values, character, 'co2');
    }

    const oxygenDecimal = parseInt(oxygenValues[0], 2);
    const co2Decimal = parseInt(co2Values[0], 2);

    logResult `day03/b: ${oxygenDecimal * co2Decimal} is the life support rating of the submarine`;
}

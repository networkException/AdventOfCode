import * as files from 'fs';

const input: Array<number> = files.readFileSync('input/day09.txt').toString().split('\n').map(line => Number(line));
const preamble: number = 25;

const isValid = (value: number, previous: Array<number>): boolean => {
    for (const a of previous)
        for (const b of previous)
            if (a + b === value) return true;

    return false;
};

let found: number = 0;

input.forEach((value, index) => {
    if (index < preamble) return;

    if (!isValid(value, input.slice(index - preamble, index))) {
        console.log('Day 9A: First value that is not a sum of the previous 25 is ' + value);
        found = value;
        return;
    }
});

input.forEach((a, indexA) => {
    input.forEach((b, indexB) => {
        if (b < a) return;

        const range: Array<number> = input.slice(indexA, indexB);

        if (range.length < 2) return;

        if (range.reduce((a, b) => a + b) === found) {
            const largest: number = range.sort((a, b) => b - a)[0];
            const smallest: number = range.sort((a, b) => a - b)[0];

            console.log('Day 9B: The smallest and largest number in the range add up to ' + (largest + smallest));
        }
    });
});
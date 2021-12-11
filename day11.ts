import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const input = readFileSync('input/day11').toString().split('\n').map(line => line.split('').map(Number));

const get = (x: number, y: number): number | undefined => input[y]?.[x];
const set = (x: number, y: number, value: number): number => input[y]?.[x] === undefined ? value : (input[y][x] = value);
const increment = (x: number, y: number): number => set(x, y, get(x, y)! + 1);

const adjacent = (x: number, y: number): Array<{ x: number, y: number }> => [
    { x: x + 1, y },
    { x: x - 1, y },
    { x, y: y + 1 },
    { x, y: y - 1 },
    { x: x + 1, y: y + 1 },
    { x: x + 1, y: y - 1 },
    { x: x - 1, y: y + 1 },
    { x: x - 1, y: y - 1 }
].filter(({ x, y }) => get(x, y) !== undefined);

/*
const print = () => {
    for (let y = 0; y < input.length; y++) {
        for (let x = 0; x < input[y].length; x++) {
            const value = get(x, y) ?? -1;

            if (value === 0) {
                process.stdout.write(`\x1b[31m${value}\x1b[0m`);
            } else {
                process.stdout.write(value.toString());
            }
        }

        process.stdout.write('\n');
    }

    process.stdout.write('\n');
};
*/

const recurseFlashes = (x: number, y: number, flashed: Array<{ x: number, y: number }>): void => {
    if (flashed.some(entry => entry.x === x && entry.y === y))
        return;

    const current = get(x, y)! + 1;

    set(x, y, current);

    if (current > 9) {
        flashed.push({ x, y });

        for (const { x: a, y: b } of adjacent(x, y))
            recurseFlashes(a, b, flashed);
    }
};

{
    let flashes = 0;

    for (let i = 0; i < 100; i++) {
        for (let y = 0; y < input.length; y++)
            for (let x = 0; x < input[y].length; x++)
                increment(x, y);

        const flashed = new Array<{ x: number, y: number }>();

        for (let y = 0; y < input.length; y++)
            for (let x = 0; x < input[y].length; x++)
                if (get(x, y)! > 9)
                    recurseFlashes(x, y, flashed);

        for (const { x, y } of flashed)
            set(x, y, 0);

        flashes += flashed.length;
    }

    logResult `day11/a: There are ${flashes} total flashes after 100 steps`;
}

{
    let i = 0;

    // eslint-disable-next-line no-constant-condition
    while (true) {
        i++;

        for (let y = 0; y < input.length; y++)
            for (let x = 0; x < input[y].length; x++)
                increment(x, y);

        const flashed = new Array<{ x: number, y: number }>();

        for (let y = 0; y < input.length; y++)
            for (let x = 0; x < input[y].length; x++)
                if (get(x, y)! > 9)
                    recurseFlashes(x, y, flashed);

        if (flashed.length === input.length * input[0].length)
            break;

        for (const { x, y } of flashed)
            set(x, y, 0);
    }

    logResult `day11/b: ${i} is the first step during which all octopuses flash`;
}

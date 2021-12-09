import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const heightmap = readFileSync('input/day9').toString().split('\n').map(line => line.split('').map(Number));

const at = (x: number, y: number): number => heightmap[y]?.[x] ?? 10;
const above = (x: number, y: number): number => at(x, y - 1);
const below = (x: number, y: number): number => at(x, y + 1);
const left = (x: number, y: number): number => at(x - 1, y);
const right = (x: number, y: number): number => at(x + 1, y);

const isLowest = (x: number, y: number): boolean => {
    const current = at(x, y);
    const surrounding = [ above(x, y), below(x, y), left(x, y), right(x, y) ];

    return surrounding.map(entry => entry > current).every(entry => entry);
};

{
    let riskLevels = 0;

    for (let y = 0; y < heightmap.length; y++)
        for (let x = 0; x < heightmap[y].length; x++)
            if (isLowest(x, y))
                riskLevels += at(x, y) + 1;

    logResult `day9/a: The sum of the risk levels of all low points on the heightmap is ${riskLevels}`;
}

{
    const recurseIntoBasin = (x: number, y: number, basin: Array<{ x: number, y: number }>) => {
        if (basin.some(entry => entry.x === x && entry.y === y))
            return;

        basin.push({ x, y });

        if (above(x, y) < 9)
            recurseIntoBasin(x, y - 1, basin);
        if (below(x, y) < 9)
            recurseIntoBasin(x, y + 1, basin);
        if (left(x, y) < 9)
            recurseIntoBasin(x - 1, y, basin);
        if (right(x, y) < 9)
            recurseIntoBasin(x + 1, y, basin);
    };

    const basins = new Array<number>();

    for (let y = 0; y < heightmap.length; y++) {
        for (let x = 0; x < heightmap[y].length; x++) {
            if (isLowest(x, y)) {
                const basin = new Array<{ x: number, y: number }>();

                recurseIntoBasin(x, y, basin);

                basins.push(basin.length);
            }
        }
    }

    const result = basins.sort((a, b) => b - a).slice(0, 3).reduce((a, b) => a * b);

    logResult `day9/b: Multiplying together the sizes of the three largest basins results in ${result}`;
}

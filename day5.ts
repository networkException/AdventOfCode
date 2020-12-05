import * as files from 'fs';

const input: string = files.readFileSync('input/day5.txt').toString();

interface BoardingPass {
    row: number;
    column: number;
    id: number;
}

interface Range {
    min: number;
    max: number;
}

const inRange = (range: Range, upper: boolean): Range => {
    const median: number = (range.min + range.max) / 2;

    if (upper) return { min: Math.round(median), max: range.max };

    return { min: range.min, max: Math.floor(median) };
};

const boardingPasses: Array<BoardingPass> = input.split('\n').map(pass => {
    let row: number;
    let column: number;

    let lastRowRange: Range = { min: 0, max: 127 };
    for (let i = 0; i < 7; i++) lastRowRange = inRange(lastRowRange, pass.charAt(i) === 'B');
    row = lastRowRange.max;

    let lastColumnRange: Range = { min: 0, max: 7 };
    for (let i = 7; i < pass.length; i++) lastColumnRange = inRange(lastColumnRange, pass.charAt(i) === 'R');
    column = lastColumnRange.max;

    return { row, column, id: row * 8 + column };
});

const boardingPassIds: Array<number> = boardingPasses.map(pass => pass.id);

console.log('Day 5A: The highest boarding pass id is ' + boardingPassIds.sort((a, b) => b - a)[0]);
console.log('Day 5B: Your seat\'s id is ' + [...Array(128 * 8).keys()]
    .filter(id => !boardingPassIds.includes(id))
    .filter(id => boardingPassIds.includes(id + 1))
    .filter(id => boardingPassIds.includes(id - 1)));
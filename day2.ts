import * as files from 'fs';

const input: Array<string> = files.readFileSync('input/day2.txt').toString().split('\n');
const pattern: RegExp = /^([0-9]+)-([0-9]+) (.): (.+)/;

let validA: number = 0;
let validB: number = 0;

for (const line of input) {
    const match = line.match(pattern);

    const min: number = Number(match[1]);
    const max: number = Number(match[2]);
    const char: string = match[3];
    const password: string = match[4];

    let count: number = 0;

    for (let i = 0; i < password.length; i++)
        if (password.charAt(i) === char) count++;

    if (count >= min && count <= max) validA++;
    if (password.charAt(min - 1) !== password.charAt(max - 1) && (password.charAt(min - 1) === char || password.charAt(max - 1) === char))
        validB++;
}

console.log('Day 2A: There are ' + validA + ' valid passwords');
console.log('Day 2B: There are ' + validB + ' valid passwords');
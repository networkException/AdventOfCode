import * as files from 'fs';

const input: string = files.readFileSync('input/day1.txt').toString();

(() => {
    for (const line of input.split('\n')) {
        for (const line2 of input.split('\n')) {
            if (Number(line) + Number(line2) === 2020) {
                console.log('Day 1A: The output is ' + Number(line) * Number(line2));
                return;
            }
        }
    }
})();

(() => {
    for (const line of input.split('\n')) {
        for (const line2 of input.split('\n')) {
            for (const line3 of input.split('\n')) {
                if (Number(line) + Number(line2) + Number(line3) === 2020) {
                    console.log('Day 1B: The output is ' + Number(line) * Number(line2) * Number(line3));
                    return;
                }
            }
        }
    }
})();
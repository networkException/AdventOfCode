import * as files from 'fs';

const input: Array<number> = files.readFileSync('input/day1.txt').toString().split('\n').map(Number);

(() => {
    for (const line of input) {
        for (const line2 of input) {
            if (line + line2 === 2020) {
                console.log('Day 1A: The output is ' + line * line2);
                return;
            }
        }
    }
})();

(() => {
    for (const line of input) {
        for (const line2 of input) {
            for (const line3 of input) {
                if (line + line2 + line3 === 2020) {
                    console.log('Day 1B: The output is ' + (line * line2 * line3));
                    return;
                }
            }
        }
    }
})();
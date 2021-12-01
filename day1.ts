import { readFileSync } from 'fs';

const lines = readFileSync('input/day1').toString().split('\n');

{
    let previous: number = NaN;
    let increased = 0;

    for (const line of lines) {
        const measurement = Number(line);

        if (!isNaN(previous) && measurement > previous) increased++;

        previous = measurement;
    }

    console.log(`day1/a: There are ${increased} measurements that are larger than the previous measurement.`);
}

{
    let previous: number = NaN;
    let increased = 0;

    for (let i = 0; i < lines.length - 2; i++) {
        const measurement = Number(lines[i]) + Number(lines[i + 1]) + Number(lines[i + 2]);

        if (!isNaN(previous) && measurement > previous) increased++;

        previous = measurement;
    }

    console.log(`day1/b: There are ${increased} sums that are larger than the previous sum.`);
}

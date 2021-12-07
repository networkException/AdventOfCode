import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const lanternfish = readFileSync('input/day6').toString().split(',').map(Number);

{
    const days = 80;

    let digits = new BigUint64Array(9);

    for (let i = 0; i <= 8; i++)
        digits[i] = BigInt(lanternfish.filter(value => value === i).length);

    for (let day = 0; day < days; day++) {
        const newDigits = new BigUint64Array(9);

        for (let i = 8; i > 0; i--)
            newDigits[i - 1] = digits[i];

        newDigits[6] = (newDigits[6] ?? 0n) + (digits[0] ?? 0n);
        newDigits[8] = (newDigits[8] ?? 0n) + (digits[0] ?? 0n);

        digits = newDigits;
    }

    const count = digits.reduce((a, b) => a + b, 0n);

    logResult `day6/a: There would be ${count} lanternfish after 80 days`;
}

{
    const days = 256;

    let digits = new BigUint64Array(9);

    for (let i = 0; i <= 8; i++)
        digits[i] = BigInt(lanternfish.filter(value => value === i).length);

    for (let day = 0; day < days; day++) {
        const newDigits = new BigUint64Array(9);

        for (let i = 8; i > 0; i--)
            newDigits[i - 1] = digits[i];

        newDigits[6] = (newDigits[6] ?? 0n) + (digits[0] ?? 0n);
        newDigits[8] = (newDigits[8] ?? 0n) + (digits[0] ?? 0n);

        digits = newDigits;
    }

    const count = digits.reduce((a, b) => a + b, 0n);

    logResult `day6/b: There would be ${count} lanternfish after 256 days`;
}

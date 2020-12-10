import * as files from 'fs';

const input: Array<number> = files.readFileSync('input/day10.txt').toString().split('\n').map(Number).sort((a, b) => a - b);

interface Adapter {
    leap: number;
    joltage: number;
}

const joltages: Map<number, number> = new Map<number, number>();
const chain: Array<Adapter> = new Array<Adapter>();
let highest: number;

const longestChain = (joltage: number): void => {
    highest = joltage;

    for (let i = 1; i < 4; i++) {
        if (input.includes(joltage + i)) {
            if (!joltages.has(i)) joltages.set(i, 0);

            joltages.set(i, joltages.get(i) + 1);

            chain.push({
                leap: i,
                joltage
            });

            longestChain(joltage + i);
            return;
        }
    }
};

longestChain(0);

joltages.set(3, joltages.get(3) + 1);
highest += 3;
chain.push({
    leap: 3,
    joltage: highest - 3
});

chain.push({
    leap: 3,
    joltage: highest
});

console.log('Day 10A: The value is ' + joltages.get(1) * joltages.get(3));

let count: number = 1;

const print = (chain: Array<Adapter>, index: number, key: string) => {
    for (let i = 0; i < chain.length; i++) {
        if (i === index) {
            process.stdout.write('(' + chain[i][key] + ')');
        } else {
            process.stdout.write(' ' + chain[i][key] + ' ');
        }
    }

    process.stdout.write('\n');
};

const leapPermutations = (leaps: number): number => {
    switch (leaps) {
        case 1: return 1;
        case 2: return 2; /* 1 1; 2 */
        case 3: return 4; /* 1 1 1; 1 2; 2 1; 3 */
        case 4: return 7; /* 1 1 1 1; 2 1 1; 1 2 1; 1 1 2; 3 1; 1 3; 2 2 */
    }
};

for (let i = 0; i < chain.length; i++) {
    print(chain, i, 'joltage');

    let leaps: number = 0;

    while (chain[i + leaps] !== undefined && chain[i + leaps].leap === 1) {
        leaps++;
    }

    if (leaps > 1) {
        console.log(leaps);
        count *= leapPermutations(leaps);
        i += leaps;
    }
}

console.log('Day 10A: There are ' + count + ' total ways the adapters could be chained');
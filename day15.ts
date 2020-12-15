import * as files from 'fs';

const input: Array<number> = files.readFileSync('input/day15.txt').toString().split(',').map(Number);

interface Turn {
    previous: number;
    before: number;
}

(async () => {
    const spoken: Set<number> = new Set<number>(input);
    const turns: Map<number, Turn> = new Map<number, Turn>(input.map((value, index) => [value, {
        previous: index + 1,
        before: index + 1
    }]));

    let counter: number = input.length;
    let lastSpoken: number = input[input.length - 1];

    while (counter < 2020) {
        counter++;

        if (spoken.has(lastSpoken)) {
            const turn: Turn = turns.get(lastSpoken);
            const turnDifference: number = turn === undefined ? 0 : (turn.previous - turn.before);

            turns.set(turnDifference, {
                previous: counter,
                before: turn === undefined ? 0 : turns.has(turnDifference) ? turns.get(turnDifference).previous : 0
            });

            lastSpoken = turnDifference;
        } else {
            spoken.add(lastSpoken);
            turns.set(0, {
                previous: counter,
                before: turns.has(0) ? turns.get(0).previous : 0
            });

            lastSpoken = 0;
        }
    }

    console.log('Day 15A: The number at turn 2020 is ' + lastSpoken);
})();

(async () => {
    const spoken: Set<number> = new Set<number>(input);
    const turns: Map<number, Turn> = new Map<number, Turn>(input.map((value, index) => [value, {
        previous: index + 1,
        before: index + 1
    }]));

    let counter: number = input.length;
    let lastSpoken: number = input[input.length - 1];

    while (counter < 30000000) {
        counter++;

        if (spoken.has(lastSpoken)) {
            const turn: Turn = turns.get(lastSpoken);
            const turnDifference: number = turn === undefined ? 0 : (turn.previous - turn.before);

            turns.set(turnDifference, {
                previous: counter,
                before: turn === undefined ? 0 : turns.has(turnDifference) ? turns.get(turnDifference).previous : 0
            });

            lastSpoken = turnDifference;
        } else {
            spoken.add(lastSpoken);
            turns.set(0, {
                previous: counter,
                before: turns.has(0) ? turns.get(0).previous : 0
            });

            lastSpoken = 0;
        }
    }

    console.log('Day 15B: The number at turn 30000000 is ' + lastSpoken);
})();

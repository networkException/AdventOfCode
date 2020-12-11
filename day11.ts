import * as files from 'fs';

type Type = 'floor' | 'empty' | 'occupied';
type Line = Array<Type>;
type Field = Array<Line>;

const input: Field = files.readFileSync('input/day11.txt').toString().split('\n').map(line => {
    const positions: Line = new Array<Type>();

    for (const char of line) positions.push(char === '.' ? 'floor' : char === 'L' ? 'empty' : 'occupied');

    return positions;
});

const get = (input: Field, x: number, y: number): Type => input[y][x];
const set = (input: Field, x: number, y: number, type: Type): Type => input[y][x] = type;
const has = (input: Field, x: number, y: number): boolean => input[y] !== undefined && input[y][x] !== undefined;
const count = (input: Field, type: Type): number => {
    let count: number = 0;

    for (const line of input) for (const position of line) if (position === type) count++;

    return count;
};

const equals = (a: Field, b: Field): boolean => {
    for (let y = 0; y < input.length; y++) {
        for (let x = 0; x < input[y].length; x++) {
            if (get(a, x, y) !== get(b, x, y)) return false;
        }
    }

    return true;
};

const copy = (input: Field): Field => {
    const next: Field = new Array<Array<Type>>();

    input.forEach(line => {
        const nextLine: Line = new Array<Type>();
        line.forEach(type => nextLine.push(type));
        next.push(nextLine);
    });

    return next;
};

const adjacent = (input: Field, x: number, y: number): Array<Type> => {
    const around: Array<Type> = new Array<Type>();

    if (has(input, x, y + 1)) around.push(get(input, x, y + 1)); // top
    if (has(input, x, y - 1)) around.push(get(input, x, y - 1)); // bottom
    if (has(input, x - 1, y)) around.push(get(input, x - 1, y)); // left
    if (has(input, x + 1, y)) around.push(get(input, x + 1, y)); // right
    if (has(input, x - 1, y + 1)) around.push(get(input, x - 1, y + 1)); // top left
    if (has(input, x + 1, y + 1)) around.push(get(input, x + 1, y + 1)); // top right
    if (has(input, x - 1, y - 1)) around.push(get(input, x - 1, y - 1)); // bottom left
    if (has(input, x + 1, y - 1)) around.push(get(input, x + 1, y - 1)); // bottom right

    return around;
};

const visible = (input: Field, x: number, y: number): Array<Type> => {
    const inSight: Array<Type> = new Array<Type>();

    for (let up = y - 1; up >= 0; up--) {
        const type: Type = get(input, x, up);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }
    }

    for (let down = y + 1; down < input.length; down++) {
        const type: Type = get(input, x, down);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }
    }

    for (let left = x - 1; left >= 0; left--) {
        const type: Type = get(input, left, y);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }
    }

    for (let right = x + 1; right < input[y].length; right++) {
        const type: Type = get(input, right, y);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }
    }

    let upLeft: number = 1;
    while (true) {
        if (!has(input, x - upLeft, y - upLeft)) break;

        const type: Type = get(input, x - upLeft, y - upLeft);

        if (type !== 'floor') {
            inSight.push(type);
            break;
        }

        upLeft++;
    }

    let upRight: number = 1;
    while (true) {
        if (!has(input, x + upRight, y - upRight)) break;

        const type: Type = get(input, x + upRight, y - upRight);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }

        upRight++;
    }

    let downLeft: number = 1;
    while (true) {
        if (!has(input, x - downLeft, y + downLeft)) break;

        const type: Type = get(input, x - downLeft, y + downLeft);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }

        downLeft++;
    }

    let downRight: number = 1;
    while (true) {
        if (!has(input, x + downRight, y + downRight)) break;

        const type: Type = get(input, x + downRight, y + downRight);
        if (type !== 'floor') {
            inSight.push(type);
            break;
        }

        downRight++;
    }


    return inSight;
};

const print = (input: Field, markX: number, markY: number): void => {
    for (let y = 0; y < input.length; y++) {
        for (let x = 0; x < input[y].length; x++) {
            const type: Type = get(input, x, y);

            if (markX === x && markY === y) {
                process.stdout.write(type === 'empty' ? '[L]' : type === 'floor' ? '[.]' : '[#]');
            } else {
                process.stdout.write(type === 'empty' ? ' L ' : type === 'floor' ? ' . ' : ' # ');
            }
        }

        process.stdout.write('\n');
    }
};

const applyRulesA = (input: Field): Field => {
    const next: Field = copy(input);

    for (let y = 0; y < input.length; y++) {
        for (let x = 0; x < input[y].length; x++) {
            const type: Type = get(input, x, y);
            const around = adjacent(input, x, y);

            if (type === 'empty' && around.filter(type => type === 'occupied').length === 0) {
                set(next, x, y, 'occupied');
            }

            if (type === 'occupied' && around.filter(type => type === 'occupied').length >= 4) {
                set(next, x, y, 'empty');
            }
        }
    }

    return next;
};

const applyRulesB = (input: Field): Field => {
    const next: Field = copy(input);

    for (let y = 0; y < input.length; y++) {
        for (let x = 0; x < input[y].length; x++) {
            const type: Type = get(input, x, y);
            const inSight = visible(input, x, y);

            if (type === 'empty' && inSight.filter(type => type === 'occupied').length === 0) {
                set(next, x, y, 'occupied');
            }

            if (type === 'occupied' && inSight.filter(type => type === 'occupied').length >= 5) {
                set(next, x, y, 'empty');
            }
        }
    }

    return next;
};

(() => {
    let last: Field = copy(input);

    while (true) {
        // print(last, 0, 0);
        // console.log();

        const next: Field = applyRulesA(last);

        if (equals(last, next)) break;

        last = next;
    }

    console.log('Day 11A: ' + count(last, 'occupied') + ' seats ended up occupied');
})();


(() => {
    let last: Field = copy(input);

    while (true) {
        // print(last, 0, 0);
        // console.log();

        const next: Field = applyRulesB(last);

        if (equals(last, next)) break;

        last = next;
    }

    console.log('Day 11B: ' + count(last, 'occupied') + ' seats ended up occupied');
})();

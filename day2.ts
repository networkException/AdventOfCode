import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const instructions = readFileSync('input/day2').toString().split('\n');

{
    const position = {
        horizontal: 0,
        depth: 0
    };

    for (const instruction of instructions) {
        const action = instruction.split(' ')[0];
        const value = Number(instruction.split(' ')[1]);

        switch (action) {
            case 'forward': position.horizontal += value; break;
            case 'down': position.depth += value; break;
            case 'up': position.depth -= value; break;
        }
    }

    logResult `day2/a: You get ${position.horizontal * position.depth} if you multiply your final horizontal position by your final depth`;
}

{
    const position = {
        aim: 0,
        horizontal: 0,
        depth: 0
    };

    for (const instruction of instructions) {
        const action = instruction.split(' ')[0];
        const value = Number(instruction.split(' ')[1]);

        switch (action) {
            case 'forward': position.horizontal += value; position.depth += position.aim * value; break;
            case 'down': position.aim += value; break;
            case 'up': position.aim -= value; break;
        }
    }

    logResult `day2/b: You get ${position.horizontal * position.depth} if you multiply your final horizontal position by your final depth`;
}

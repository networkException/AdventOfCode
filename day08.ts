import * as files from 'fs';

const input: string = files.readFileSync('input/day08.txt').toString();

interface Instruction {
    operation: string;
    argument: number;
}

interface Result {
    looped: boolean;
    accumulator: number;
}

type Operation = (argument: number) => void;

const readkey = async () => {
    process.stdin.setRawMode(true);

    return new Promise<void>(resolve => process.stdin.once('data', data => {
        const byteArray: Array<number> = [...data];
        if (byteArray.length > 0 && byteArray[0] === 3) process.exit(0);

        process.stdin.setRawMode(false);
        resolve();
    }));
};

const interpret = (instructions: Array<Instruction>): Result => {
    let accumulator: number = 0;
    let pointer: number = 0;
    const ran: Set<number> = new Set<number>();

    const operations: Map<string, Operation> = new Map<string, Operation>();
    operations.set('acc', arugment => {
        accumulator += arugment;
        pointer++;
    });

    operations.set('jmp', arugment => pointer += arugment);
    operations.set('nop', () => pointer++);

    while (pointer < instructions.length) {
        if (ran.has(pointer)) return {
            looped: true,
            accumulator
        };

        ran.add(pointer);

        const instuction: Instruction = instructions[pointer];

        // console.log(pointer.toString(10).padEnd(2) + '| ' + instuction.operation + ' ' + instuction.argument.toString(10).padEnd(3) + '| ' + accumulator);
        // await readkey();

        operations.get(instuction.operation)(instuction.argument);
    }

    return {
        looped: false,
        accumulator
    };
};

const instructions: Array<Instruction> = input.split('\n').map(instruction => {
    const split: Array<string> = instruction.split(' ');

    return {
        operation: split[0],
        argument: Number(split[1])
    };
});

console.log('Day 8A: Before the instructions loop again the accumulator is ' + interpret(instructions).accumulator);

instructions
    .map((instruction, index) => { return { index, instruction }; })
    .filter(entry => entry.instruction.operation !== 'acc')
    .forEach(entry => {
        const permutation: Array<Instruction> = new Array<Instruction>(...instructions);
        const instruction: Instruction = {
            operation: entry.instruction.operation === 'jmp' ? 'nop' : 'jmp',
            argument: entry.instruction.argument
        };

        permutation[entry.index] = instruction;

        const result: Result = interpret(permutation);

        if (!result.looped) {
            console.log('Day 8B: After changing \'' + entry.instruction.operation + ' ' + entry.instruction.argument + '\' at ' + entry.index + ' to \'' + instruction.operation + '\' the accumulator is ' + result.accumulator);
        }
    });
import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const lines = readFileSync('input/day10').toString().split('\n');

const enum ChunkType {
    Round = ')',
    Square = ']',
    Curly = '}',
    Angle = '>'
}

const nonCorruptedLines = new Array<string>();

{
    let illegal = 0;

    for (const line of lines) {
        const chunkStack = new Array<ChunkType>();

        const isCorrupted = (() => {
            for (const character of line) {
                switch (character) {
                    case '(': chunkStack.push(ChunkType.Round); break;
                    case '[': chunkStack.push(ChunkType.Square); break;
                    case '{': chunkStack.push(ChunkType.Curly); break;
                    case '<': chunkStack.push(ChunkType.Angle); break;

                    case ')':
                    case ']':
                    case '}':
                    case '>': {
                        const popped = chunkStack.pop();

                        if (popped === undefined)
                            continue;

                        if (popped !== character) {
                            switch (character) {
                                case ChunkType.Round: illegal += 3; break;
                                case ChunkType.Square: illegal += 57; break;
                                case ChunkType.Curly: illegal += 1197; break;
                                case ChunkType.Angle: illegal += 25137; break;
                            }

                            return true;
                        }

                        break;
                    }
                    default: process.exit(1);
                }
            }

            return false;
        })();

        if (!isCorrupted)
            nonCorruptedLines.push(line);
    }

    logResult `day10/a: The total syntax error score for those errors is ${illegal}`;
}

{
    const scores = new Array<number>();

    for (const line of nonCorruptedLines) {
        const chunkStack = new Array<ChunkType>();

        for (const character of line) {
            switch (character) {
                case '(': chunkStack.push(ChunkType.Round); break;
                case '[': chunkStack.push(ChunkType.Square); break;
                case '{': chunkStack.push(ChunkType.Curly); break;
                case '<': chunkStack.push(ChunkType.Angle); break;

                case ')':
                case ']':
                case '}':
                case '>': {
                    const popped = chunkStack.pop();

                    if (popped === undefined)
                        continue;

                    break;
                }
                default: process.exit(1);
            }
        }

        chunkStack.reverse();

        let score = 0;

        for (const completion of chunkStack) {
            score *= 5;

            switch (completion) {
                case ChunkType.Round: score += 1; break;
                case ChunkType.Square: score += 2; break;
                case ChunkType.Curly: score += 3; break;
                case ChunkType.Angle: score += 4; break;
            }
        }

        scores.push(score);
    }

    scores.sort((a, b) => a - b);

    const middle = scores[Math.floor(scores.length / 2)];

    logResult `day10/b: The middle score is ${middle}`;
}

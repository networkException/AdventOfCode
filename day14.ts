import * as files from 'fs';
import { Parser } from './share/parser';
import { replaceAt } from './share/string';
import { newLine, Token, Tokenizer, whitespace } from './share/tokenizer';

const input: string = files.readFileSync('input/day14.txt').toString();

const tokenizer: Tokenizer = new Tokenizer([
    { name: 'mask', pattern: /^[X01]{36}/ },
    { name: 'instruction', pattern: /^(mask|mem)/ },
    { name: 'bracketOpen', pattern: /^\[/ },
    { name: 'bracketClose', pattern: /^\]/ },
    { name: 'equals', pattern: /^=/ },
    { name: 'decimal', pattern: /^[0-9]+/ }
]);

const tokens: Array<Token> = tokenizer.tokenize(input, whitespace, newLine);

(() => {
    const parser: Parser = new Parser(new Array<Token>(...tokens));

    let mask: string = '';
    const memory: Array<number> = new Array<number>();

    while (tokens.length > 0) {
        if (parser.matchAndTake('mask')) {
            parser.take(1); // =
            mask = parser.take(1);
        } else if (parser.matchAndTake('mem')) {
            parser.take(1); // [
            const address: number = Number(parser.take(1));
            parser.take(2); // ] =
            let value: string = Number(parser.take(1)).toString(2).padStart(36, '0');

            // console.log(value, parseInt(value, 2));
            // console.log(mask);

            for (let i = 0; i < 36; i++) if (mask[i] !== 'X') value = replaceAt(value, i, mask[i]);

            // console.log(value, parseInt(value, 2));
            // console.log('-------------------');
            memory[address] = parseInt(value, 2);
        }
    }

    console.log('Day 14A: The sum of all values in memory is ' + memory.reduce((a, b) => a + b));
})();

(async () => {
    const parser: Parser = new Parser(new Array<Token>(...tokens));

    let mask: string = '';
    const memory: Map<string, number> = new Map<string, number>(); // choose your data structures wisely when working with 36 bit number keys

    while (tokens.length > 0) {
        if (parser.matchAndTake('mask')) {
            parser.take(1); // =
            mask = parser.take(1);
        } else if (parser.matchAndTake('mem')) {
            parser.take(1); // [
            let address: string = Number(parser.take(1)).toString(2).padStart(36, '0');
            parser.take(2); // ] =
            const value: number = Number(parser.take(1));

            for (let i = 0; i < 36; i++) {
                if (mask[i] === '1') {
                    address = replaceAt(address, i, '1');
                } else if (mask[i] === 'X') {
                    address = replaceAt(address, i, 'X');
                }
            }

            const floatingBitCount: number = address.split('').filter(char => char === 'X').length;
            const floatingBitMax: number = Math.pow(2, floatingBitCount);

            for (let i = 0; i < floatingBitMax; i++) {
                const floating: string = i.toString(2).padStart(floatingBitCount, '0');
                let maskedAddress: string = address;

                for (let j = 0; j < floatingBitCount; j++) {
                    maskedAddress = maskedAddress.replace('X', floating[j]);
                }

                memory.set(maskedAddress, value);
            }
        }
    }

    console.log('Day 14B: The sum of all values in memory is ' + Array.from(memory.values()).reduce((a, b) => a + b));
})();
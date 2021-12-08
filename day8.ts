import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const entries = readFileSync('input/day8').toString().split('\n').map(entry => entry.split('|').map(part => part.trim().split(' ')));

{
    let count = 0;

    for (const entry of entries) {
        const [, ouputPatterns ] = entry;

        for (const pattern of ouputPatterns) {
            switch (pattern.length) {
                case 2: // 1
                case 4: // 4
                case 3: // 7
                case 7: // 8
                    count++;
                    break;
                default: continue;
            }
        }
    }

    logResult `day8/a: In the output numbers 1, 4, 7 and 8 occur ${count} times`;
}

{
    const digits = [
        [ 'a', 'b', 'c',      'e', 'f', 'g' ],
        [           'c',           'f'      ], // 1
        [ 'a',      'c', 'd', 'e',      'g' ],
        [ 'a',      'c', 'd',      'f', 'g' ],
        [      'b', 'c', 'd',      'f'      ], // 4
        [ 'a', 'b',      'd',      'f', 'g' ],
        [ 'a', 'b',      'd', 'e', 'f', 'g' ],
        [ 'a',      'c',           'f'      ], // 7
        [ 'a', 'b', 'c', 'd', 'e', 'f', 'g' ], // 8
        [ 'a', 'b', 'c', 'd',      'f', 'g' ]
    ];

    const findWithPatternLength = (patterns: Array<string>, length: number): string | undefined => patterns.find(pattern => pattern.length === length);
    const numberOfOtherPatternsWith = (patterns: Array<string>, character: string): number => patterns.filter(pattern => pattern.includes(character)).length;
    const unknownCharactersInPattern = (known: Record<string, string>, pattern: string): Array<string> => pattern.split('').filter(character => known[character] === undefined);

    let result = 0;

    for (const entry of entries) {
        const [ patterns, outputPatterns ] = entry;

        const known: Record<string, string> = {};

        const one = findWithPatternLength(patterns, 2) as string; // A one should exactly exist once in the input pattern list, ignoring undefined
        if (numberOfOtherPatternsWith(patterns, one[0]) === 9) { // All digits except "2" have character f, which is either the first or the second character in "one"
            known[one[0]] = 'f';
            known[one[1]] = 'c';
        } else {
            known[one[1]] = 'f';
            known[one[0]] = 'c';
        }

        const seven = findWithPatternLength(patterns, 3) as string;
        known[unknownCharactersInPattern(known, seven)[0]] = 'a'; // Seven consists of all characters found in "one" and an additional one

        const four = findWithPatternLength(patterns, 4) as string;
        const unknownFourCharacters = unknownCharactersInPattern(known, four); // Four consists of all characters found in one as well as b and d, of which b only occurs 6 times in all digits
        if (numberOfOtherPatternsWith(patterns, unknownFourCharacters[0]) === 6) {
            known[unknownFourCharacters[0]] = 'b';
            known[unknownFourCharacters[1]] = 'd';
        } else {
            known[unknownFourCharacters[1]] = 'b';
            known[unknownFourCharacters[0]] = 'd';
        }

        const eight = findWithPatternLength(patterns, 7) as string;
        const unknownEightCharacters = unknownCharactersInPattern(known, eight); // Eight consists of all characters, only e and g havent been found yet and e only occurs 4 times in all digits
        if (numberOfOtherPatternsWith(patterns, unknownEightCharacters[0]) === 4) {
            known[unknownEightCharacters[0]] = 'e';
            known[unknownEightCharacters[1]] = 'g';
        } else {
            known[unknownEightCharacters[1]] = 'e';
            known[unknownEightCharacters[0]] = 'g';
        }

        let output = '';

        for (const outputPattern of outputPatterns) {
            const decoded = outputPattern.split('').map(character => known[character]).sort();

            for (let i = 0; i < digits.length; i++) {
                if (decoded.join('') === digits[i].join('')) {
                    output += i;

                    break;
                }
            }
        }

        result += parseInt(output);
    }

    logResult `day8/b: Adding up all the output values results in ${result}`;
}

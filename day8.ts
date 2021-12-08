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
    const unknownCharactersInPattern = (known: Record<string, string>, pattern: string): Array<string> => {
        const knownValues = Object.values(known);

        return pattern.split('').filter(character => !knownValues.includes(character));
    };

    let result = 0;

    for (const entry of entries) {
        const [ patterns, outputPatterns ] = entry;

        const known: Record<string, string> = {};

        const one = findWithPatternLength(patterns, 2) as string; // A one should exactly exist once in the input pattern list, ignoring undefined
        if (numberOfOtherPatternsWith(patterns, one[0]) === 9) { // All digits except "2" have character f, which is either the first or the second character in "one"
            known['f'] = one[0];
            known['c'] = one[1];
        } else {
            known['f'] = one[1];
            known['c'] = one[0];
        }

        const seven = findWithPatternLength(patterns, 3) as string;
        known['a'] = unknownCharactersInPattern(known, seven)[0]; // Seven consists of all characters found in "one" and an additional one

        const four = findWithPatternLength(patterns, 4) as string;
        const unknownFourCharacters = unknownCharactersInPattern(known, four); // Four consists of all characters found in one as well as b and d, of which b only occurs 6 times in all digits
        if (numberOfOtherPatternsWith(patterns, unknownFourCharacters[0]) === 6) {
            known['b'] = unknownFourCharacters[0];
            known['d'] = unknownFourCharacters[1];
        } else {
            known['b'] = unknownFourCharacters[1];
            known['d'] = unknownFourCharacters[0];
        }

        const eight = findWithPatternLength(patterns, 7) as string;
        const unknownEightCharacters = unknownCharactersInPattern(known, eight); // Eight consists of all characters, only e and g havent been found yet and e only occurs 4 times in all digits
        if (numberOfOtherPatternsWith(patterns, unknownEightCharacters[0]) === 4) {
            known['e'] = unknownEightCharacters[0];
            known['g'] = unknownEightCharacters[1];
        } else {
            known['e'] = unknownEightCharacters[1];
            known['g'] = unknownEightCharacters[0];
        }

        const lookupTable = Object.fromEntries(Object.entries(known).map(([ known, translated ]) => [ translated, known ]));

        let output = '';

        for (const outputPattern of outputPatterns) {
            const decoded = outputPattern.split('').map(character => lookupTable[character]).sort();

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

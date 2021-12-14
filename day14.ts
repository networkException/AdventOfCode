import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const input = readFileSync('input/day14').toString().replace(/\r/g, '').split('\n\n');

const template = input[0];
const rules = new Map<string, string>(input[1].split('\n').map(rule => rule.split(' -> ') as [ string, string ]));

/*
{
    let current = template;

    for (let i = 0; i < 10; i++) {
        let next = current;
        let inserted = 0;

        for (let character = 0; character < current.length; character++) {
            for (const [ match, insert ] of rules) {
                if (current.substring(character).startsWith(match)) {
                    const insertionIndex = character + 1 + inserted;

                    next = next.substring(0, insertionIndex) + insert + next.substring(insertionIndex);

                    inserted++;
                }
            }
        }

        current = next;
    }

    const charactersByOccurence = new Map<string, number>();

    for (const character of current) charactersByOccurence.set(character, (charactersByOccurence.get(character) ?? 0) + 1);

    const entries = Array.from(charactersByOccurence.values()).sort((a, b) => b - a);

    console.log(entries[0] - entries.slice(-1)[0]);
}
*/

const process = (iterations: number): number => {
    const characters = new Map<string, number>();
    let patterns = new Map<string, number>();

    const increment = (map: Map<string, number>, key: string, value: number = 1) => map.set(key, (map.get(key) ?? 0) + value);

    for (let character = 0; character < template.length; character++) {
        increment(characters, template[character]);

        for (const [ match ] of rules)
            if (template.substring(character).startsWith(match))
                increment(patterns, match);
    }

    for (let i = 0; i < iterations; i++) {
        const nextPatterns = new Map<string, number>();

        for (const [ match, count ] of patterns) {
            const insert = rules.get(match);
            // if (!insert) process.exit(1);

            increment(characters, insert!, count);

            const firstNewPattern = match[0] + insert;
            const secondNewPattern = insert + match[1];

            // if (!rules.has(firstNewPattern)) process.exit(1);
            // if (!rules.has(secondNewPattern)) process.exit(1);

            increment(nextPatterns, firstNewPattern, count);
            increment(nextPatterns, secondNewPattern, count);
        }

        patterns = nextPatterns;
    }

    const entries = Array.from(characters.values()).sort((a, b) => b - a);

    return entries[0] - entries.slice(-1)[0];
};

logResult `day14/a: You get ${process(10)} if you take the quantity of the most common element and subtract the quantity of the least common element`;
logResult `day14/b: You get ${process(40)} if you take the quantity of the most common element and subtract the quantity of the least common element`;

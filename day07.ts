import * as files from 'fs';
import { Parser } from './share/parser';
import { Tokenizer, whitespace } from './share/tokenizer';

const input: string = files.readFileSync('input/day07.txt').toString();

const tokenizer: Tokenizer = new Tokenizer([
    { name: 'string', pattern: /^[a-z]+/ },
    { name: 'number', pattern: /^[0-9]+/ },
    { name: 'comma', pattern: /^,/ },
    { name: 'dot', pattern: /^\./ }
]);

interface Bag {
    color: string;
    quantity: number;
}

const rules: Map<string, Array<Bag>> = new Map<string, Array<Bag>>(input.split('\n').map(rule => {
    const parser: Parser = new Parser(tokenizer.tokenize(input, whitespace));

    return [
        parser.take(2), // current bag color
        (() => {
            parser.take(2); // bags contain
            const contains: Array<Bag> = new Array<Bag>();

            if (parser.match('no')) return contains; // no other bags

            do {
                contains.push({
                    quantity: Number(parser.take(1)), // contained bag quanitity
                    color: parser.take(2) // contained bag color
                });
            } while ((parser.matchAndTake('bags') || parser.matchAndTake('bag')) && parser.matchAndTake(',')); // forward in list

            return contains;
        })()
    ];
}));

const found: Set<string> = new Set<string>();

const find = (color: string): void => rules.forEach((ruleContains, ruleColor) => ruleContains.forEach(bag => {
    if (bag.color === color) {
        found.add(ruleColor);
        find(ruleColor);
    }
}));

find('shiny gold');

console.log('Day 6A: ' + found.size + ' bags can contain the shiny gold one');


let counted: number = 0;

const count = (color: string, quantity: number): void => {
    rules.get(color).forEach(bag => {
        const current: number = quantity * bag.quantity;
        counted += current;
        count(bag.color, current);

        /*
            Dammed be the examples, the first one works as well if I would call "count(bag.color, bag.quantity)" instead of
            "count(bag.color, quantity * bag.quantity)". This cost me like 30 minutes of debugging
        */
    });
};

count('shiny gold', 1);

console.log('Day 6B: ' + counted + ' bags have to be contained in a shiny gold one');
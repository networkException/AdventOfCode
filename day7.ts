import * as files from 'fs';

const input: string = files.readFileSync('input/day7.txt').toString();

interface Bag {
    color: string;
    quantity: number;
}

interface TokenDefinition {
    name: string;
    pattern: RegExp;
}

interface Token {
    token: string;
    value: string;
}

const definitions: Array<TokenDefinition> = [
    { name: 'string', pattern: /^[a-z]+/ },
    { name: 'number', pattern: /^[0-9]+/ },
    { name: 'comma', pattern: /^,/ },
    { name: 'dot', pattern: /^\./ },
];

const rules: Map<string, Array<Bag>> = new Map<string, Array<Bag>>(input.split('\n').map(rule => {
    let tokens: Array<Token> = new Array<Token>();

    while (rule.length > 0) {
        if (rule.startsWith(' ')) {
            rule = rule.substring(1);
            continue;
        }

        definitions.forEach(definition => {
            const matches: RegExpMatchArray = rule.match(definition.pattern);

            if (matches !== null) {
                const value: string = matches[0];

                tokens.push({ token: definition.name, value });
                rule = rule.substring(value.length);
                return;
            }
        });
    }

    const take = (length: number): string => {
        const result: string = tokens.slice(0, length).map(token => token.value).join(' ');

        tokens = tokens.splice(length);

        return result;
    };

    const match = (value: string): boolean => tokens.slice(0, 1)[0].value === value;

    const matchAndTake = (value: string): boolean => {
        if (match(value)) {
            take(1);

            return true;
        }

        return false;
    };

    return [
        take(2), // current bag color
        (() => {
            take(2); // bags contain
            const contains: Array<Bag> = new Array<Bag>();

            if (match('no')) return contains; // no other bags

            do {
                contains.push({
                    quantity: Number(take(1)), // contained bag quanitity
                    color: take(2) // contained bag color
                });
            } while ((matchAndTake('bags') || matchAndTake('bag')) && matchAndTake(',')); // forward in list

            return contains;
        })()
    ];
}));

(() => {
    const found: Set<string> = new Set<string>();

    const find = (color: string): void => rules.forEach((ruleContains, ruleColor) => ruleContains.forEach(bag => {
        if (bag.color === color) {
            found.add(ruleColor);
            find(ruleColor);
        }
    }));

    find('shiny gold');

    console.log('Day 6A: ' + found.size + ' bags can contain the shiny gold one');
})();

(() => {
    let result: number = 0;

    const count = (color: string, quantity: number): void => {
        rules.get(color).forEach(bag => {
            const current: number = quantity * bag.quantity;
            result += current;
            count(bag.color, current);

            /*
                Dammed be the examples, the first one works as well if I would call "count(bag.color, bag.quantity)" instead of
                "count(bag.color, quantity * bag.quantity)". This cost me like 30 minutes of debugging
            */
        });
    };

    count('shiny gold', 1);

    console.log('Day 6B: ' + result + ' bags have to be contained in a shiny gold one');
})();
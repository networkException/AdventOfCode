import * as files from 'fs';

class Range {

    public constructor(private readonly min: number, private readonly max: number) {
    }

    public matches(input: number): boolean {
        return input >= this.min && input <= this.max;
    }
}

class Rule {

    public readonly name: string;
    private readonly ranges: Array<Range>;

    public constructor(input: string) {
        const pattern: RegExp = /(.*): (\d*)-(\d*) or (\d*)-(\d*)/;
        const match: RegExpExecArray | null = pattern.exec(input);

        if (!match) {
            console.error('Rule: Input does not match pattern');
        } else {
            this.name = match[1];

            this.ranges = new Array<Range>();
            this.ranges.push(new Range(Number(match[2]), Number(match[3])));
            this.ranges.push(new Range(Number(match[4]), Number(match[5])));
        }
    }

    public matches(input: number): boolean {
        return this.ranges.map(range => range.matches(input)).reduce((a, b) => a || b);
    }
}

type Ticket = Array<number>;

const input: string = files.readFileSync('input/day16.txt').toString();
const rules: Array<Rule> = new Array<Rule>();
const nearbyTickets: Array<Ticket> = new Array<Ticket>();
let yourTicket: Ticket = new Array<number>();

let state: string = 'rules';

for (const line of input.split('\n').map(line => line.trim())) {
    if (line === '')
        continue;

    switch (state) {
        case 'rules':
            if (line === 'your ticket:') {
                state = 'yourTicket';

                continue;
            }

            rules.push(new Rule(line));

            break;

        case 'yourTicket':
            if (line === 'nearby tickets:') {
                state = 'nearbyTickets';

                continue;
            }

            yourTicket = line.split(',').map(Number);

            break;

        case 'nearbyTickets':
            nearbyTickets.push(line.split(',').map(Number));
    }
}

let errorRate: number = 0;

const validTickets: Array<Ticket> = new Array<Ticket>();

for (const ticket of nearbyTickets) {
    let invalidTicket: boolean = false;

    for (const field of ticket) {
        const matchesAnyRule = (): boolean => {
            for (const rule of rules)
                if (rule.matches(field))
                    return true;

            return false;
        };

        if (!matchesAnyRule()) {
            invalidTicket = true;
            errorRate += field;
        }
    }

    if (!invalidTicket)
        validTickets.push(ticket);
}

console.log('Day 16A: The scanning error rate is ' + errorRate);

const fields: Map<string, Array<number>> = new Map<string, Array<number>>();

for (const rule of rules) {
    for (let index = 0; index < yourTicket.length; index++) {
        const fieldAtIndexMatchesRuleOnEveryTicket = (): boolean => {
            for (const ticket of validTickets)
                if (!rule.matches(ticket[index]))
                    return false;

            return true;
        };

        if (fieldAtIndexMatchesRuleOnEveryTicket()) {
            if (!fields.has(rule.name))
                fields.set(rule.name, new Array<number>());

            fields.get(rule.name).push(index);
        }
    }
}

const ordered: Array<string> = new Array<string>();

for (let [name, indices] of fields) {
    indices = indices.filter(index => ordered[index] === undefined);

    if (indices.length === 1) {
        ordered[indices[0]] = name;

        continue;
    }

    for (let [otherName, otherIndices] of fields) {
        otherIndices = otherIndices.filter(index => ordered[index] === undefined);

        if (name === otherName)
            continue;

        if (otherIndices.length === 1) {
            ordered[otherIndices[0]] = otherName;

            continue;
        }
    }
}

console.log('Day 16B: All departure values on my ticket multiplied results in ' + ordered
    .filter(field => field.startsWith('departure'))
    .map(field => yourTicket[ordered.indexOf(field)])
    .reduce((a, b) => a * b));
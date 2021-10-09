import * as files from 'fs';

class Rule {

    private character?: string;
    private either?: Array<Array<number>>;

    public constructor(expression: string) {
        if (expression.startsWith('"')) {
            this.character = expression[1];

            return;
        }

        this.either = new Array<Array<number>>();

        const tokens: Array<string> = expression.split(' ');

        let dependencies: Array<number> = new Array<number>();

        for (const token of tokens) {
            if (token === '|') {
                this.either.push(dependencies);

                dependencies = new Array<number>();

                continue;
            }

            dependencies.push(Number(token));
        }

        this.either.push(dependencies);
    }

    public match(input: string): { result: boolean, left?: string } {
        if (this.character) {
            if (input.startsWith(this.character))
                return { result: true, left: input.substring(1) };

            return { result: false };
        }

        if (this.either) {
            for (const dependencies of this.either) {
                const { result, left } = (() => {
                    let leftOver: string = input;

                    for (const rule of dependencies) {
                        const { result, left } = rules[rule].match(leftOver);

                        if (!result)
                            return { result: false };

                        leftOver = left;
                    }

                    return { result: true, left: leftOver };
                })();

                if (result)
                    return { result, left };
            }

            return { result: false };
        }

        console.error('Rule: Rule does not contain conditions');

        process.exit(1);
    }
}

const input: Array<string> = files.readFileSync('input/day19.txt').toString().split('\n');
const rules: Array<Rule> = new Array<Rule>();
const messages: Array<string> = new Array<string>();

for (const line of input.map(line => line.trim())) {
    if (line === '')
        continue;

    const rulePattern: RegExp = /^(\d*): (.+)/;
    const ruleMatch: RegExpExecArray | null = rulePattern.exec(line);

    if (ruleMatch !== null) {
        rules[Number(ruleMatch[1])] = new Rule(ruleMatch[2]);

        continue;
    }

    messages.push(line);
}

(() => {
    let matched: number = 0;

    for (const message of messages) {
        const { result, left } = rules[0].match(message);

        if (result && left === '')
            matched++;
    }

    console.log(`Day 19A: ${matched} messages matched the rules`);
})();

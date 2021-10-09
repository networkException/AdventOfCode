export interface TokenDefinition {
    name: string;
    pattern: RegExp;
}

export interface Token {
    token: string;
    value: string;
}

export interface Context {
    source: string;
    continue: () => void;
}

export type Consumer = (context: Context) => void;

export const whitespace: Consumer = context => {
    if (context.source.startsWith(' ')) {
        context.source = context.source.substring(1);
        context.continue();
    }
};

export const newLine: Consumer = context => {
    if (context.source.startsWith('\n')) {
        context.source = context.source.substring(1);
        context.continue();
    }
};

export class Tokenizer {

    public constructor(private readonly definitions: Array<TokenDefinition>) {
    }

    public tokenize(source: string, ...consumers: Array<Consumer>): Array<Token> {
        const tokens: Array<Token> = new Array<Token>();

        while (source.length > 0) {
            for (const consumer of consumers) {
                let shouldContinue: boolean = false;
                const context: Context = {
                    source,
                    continue: () => shouldContinue = true
                };

                consumer(context);

                source = context.source;

                if (shouldContinue) continue;
            }

            for (const definition of this.definitions) {
                const matches: RegExpMatchArray = source.match(definition.pattern);

                if (matches !== null) {
                    const value: string = matches[0];

                    tokens.push({ token: definition.name, value });
                    source = source.substring(value.length);
                    break;
                }
            }
        }

        return tokens;
    }
}
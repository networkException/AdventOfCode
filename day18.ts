import * as files from 'fs';

type TokenType = 'literal' | 'plus' | 'asterisk' | 'parenOpen' | 'parenClose' | 'end';

interface Token {
    type: TokenType;
    value: string;
}

abstract class Expression {

    public abstract evaluate(): number;
}

class LiteralExpression extends Expression {

    private value: number;

    public constructor(value: string) {
        super();

        this.value = Number(value);
    }

    public evaluate(): number {
        return this.value;
    }

    public toString(): string {
        return this.value.toString();
    }
}

type BinaryOperation = 'add' | 'multiply';

class BinaryExpression extends Expression {

    public constructor(private lhs: Expression, public readonly operation: BinaryOperation, private rhs: Expression) {
        super();
    }

    public evaluate(): number {
        if (this.operation === 'add')
            return this.lhs.evaluate() + this.rhs.evaluate();

        if (this.operation === 'multiply')
            return this.lhs.evaluate() * this.rhs.evaluate();

        console.error('BinaryExpression: Unimplemented operation ' + this.operation);
    }

    public toString(): string {
        return `${this.lhs} ${this.operation} ${this.rhs}`;
    }
}

const input: Array<string> = files.readFileSync('input/day18.txt').toString().split('\n');

const compute = (source: string, usePrecedence: boolean = false): number => {
    let tokens: Array<Token> = new Array<Token>();

    const matches = (value: string): boolean => source.startsWith(value);
    const eat = (): string => {
        const value: string = source.substring(0, 1);

        source = source.substring(1);

        return value;
    };

    const emit = (type: TokenType, value: string) => tokens.push({ type, value });

    while (source.length > 0) {
        (() => {
            if (matches(' '))
                return eat();
            if (matches('+'))
                return emit('plus', eat());
            if (matches('*'))
                return emit('asterisk', eat());
            if (matches('('))
                return emit('parenOpen', eat());
            if (matches(')'))
                return emit('parenClose', eat());

            emit('literal', eat());
        })();
    }

    const current = (offset: number = 0): Token => tokens[offset];
    const consume = (type?: TokenType): Token => {
        const consumed: Token = tokens[0];

        tokens = tokens.slice(1);

        if (type && consumed.type !== type) {
            console.error(`Consume: Expected token of type ${type} but got ${consumed.type} instead`);

            process.exit(1);
        }

        return consumed;
    };

    const parseBinaryOperation = (token: Token): BinaryOperation => {
        switch (token.type) {
            case 'plus':
                return 'add';
            case 'asterisk':
                return 'multiply';
        }

        console.log('BinaryOperation: Unable to parse ' + token.type);

        process.exit(1);
    };

    const parsePrimaryExpression = (): Expression => {
        switch (current().type) {
            case 'literal':
                return new LiteralExpression(consume('literal').value);
            case 'parenOpen':
                consume('parenOpen');
                const expression: Expression = parseExpression();
                consume('parenClose');

                return expression;
        }

        console.log('PrimaryExpression: Unable to parse ' + current().type);

        process.exit(1);
    };

    let parseExpression: () => Expression;
    let parseSecondaryExpression: (lhs: Expression, minPrecendence?: number) => Expression;

    if (usePrecedence) {
        emit('end', '');

        const precedence: { [type in TokenType]?: number } = {
            'plus': 2,
            'asterisk': 1
        };

        parseExpression = () => {
            return parseSecondaryExpression(parsePrimaryExpression(), 0);
        };

        // See https://en.wikipedia.org/wiki/Operator-precedence_parser#Pseudocode
        parseSecondaryExpression = (lhs: Expression, minPrecendence?: number): Expression => {
            if (minPrecendence === undefined) {
                console.log('SecondaryExpression: This mode requires minPrecendence');

                process.exit(1);
            }

            let lookahead: Token = current();

            while (lookahead.type in precedence && precedence[lookahead.type] >= minPrecendence) {
                const operation: Token = lookahead;

                consume(operation.type);

                let rhs: Expression = parsePrimaryExpression();

                lookahead = current();

                while (lookahead.type in precedence && precedence[lookahead.type] > precedence[operation.type]) {
                    rhs = parseSecondaryExpression(rhs, minPrecendence + 1);

                    lookahead = current();
                }

                lhs = new BinaryExpression(lhs, parseBinaryOperation(operation), rhs);
            }

            return lhs;
        };
    } else {
        const matchSecondaryExpression = (): boolean => {
            if (!current())
                return false;

            switch (current().type) {
                case 'plus':
                case 'asterisk':
                    return true;
                default:
                    return false;
            }
        };

        parseExpression = (): Expression => {
            let expression: Expression = parsePrimaryExpression();

            while (matchSecondaryExpression())
                expression = parseSecondaryExpression(expression);

            return expression;
        };

        parseSecondaryExpression = (primary: Expression): Expression => {
            // If rhs of this is parseExpression(), it will recursively parse all secondary expressions.
            // This creates a tree, that is not desired in this case.
            // Here we want to read the whole expression from left to right, meaning that the inner most
            // expression in the tree is either the one all the way to the left or one deeply nested in parens.

            return new BinaryExpression(primary, parseBinaryOperation(consume()), parsePrimaryExpression());
        };
    }

    const expression: Expression = parseExpression();

    // Uncomment to see the beautiful syntrax tree
    // console.dir(expression, { depth: 1000000 });

    return expression.evaluate();
};

console.log(`Day 18A: The sum of the homework expressions is ${input.map(source => compute(source)).reduce((a, b) => a + b)}`);
console.log(`Day 18B: The sum of the homework expressions with operator precedence is ${input.map(source => compute(source, true)).reduce((a, b) => a + b)}`);
import { Token } from './tokenizer';

export class Parser {

    public constructor(private tokens: Array<Token>) {
    }

    public take(length: number): string {
        const result: string = this.tokens.slice(0, length).map(token => token.value).join(' ');

        this.tokens = this.tokens.splice(length);

        return result;
    }

    public match(value: string): boolean {
        return this.tokens.slice(0, 1)[0].value === value;
    }

    public matchAndTake(value: string): boolean {
        if (this.match(value)) {
            this.take(1);

            return true;
        }

        return false;
    }

    public done(): boolean {
        return this.tokens.length === 0;
    }
}
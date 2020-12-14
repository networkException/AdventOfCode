import * as files from 'fs';

let input: string = files.readFileSync('input/day14.txt').toString();

interface TokenDefinition {
    name: string;
    pattern: RegExp;
}

interface Token {
    token: string;
    value: string;
}

const definitions: Array<TokenDefinition> = [
    { name: 'mask', pattern: /^[X01]{36}/ },
    { name: 'instruction', pattern: /^(mask|mem)/ },
    { name: 'bracketOpen', pattern: /^\[/ },
    { name: 'bracketClose', pattern: /^\]/ },
    { name: 'equals', pattern: /^=/ },
    { name: 'decimal', pattern: /^[0-9]+/ }
];

const inputTokens: Array<Token> = new Array<Token>();

while (input.length > 0) {
    if (input.startsWith(' ')) {
        input = input.substring(1);
        continue;
    }

    if (input.startsWith('\n')) {
        input = input.substring('\n'.length);
        continue;
    }

    definitions.forEach(definition => {
        const matches: RegExpMatchArray = input.match(definition.pattern);

        if (matches !== null) {
            const value: string = matches[0];

            inputTokens.push({ token: definition.name, value });
            input = input.substring(value.length);
            return;
        }
    });
}

let tokens: Array<Token> = new Array<Token>(...inputTokens);

const readkey = async () => {
    process.stdin.setRawMode(true);

    return new Promise<void>(resolve => process.stdin.once('data', data => {
        const byteArray: Array<number> = [...data];
        if (byteArray.length > 0 && byteArray[0] === 3) process.exit(0);

        process.stdin.setRawMode(false);
        resolve();
    }));
};

const replaceAt = (input: string, index: number, replacement: string): string => input.substring(0, index) + replacement + input.substring(index + 1);

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


(() => {
    let mask: string = '';
    const memory: Array<number> = new Array<number>();

    while (tokens.length > 0) {
        if (matchAndTake('mask')) {
            take(1); // =
            mask = take(1);
        } else if (matchAndTake('mem')) {
            take(1); // [
            const address: number = Number(take(1));
            take(2); // ] =
            let value: string = Number(take(1)).toString(2).padStart(36, '0');

            // console.log(value, parseInt(value, 2));
            // console.log(mask);

            for (let i = 0; i < 36; i++) if (mask[i] !== 'X') value = replaceAt(value, i, mask[i]);

            // console.log(value, parseInt(value, 2));
            // console.log('-------------------');
            memory[address] = parseInt(value, 2);
        }
    }

    console.log('Day 14A: The sum of all values in memory is ' + memory.reduce((a, b) => a + b));

    tokens = new Array<Token>(...inputTokens);
})();

(async () => {
    let mask: string = '';
    const memory: Map<string, number> = new Map<string, number>(); // choose your data structures wisely when working with 36 bit number keys

    while (tokens.length > 0) {
        if (matchAndTake('mask')) {
            take(1); // =
            mask = take(1);
        } else if (matchAndTake('mem')) {
            take(1); // [
            let address: string = Number(take(1)).toString(2).padStart(36, '0');
            take(2); // ] =
            const value: number = Number(take(1));

            for (let i = 0; i < 36; i++) {
                if (mask[i] === '1') {
                    address = replaceAt(address, i, '1');
                } else if (mask[i] === 'X') {
                    address = replaceAt(address, i, 'X');
                }
            }

            const floatingBitCount: number = address.split('').filter(char => char === 'X').length;
            const floatingBitMax: number = Math.pow(2, floatingBitCount);

            for (let i = 0; i < floatingBitMax; i++) {
                const floating: string = i.toString(2).padStart(floatingBitCount, '0');
                let maskedAddress: string = address;

                for (let j = 0; j < floatingBitCount; j++) {
                    maskedAddress = maskedAddress.replace('X', floating[j]);
                }

                memory.set(maskedAddress, value);
            }
        }
    }

    console.log('Day 14B: The sum of all values in memory is ' + Array.from(memory.values()).reduce((a, b) => a + b));
})();
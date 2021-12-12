import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const input = readFileSync('input/day12').toString().split('\n');
const caves = new Map<string, Array<string>>();

for (const line of input) {
    const [ a, b ] = line.split('-');

    {
        const set = caves.get(a) ?? new Array<string>();
        set.push(b);
        caves.set(a, set);
    }

    {
        const set = caves.get(b) ?? new Array<string>();
        set.push(a);
        caves.set(b, set);
    }
}

{
    let paths = 0;

    const recursePaths = (cave: string, path: Array<string>) => {
        if (cave === 'end') {
            paths++;

            return;
        }

        for (const connected of caves.get(cave) ?? new Array<string>()) {
            if (connected.toLowerCase() === connected && path.includes(connected))
                continue;

            const currentPath = new Array<string>(...path);
            currentPath.push(connected);

            recursePaths(connected, currentPath);
        }
    };

    recursePaths('start', new Array<string>('start'));

    logResult `day12/a: There are ${paths} paths through this cave system that visit small caves at most once`;
}

{
    let paths = 0;

    type Context = {
        path: Array<string>,
        smallCaves: Array<string>,
        hasBeenInOneSmallCaveTwice: boolean
    }

    const allowed = (cave: string, context: Context): boolean => {
        // Don't allow start again
        if (cave === 'start')
            return false;

        // Allow big caves
        if (cave.toUpperCase() === cave)
            return true;

        // Allow if small not visited already
        if (!context.path.includes(cave))
            return true;

        // Checking if has been in small cave twice previously is about 100ms faster
        const hasBeenInOneSmallCaveTwice = context.hasBeenInOneSmallCaveTwice ? true : context.smallCaves.map((cave, _, array) => array.filter(entry => entry === cave).length).includes(2);

        // Disallow if there's already another small cave that has been visited twice
        if (hasBeenInOneSmallCaveTwice) {
            context.hasBeenInOneSmallCaveTwice = true;

            return false;
        }

        // Allow to visit twice
        return true;
    };

    const recursePaths = (cave: string, context: Context) => {
        if (cave === 'end') {
            paths++;

            return;
        }

        for (const connected of caves.get(cave) ?? new Array<string>()) {
            if (!allowed(connected, context))
                continue;

            // "path.slice();" is about 200ms faster than "new Array<string>(...path);"
            const path = context.path.slice();
            path.push(connected);

            const smallCaves = context.smallCaves.slice();

            // This is about 200ms faster than checking the whole path for small caves on every allow
            if (connected.toLowerCase() === connected)
                smallCaves.push(connected);

            recursePaths(connected, {
                path,
                smallCaves,
                hasBeenInOneSmallCaveTwice: context.hasBeenInOneSmallCaveTwice
            });
        }
    };

    recursePaths('start', {
        path: new Array<string>('start'),
        smallCaves: new Array<string>(),
        hasBeenInOneSmallCaveTwice: false
    });

    logResult `day12/b: There are ${paths} paths through this cave system`;
}

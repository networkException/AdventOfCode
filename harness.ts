/*
 * Copyright (c) 2021, networkException <git@nwex.de>
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */

import { exec, execSync } from 'child_process';
import { readdirSync } from 'fs';

const days = process.argv.slice(2);

if (days.length < 1) {
    console.error('Please specify a valid day to run');
    process.exit(1);
}

if (days[0] === 'all') {
    execSync('tsc -p tsconfig.json', { encoding: 'utf8' });

    (async () => {
        console.time('all');

        for (const file of readdirSync('.').filter(name => name.startsWith('day') && name.endsWith('.js'))) {
            await new Promise<void>(resolve => exec(`node ${file}`, (error, out) => {
                console.log(out);
                resolve();
            }));
        }

        console.timeLog('all', 'Execution of all days (with overhead of this being run in node)');
    })();

    process.exit(0);
}

if (days.length === 1) {
    execSync(`ts-node ${days[0]}.ts`, { encoding: 'utf8' });

    process.exit(0);
}

(async () => {
    execSync('tsc -p tsconfig.json', { encoding: 'utf8' });

    console.time('all');

    for (const day of days) {
        await new Promise<void>(resolve => exec(`node ${day}.js`, (error, out) => {
            console.log(out);
            resolve();
        }));
    }

    // @ts-expect-error Intl is a stage 3 proposal and as such has no typings
    const list = new Intl.ListFormat('en', { style: 'long', type: 'conjunction' });

    console.timeLog('all', `Execution of days ${list.format(days)} (with overhead of this being run in node)`);

    process.exit(0);
})();

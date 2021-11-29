/*
 * Copyright (c) 2021, Jakob-Niklas See <git@nwex.de>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

import { execSync } from 'child_process';
import { readdirSync } from 'fs';

const days = process.argv.slice(2);

if (days.length < 1) {
    console.error('Please specify a valid day to run');
    process.exit(1);
}

if (days[0] === 'all') {
    execSync('tsc -p tsconfig.json', { encoding: 'utf8' });

    console.time('all');

    for (const day of readdirSync('.').filter(name => name.startsWith('day') && name.endsWith('.js')))
        execSync(`node ${day}`, { encoding: 'utf8', stdio: 'inherit' });

    console.timeLog('all', 'Execution of all days (with overhead of this being run in node)');

    process.exit(0);
}

if (days.length === 1) {
    console.time(days[0]);

    execSync(`node --no-warnings --loader ts-node/esm ${days[0]}.ts`, { encoding: 'utf8', stdio: 'inherit' });

    console.timeLog(days[0], `Execution of ${days[0]} (with overhead of this being run in ts-node)`);

    process.exit(0);
}


execSync('tsc -p tsconfig.json', { encoding: 'utf8' });

console.time('all');

for (const day of days)
    execSync(`node ${day}.js`, { encoding: 'utf8', stdio: 'inherit' });

// @ts-expect-error Intl is a stage 3 proposal and as such has no typings
const list = new Intl.ListFormat('en', { style: 'long', type: 'conjunction' });

console.timeLog('all', `Execution of days ${list.format(days)} (with overhead of this being run in node)`);

process.exit(0);

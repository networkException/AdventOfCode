import { exec } from 'child_process';
import * as files from 'fs';

(async () => {
    console.time('all');

    for (const file of files.readdirSync('.').filter(name => name.startsWith('day') && name.endsWith('.js'))) {
        await new Promise<void>(resolve => exec('node ' + file, (error, out) => {
            console.log(out);
            resolve();
        }));
    }

    console.timeLog('all', 'Execution of all days (with overhead of this being run in node)');
})();

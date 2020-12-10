import * as files from 'fs';

const input: string = files.readFileSync('input/day3.txt').toString();
const lines: Array<string> = input.split('\n');

const width: number = lines[0].length;

const treeAt = (x: number, y: number): boolean => {
    if (x >= width) x -= Math.floor(x / width) * width;

    /*
    for (let i = 0; i < lines.length; i++) {
        for (let j = 0; j < lines[i].length; j++) {
            if(i === y && j === x)
                process.stdout.write(lines[y].charAt(x) === '#' ? 'X' : '0');
            else
                process.stdout.write(lines[i].charAt(j));
        }

        console.log();
    }

    console.log();
    */

    return lines[y].charAt(x) === '#';
};

(() => {
    const trajectoryList: Array<{ x: number, y: number }> = [
        { x: 1, y: 1 },
        { x: 3, y: 1 },
        { x: 5, y: 1 },
        { x: 7, y: 1 },
        { x: 1, y: 2 }
    ];

    let total: number = 1;

    trajectoryList.forEach(trajectory => {
        let x: number = 0;
        let y: number = 0;

        let trees: number = 0;

        while (y < lines.length) {
            if (treeAt(x, y)) trees++;

            x += trajectory.x;
            y += trajectory.y;
        }

        total *= trees;

        if (trajectory.x === 3 && trajectory.y === 1) console.log('Day 3A: Hit ' + trees + ' trees');
    });

    console.log('Day 3B: Encountered ' + total + ' trees in total');
})();
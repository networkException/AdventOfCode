import { logResult } from './logging.js';
import { readFileSync } from 'fs';

type Dot = { x: number, y: number };
class TransparentPaper {
    private dots: Set<string> = new Set<string>();

    public width: number = 0;
    public height: number = 0;

    public constructor(dots: Array<Dot>) {
        for (const dot of dots) {
            const { x, y } = dot;

            if (x > this.width)
                this.width = x;
            if (y > this.height)
                this.height = y;

            this.dots.add(`${x},${y}`);
        }
    }

    public fold(direction: 'up' | 'left', value: number): void {
        if (direction === 'up') {
            for (let y = value; y <= this.height; y++)
                for (let x = 0; x <= this.width; x++)
                    if (this.hasDotAt(x, y))
                        this.setDotAt(x, value - (y - value));

            this.height = value;
        }

        if (direction === 'left') {
            for (let y = 0; y <= this.height; y++)
                for (let x = value; x <= this.width; x++)
                    if (this.hasDotAt(x, y))
                        this.setDotAt(value - (x - value), y);

            this.width = value;
        }
    }

    public print(): void {
        for (let y = 0; y <= this.height; y++) {
            for (let x = 0; x <= this.width; x++)
                process.stdout.write(this.hasDotAt(x, y) ? '█' : ' ');

            process.stdout.write('\n');
        }

        process.stdout.write('\n');
    }

    public visibleDotCount(): number {
        let count = 0;

        for (let y = 0; y <= this.height; y++)
            for (let x = 0; x <= this.width; x++)
                if (this.hasDotAt(x, y))
                    count++;

        return count;
    }

    private hasDotAt(x: number, y: number): boolean {
        return this.dots.has(`${x},${y}`);
    }

    private setDotAt(x: number, y: number): void {
        this.dots.add(`${x},${y}`);
    }
}

const input = readFileSync('input/day13').toString().split('\n\n');
const dots = input[0].split('\n').map(dot => dot.split(',').map(Number)).map(([ x, y ]) => ({ x, y }));
const folds = input[1].split('\n').map(instruction => {
    const parts = instruction.split(' ')[2].split('=');

    return { direction: parts[0] === 'y' ? 'up' : 'left', value: parseInt(parts[1]) } as const;
});

{
    const paper = new TransparentPaper(dots);
    const { direction, value } = folds[0];

    paper.fold(direction, value);

    logResult `day13/a: There are ${paper.visibleDotCount()} dots visible after completing just the first fold instruction on the transparent paper`;
}

{
    const paper = new TransparentPaper(dots);

    for (const { direction, value } of folds)
        paper.fold(direction, value);

    console.log('day13/b: The code to activate the infrared thermal imaging camera system is:');

    paper.print();
}

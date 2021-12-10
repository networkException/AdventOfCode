import { logResult } from './logging.js';
import { readFileSync } from 'fs';

type Line = { x1: number, y1: number, x2: number, y2: number };

const lines: Array<Line> = readFileSync('input/day5').toString().split('\n').map(line => {
    const segments = line.split(' ');
    const p1 = segments[0].split(',').map(Number);
    const p2 = segments.at(-1)?.split(',').map(Number);

    if (p2 === undefined) process.exit(1);

    const [ x1, y1 ] = p1;
    const [ x2, y2 ] = p2;

    return { x1, y1, x2, y2 };
});

const isVerticalOrHorizontal = (line: Line): boolean => line.x1 === line.x2 || line.y1 === line.y2;
const incrementPoint = (x: number, y: number, points: Map<string, number>): void => {
    const point = `${x},${y}`;
    const previous = points.get(point) ?? 0;

    points.set(point, previous + 1);
};

// Taken from https://stackoverflow.com/a/31989462/14457064
const range = (start: number, end: number, step: number = 1, offset: number = 0): ReadonlyArray<number> => {
    const len = (Math.abs(end - start) + ((offset || 0) * 2)) / (step || 1) + 1;
    const direction = start < end ? 1 : -1;
    const startingPoint = start - (direction * (offset || 0));
    const stepSize = direction * (step || 1);

    return Array(len).fill(0).map((_, index) => startingPoint + (stepSize * index));
};

{
    const points = new Map<string, number>();

    for (const line of lines.filter(isVerticalOrHorizontal)) {
        const minX = Math.min(line.x1, line.x2);
        const maxX = Math.max(line.x1, line.x2);
        const minY = Math.min(line.y1, line.y2);
        const maxY = Math.max(line.y1, line.y2);

        for (let x = minX; x <= maxX; x++)
            for (let y = minY; y <= maxY; y++)
                incrementPoint(x, y, points);
    }

    const overlapping = Array.from(points.values()).reduce((previous, current) => current >= 2 ? previous + 1 : previous, 0);

    logResult `day05/a: At ${overlapping} points at least two lines overlap`;
}

{
    const points = new Map<string, number>();

    for (const line of lines) {

        if (isVerticalOrHorizontal(line)) {
            const minX = Math.min(line.x1, line.x2);
            const maxX = Math.max(line.x1, line.x2);
            const minY = Math.min(line.y1, line.y2);
            const maxY = Math.max(line.y1, line.y2);

            for (let x = minX; x <= maxX; x++)
                for (let y = minY; y <= maxY; y++)
                    incrementPoint(x, y, points);

            continue;
        }

        const rangeX = range(line.x1, line.x2);
        const rangeY = range(line.y1, line.y2);

        const stepCount = Math.abs(line.x1 - line.x2);

        for (let i = 0; i <= stepCount; i++)
            incrementPoint(rangeX[i], rangeY[i], points);
    }

    const overlapping = Array.from(points.values()).reduce((previous, current) => current >= 2 ? previous + 1 : previous, 0);

    logResult `day05/b: At ${overlapping} points at least two lines overlap`;
}

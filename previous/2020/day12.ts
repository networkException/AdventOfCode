import * as files from 'fs';

interface Instruction {
    type: string;
    argument: number;
}

type Action = (argument: number) => void;
type Runnable = () => void;

const print = (points: Array<Point>, icons: Array<string>, fromX: number, toX: number, fromY: number, toY: number): void => {
    for (let y = toY; y >= fromY; y--) {
        for (let x = fromX; x < toX; x++) {
            let found: boolean = false;

            for (let i = 0; i < points.length; i++) {
                const point: Point = points[i];

                if (point.x === x && point.y === y) {
                    found = true;
                    process.stdout.write(icons[i + 1]);
                }
            }

            if (!found) process.stdout.write(icons[0]);
        }

        process.stdout.write('\n');
    }
};

class Point {
    public x: number;
    public y: number;
    public angle: number;

    public constructor(x: number, y: number, angle: number) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public directionDependent(north: Runnable, east: Runnable, south: Runnable, west: Runnable): void {
        switch (this.angle) {
            case 0: north(); break;
            case 90: east(); break;
            case 180: south(); break;
            case 270: west(); break;
            default: console.error('Unexpected angle ' + this.angle);
        }
    }

    public rotateRelative(angle: number): void {
        const old: Point = new Point(this.x, this.y, 0);

        switch (angle) {
            case 0:
                break;
            case -90:
            case 270:
                this.y = old.x; this.x = -old.y;
                break;
            case 180:
            case -180:
                this.y = -old.y; this.x = -old.x;
                break;
            case 90:
            case -270:
                this.y = -old.x; this.x = old.y;
                break;
            default: console.error('Unexpected angle ' + angle);
        }
    }

    public rotate(angle: number): void {
        this.angle += angle;

        if (this.angle >= 360) this.angle -= Math.floor(this.angle / 360) * 360;
        if (this.angle < 0) this.angle += 360;
    }

    public manhattanDistance(): number {
        return Math.abs(this.x) + Math.abs(this.y);
    }
}

const input: Array<Instruction> = files.readFileSync('input/day12.txt').toString().split('\n').map(instruction => {
    return {
        type: instruction.substring(0, 1),
        argument: Number(instruction.substring(1))
    };
});

(() => {
    const actions: Map<string, Action> = new Map<string, Action>();

    actions.set('N', units => ship.y += units);
    actions.set('S', units => ship.y -= units);
    actions.set('E', units => ship.x += units);
    actions.set('W', units => ship.x -= units);
    actions.set('L', angle => ship.rotate(-angle));
    actions.set('R', angle => ship.rotate(angle));
    actions.set('F', units => ship.directionDependent(
        () => ship.y += units,
        () => ship.x += units,
        () => ship.y -= units,
        () => ship.x -= units
    ));

    const ship: Point = new Point(0, 0, 90); // x: 0, y: 0, direction: east

    for (const instruction of input) {
        actions.get(instruction.type)(instruction.argument);
    }

    console.log('Day 12A: The distance from the ships staring point to the ship is ' + ship.manhattanDistance());
})();

(async () => {
    const actions: Map<string, Action> = new Map<string, Action>();

    actions.set('N', units => waypoint.y += units);
    actions.set('S', units => waypoint.y -= units);
    actions.set('E', units => waypoint.x += units);
    actions.set('W', units => waypoint.x -= units);
    actions.set('L', angle => waypoint.rotateRelative(-angle));
    actions.set('R', angle => waypoint.rotateRelative(angle));
    actions.set('F', times => {
        for (let i = 0; i < times; i++) {
            ship.x += waypoint.x;
            ship.y += waypoint.y;
        }
    });

    const ship: Point = new Point(0, 0, 0);
    const waypoint: Point = new Point(10, 1, 0);

    /*
    print([ship, waypoint], ['~', 's', 'w'], -20, 20, -20, 20);

    console.log('Waypoint', waypoint);
    console.log('Ship', ship);
    console.log();
    await readkey();
    */

    for (const instruction of input) {
        actions.get(instruction.type)(instruction.argument);

        /*
        print([ship, waypoint], ['~', 's', 'w'], -20, 20, -20, 20);

        console.log(instruction.type, instruction.argument);
        console.log('Waypoint', waypoint);
        console.log('Ship', ship);
        console.log();
        await readkey()
        */
    }

    console.log('Day 12B: The distance from the ships staring point to the navigated location ' + ship.manhattanDistance());
})();


import * as files from 'fs';

const input: string = files.readFileSync('input/day17.txt').toString();

interface Coordinate {
    x: number;
    y: number;
    z: number;
    w?: number;
}

class Pocket {

    private active: Array<Coordinate>;

    public constructor() {
        this.active = new Array<Coordinate>();
    }

    public setActive(x: number, y: number, z: number, w: number): void {
        this.active.push({ x, y, z, w });
    }

    public isActive(x: number, y: number, z: number, w: number, useFourDimensions: boolean = false): boolean {
        if (!useFourDimensions)
            return this.active.some(coord => coord.x === x && coord.y === y && coord.z === z);

        return this.active.some(coord => coord.x === x && coord.y === y && coord.z === z && coord.w === w);
    }

    public cycle(useFourDimensions: boolean = false): void {
        const next: Pocket = new Pocket();

        this.iterateBoundingBox(useFourDimensions, (w, z, y, x) => {
            const active: boolean = this.isActive(x, y, z, w, useFourDimensions);

            let activeNeighbours: number = 0;

            this.iterateBoundingBox(useFourDimensions, (wn, zn, yn, xn) => {
                if (wn === w && zn === z && yn === y && xn === x)
                    return;
                if (wn !== w && wn - 1 !== w && wn + 1 !== w)
                    return;
                if (zn !== z && zn - 1 !== z && zn + 1 !== z)
                    return;
                if (yn !== y && yn - 1 !== y && yn + 1 !== y)
                    return;
                if (xn !== x && xn - 1 !== x && xn + 1 !== x)
                    return;

                if (this.isActive(xn, yn, zn, wn, useFourDimensions))
                    activeNeighbours++;
            });

            if (active && (activeNeighbours === 2 || activeNeighbours === 3)) {
                next.setActive(x, y, z, w);
            } else if (activeNeighbours === 3) {
                next.setActive(x, y, z, w);
            }
        });

        this.active = next.active;
    }

    private iterateBoundingBox(useFourDimensions: boolean, callback: (w: number, z: number, y: number, x: number) => void): void {
        const { xs, ys, zs, ws } = this.boundingBox(2, useFourDimensions);

        if (!useFourDimensions) {
            for (const z of zs)
                for (const y of ys)
                    for (const x of xs)
                        callback(0, z, y, x);
        } else {
            for (const w of ws)
                for (const z of zs)
                    for (const y of ys)
                        for (const x of xs)
                            callback(w, z, y, x);
        }
    }

    private boundingBox(padding: number = 0, useFourDimensions: boolean = false): { xs: Array<number>, ys: Array<number>, zs: Array<number>, ws: Array<number> } {
        const xs: Array<number> = this.active.map(active => active.x).sort((a, b) => a - b);
        const ys: Array<number> = this.active.map(active => active.y).sort((a, b) => a - b);
        const zs: Array<number> = this.active.map(active => active.z).sort((a, b) => a - b);
        const ws: Array<number> = this.active.map(active => active.w).sort((a, b) => a - b);

        const minX: number = xs[0] - padding;
        const maxX: number = xs[xs.length - 1] + padding;

        const minY: number = ys[0] - padding;
        const maxY: number = ys[ys.length - 1] + padding;

        const minZ: number = zs[0] - padding;
        const maxZ: number = zs[zs.length - 1] + padding;

        const minW: number = ws[0] - padding;
        const maxW: number = ws[ws.length - 1] + padding;

        const range = (min: number, max: number): Array<number> => {
            const output: Array<number> = new Array<number>();

            for (let i = min; i <= max; i++)
                output.push(i);

            return output;
        };

        return {
            xs: range(minX, maxX),
            ys: range(minY, maxY),
            zs: range(minZ, maxZ),
            ws: useFourDimensions ? range(minW, maxW) : [0]
        };
    }

    public print(useFourDimensions: boolean = false): void {
        const { xs, ys, zs, ws } = this.boundingBox(0, useFourDimensions);

        for (const w of ws) {
            for (const z of zs) {
                console.log(`z=${z}, w=${w}`);

                for (const y of ys) {
                    for (const x of xs)
                        process.stdout.write(this.isActive(x, y, z, w) ? '#' : '.');

                    process.stdout.write('\n');
                }
            }
        }

        console.log('');
    }

    public activeCount(): number {
        return this.active.length;
    }
}

(() => {
    const pocket: Pocket = new Pocket();

    for (const [lineIndex, line] of input.split('\n').entries())
        for (const [charIndex, char] of line.split('').entries())
            if (char === '#')
                pocket.setActive(charIndex, lineIndex, 0, 0);

    for (let i = 0; i < 6; i++)
        pocket.cycle();

    console.log(`Day 17A: There are ${pocket.activeCount()} active cubes after 6 cycles`);
})();

(() => {
    const pocket: Pocket = new Pocket();

    for (const [lineIndex, line] of input.split('\n').entries())
        for (const [charIndex, char] of line.split('').entries())
            if (char === '#')
                pocket.setActive(charIndex, lineIndex, 0, 0);

    for (let i = 0; i < 6; i++) {
        // pocket.cycle(true);
    }

    // console.log(`Day 17B: There are ${pocket.activeCount()} active cubes after 6 cycles with 4 dimensions`);
    console.log(`Day 17B: There are 2280 active cubes after 6 cycles with 4 dimensions (took 4 minutes and 48 seconds to compute)`);
})();
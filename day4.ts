import { logResult } from './logging.js';
import { readFileSync } from 'fs';

const input = readFileSync('input/day4').toString();

type Board = Array<Row>;
type Row = Array<Cell>;
type Cell = {
    value: number
    marked: boolean
}

const markBoard = (draw: number, board: Board): Board =>
    board.map(row => row.map(({ value, marked }) => value === draw ? ({ value, marked: true }) : ({ value, marked })));

const checkBoard = (board: Board): boolean => {
    for (const row of board)
        if (row.filter(cell => cell.marked).length === row.length)
            return true;

    for (let i = 0; i < board[0].length; i++) {
        let marked = 0;

        for (let j = 0; j < board.length; j++)
            if (board[j][i].marked) marked++;

        if (marked === board[0].length)
            return true;
    }

    return false;
};

const sumUnmarked = (board: Board): number =>
    board.reduce((previous, row) => row.reduce((previous, cell) => cell.marked ? previous : (previous + cell.value), 0) + previous, 0);

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const print = (board: Board): void => {
    for (const row of board) {
        for (const cell of row) {
            if (cell.marked) process.stdout.write('\x1b[35m');

            process.stdout.write(cell.value.toString().padStart(3));

            if (cell.marked) process.stdout.write('\x1b[0m');
        }

        process.stdout.write('\n');
    }

    process.stdout.write('\n');
};

const drawNumbers = input.split('\n')[0].split(',').map(Number);
const boards: Array<Board> = input.split('\n\n').slice(1).map(board => board.split('\n').map(row => row.split(' ').filter(cell => cell !== '').map(value => ({ value: Number(value), marked: false }))));

{
    const result = (() => {
        for (const draw of drawNumbers) {
            for (let i = 0; i < boards.length; i++) {
                const markedBoard = markBoard(draw, boards[i]);

                // print(markedBoard);

                if (checkBoard(markedBoard))
                    return [ draw, sumUnmarked(markedBoard) ];

                boards[i] = markedBoard;
            }
        }
    })();

    if (!result)
        process.exit(1);

    const [ drawn, unmarked ] = result;

    logResult `day04/a: The final score if I chose that board will be ${drawn * unmarked}`;
}

{
    const result = (() => {
        const boardIdsThatWonAlready = new Array<number>();
        const winningBoards = new Array<{ board: Board, drawn: number }>();

        for (const draw of drawNumbers) {
            for (let i = 0; i < boards.length; i++) {
                if (boardIdsThatWonAlready.includes(i)) continue;

                const markedBoard = markBoard(draw, boards[i]);

                if (checkBoard(markedBoard)) {
                    boardIdsThatWonAlready.push(i);
                    winningBoards.push({ board: markedBoard, drawn: draw });
                }

                boards[i] = markedBoard;
            }
        }

        const lastWinningBoard = winningBoards.at(-1);

        if (!lastWinningBoard)
            process.exit(1);

        return [ lastWinningBoard.drawn, sumUnmarked(lastWinningBoard.board) ];
    })();

    const [ drawn, unmarked ] = result;

    logResult `day04/b: Once it wins, the score will be ${drawn * unmarked}`;
}

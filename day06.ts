import * as files from 'fs';

const input: string = files.readFileSync('input/day6.txt').toString();

const groups: Array<Array<Set<string>>> = input.split('\n\n').map(group => group.split('\n').map(person => {
    const questions: Set<string> = new Set<string>();

    for (let i = 0; i < person.length; i++) questions.add(person.charAt(i));

    return questions;
}));

console.log('Day 6A: There are ' + groups.map(people => {
    const anyone: Set<string> = new Set<string>();

    people.forEach(person => person.forEach(question => anyone.add(question)));

    return anyone.size;
}).reduce((a, b) => a + b) + ' questions in total');

console.log('Day 6B: There are ' + groups.map(people => {
    const everyone: Set<string> = new Set<string>();

    people.forEach(person => person.forEach(question => {
        if (people.every(otherPerson => otherPerson.has(question))) everyone.add(question);
    }));

    return everyone.size;
}).reduce((a, b) => a + b) + ' questions in total');
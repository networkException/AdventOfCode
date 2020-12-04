import * as files from 'fs';

const input: string = files.readFileSync('input/day4.txt').toString();

const passports: Array<Map<string, string>> = input.split('\n\n').map(passport => {
    const attributes: Map<string, string> = new Map<string, string>();

    passport.replaceAll('\n', ' ').split(' ').map(attribute => attributes.set(attribute.split(':')[0], attribute.split(':')[1]));

    return attributes;
});

console.log('Day 4A: ' + passports.filter(passport =>
    passport.has('byr') &&
    passport.has('iyr') &&
    passport.has('eyr') &&
    passport.has('hgt') &&
    passport.has('hcl') &&
    passport.has('ecl') &&
    passport.has('pid')).length + ' passports are valid');

const validheight = (height: string): boolean => {
    if (height.endsWith('cm')) {
        return Number(height.substring(0, height.length - 2)) >= 150 && Number(height.substring(0, height.length - 2)) <= 193;
    }

    if (height.endsWith('in')) {
        return Number(height.substring(0, height.length - 2)) >= 59 && Number(height.substring(0, height.length - 2)) <= 76;
    }

    return false;
};

console.log('Day 4B: ' + passports.filter(passport =>
    passport.has('byr') && passport.get('byr').length === 4 && Number(passport.get('byr')) >= 1920 && Number(passport.get('byr')) <= 2002 &&
    passport.has('iyr') && passport.get('iyr').length === 4 && Number(passport.get('iyr')) >= 2010 && Number(passport.get('iyr')) <= 2020 &&
    passport.has('eyr') && passport.get('eyr').length === 4 && Number(passport.get('eyr')) >= 2020 && Number(passport.get('eyr')) <= 2030 &&
    passport.has('hgt') && validheight(passport.get('hgt')) &&
    passport.has('hcl') && passport.get('hcl').match(/^#[a-f0-9]{6}$/) !== null &&
    passport.has('ecl') && passport.get('ecl').match(/^(?:amb)|(?:blu)|(?:brn)|(?:gry)|(?:grn)|(?:hzl)|(?:oth)$/) !== null &&
    passport.has('pid') && passport.get('pid').match(/^[0-9]{9}$/) !== null).length + ' passports are valid');
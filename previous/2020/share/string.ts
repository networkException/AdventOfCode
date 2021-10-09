export function replaceAt(input: string, index: number, replacement: string): string {
    return input.substring(0, index) + replacement + input.substring(index + replacement.length);
}
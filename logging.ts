export const logResult = (parts: TemplateStringsArray, result: number | bigint): void => console.log(`${parts[0]}\x1b[1m${result}\x1b[0m${parts[1]}`);

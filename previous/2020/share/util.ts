const readkey = async () => {
    process.stdin.setRawMode(true);

    return new Promise<void>(resolve => process.stdin.once('data', data => {
        const byteArray: Array<number> = [...data];
        if (byteArray.length > 0 && byteArray[0] === 3) process.exit(0);

        process.stdin.setRawMode(false);
        resolve();
    }));
};
package de.nwex.adventofcode.year2019.day09.parser.instruction;

import de.nwex.adventofcode.year2019.day09.parser.Memory;
import java.util.List;

public abstract class Instruction
{
    protected de.nwex.adventofcode.year2019.day09.parser.Memory memory;

    public abstract Integer parameterCount();

    public abstract void run(List<Parameter> parameters);

    void setMemory(Memory memory)
    {
        this.memory = memory;
    }
}

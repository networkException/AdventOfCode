package de.jakobniklas.adventofcode.year2019.day09.parser.instruction;

import de.jakobniklas.adventofcode.year2019.day09.parser.Memory;

import java.util.List;

public abstract class Instruction
{
    protected Memory memory;

    public abstract Integer parameterCount();

    public abstract void run(List<Parameter> parameters);

    void setMemory(Memory memory)
    {
        this.memory = memory;
    }
}

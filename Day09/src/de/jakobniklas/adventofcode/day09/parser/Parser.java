package de.jakobniklas.adventofcode.day09.parser;

import de.jakobniklas.adventofcode.day09.parser.instruction.InstructionRegistry;
import de.jakobniklas.adventofcode.day09.parser.instruction.impl.*;

public class Parser
{
    private Memory memory;
    private InstructionRegistry instructionRegistry;
    private Boolean ended;
    private static Boolean debug;

    public Parser()
    {
        this(false);
    }

    public Parser(Boolean debug)
    {
        Parser.debug = debug;
        this.ended = false;
        this.memory = new Memory();
        this.instructionRegistry = new InstructionRegistry(memory);

        instructionRegistry.registerInstruction(1, new AddInstruction());
        instructionRegistry.registerInstruction(2, new MultiplyInstruction());
        instructionRegistry.registerInstruction(3, new ScannerInputInstruction());
        instructionRegistry.registerInstruction(4, new ConsoleOutputInstruction());
        instructionRegistry.registerInstruction(5, new JumpIfTrueInstruction());
        instructionRegistry.registerInstruction(6, new JumpIfFalseInstruction());
        instructionRegistry.registerInstruction(7, new LessThanInstruction());
        instructionRegistry.registerInstruction(8, new EqualsInstruction());
        instructionRegistry.registerInstruction(9, new RelativeAdjustmentInstruction());
        instructionRegistry.registerInstruction(99, new EndInstruction(() -> ended = true));
    }

    public void parse()
    {
        memory.loadInitial("res/input.txt");

        while(!ended)
        {
            ended = instructionRegistry.parseInstruction(String.valueOf(memory.getAtPointer()));
        }
    }

    public static Boolean isDebug()
    {
        return debug;
    }
}

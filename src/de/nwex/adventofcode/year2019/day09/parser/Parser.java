package de.nwex.adventofcode.year2019.day09.parser;

import de.nwex.adventofcode.year2019.day09.parser.instruction.InstructionRegistry;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.AddInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.EndInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.EqualsInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.ImplementationInputInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.ImplementationOutputInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.JumpIfFalseInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.JumpIfTrueInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.LessThanInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.MultiplyInstruction;
import de.nwex.adventofcode.year2019.day09.parser.instruction.impl.RelativeAdjustmentInstruction;

public class Parser
{
    public static Boolean debug;
    private de.nwex.adventofcode.year2019.day09.parser.Memory memory;
    private InstructionRegistry instructionRegistry;
    private Boolean ended;
    private Long output;

    public Parser(Boolean debug, Long mode)
    {
        Parser.debug = debug;
        this.ended = false;
        this.memory = new Memory();
        this.instructionRegistry = new InstructionRegistry(memory);

        instructionRegistry.registerInstruction(1, new AddInstruction());
        instructionRegistry.registerInstruction(2, new MultiplyInstruction());
        instructionRegistry.registerInstruction(3, new ImplementationInputInstruction(() -> mode));
        instructionRegistry.registerInstruction(4, new ImplementationOutputInstruction((output) -> this.output = output));
        instructionRegistry.registerInstruction(5, new JumpIfTrueInstruction());
        instructionRegistry.registerInstruction(6, new JumpIfFalseInstruction());
        instructionRegistry.registerInstruction(7, new LessThanInstruction());
        instructionRegistry.registerInstruction(8, new EqualsInstruction());
        instructionRegistry.registerInstruction(9, new RelativeAdjustmentInstruction());
        instructionRegistry.registerInstruction(99, new EndInstruction(() -> ended = true));
    }

    public static Boolean isDebug()
    {
        return debug;
    }

    public Long parse(String path)
    {
        memory.loadInitial(path);

        while(!ended)
        {
            ended = instructionRegistry.parseInstruction(String.valueOf(memory.getAtPointer()));
        }

        return output;
    }
}

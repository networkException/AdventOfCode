package de.jakobniklas.adventofcode.day11;

import de.jakobniklas.adventofcode.day09.parser.Memory;
import de.jakobniklas.adventofcode.day09.parser.instruction.InstructionRegistry;
import de.jakobniklas.adventofcode.day09.parser.instruction.impl.*;
import de.jakobniklas.adventofcode.day11.instruction.ImplementationInputInstruction;
import de.jakobniklas.adventofcode.day11.instruction.ImplementationOutputInstruction;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.HashMap;
import java.util.Map;

public class Parser
{
    private Memory memory;
    private InstructionRegistry instructionRegistry;
    private Boolean ended;
    private static Boolean debug;

    //isWhite
    private Map<Position, Boolean> panels;
    private Position robot;
    private Direction facing;
    private Integer unique;

    private Boolean outputMode;

    public Parser()
    {
        this(false);
    }

    public Parser(Boolean debug)
    {
        de.jakobniklas.adventofcode.day09.parser.Parser.debug = debug;
        this.ended = false;
        this.memory = new Memory();
        this.instructionRegistry = new InstructionRegistry(memory);

        panels = new HashMap<>();
        robot = new Position(0, 0);
        facing = Direction.UP;
        unique = 0;

        outputMode = true;

        instructionRegistry.registerInstruction(1, new AddInstruction());
        instructionRegistry.registerInstruction(2, new MultiplyInstruction());
        instructionRegistry.registerInstruction(3, new ImplementationInputInstruction(this::getPanel));
        instructionRegistry.registerInstruction(4, new ImplementationOutputInstruction(this::processOutput));
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

        Log.print(panels.toString());
        Log.print(String.valueOf(unique));
    }

    private void processOutput(Long output)
    {
        //Color
        if(outputMode)
        {
            if(panels.containsKey(robot))
            {
                panels.replace(new Position(robot.getX(), robot.getY()), output == 1);
            }
            else
            {
                panels.put(new Position(robot.getX(), robot.getY()), output == 1);
                unique++;
            }
        }
        //Direction
        else
        {
            if(output == 0)
            {
                Direction temp;

                switch(facing)
                {
                    case UP: temp = Direction.LEFT; break;
                    case DOWN: temp = Direction.RIGHT; break;
                    case LEFT: temp = Direction.DOWN; break;
                    case RIGHT: temp = Direction.UP; break;
                    default: throw new IllegalStateException("Unexpected value: " + facing);
                }

                facing = temp;
            }
            else
            {
                Direction temp;

                switch(facing)
                {
                    case UP: temp = Direction.RIGHT; break;
                    case DOWN: temp = Direction.LEFT; break;
                    case LEFT: temp = Direction.UP; break;
                    case RIGHT: temp = Direction.DOWN; break;
                    default: throw new IllegalStateException("Unexpected value: " + facing);
                }

                facing = temp;
            }

            switch(facing)
            {
                case UP: robot.addY(1); break;
                case DOWN: robot.addY(-1); break;
                case LEFT: robot.addX(-1); break;
                case RIGHT: robot.addX(1); break;
            }
        }

        outputMode = !outputMode;
    }

    private Long getPanel()
    {
        if(panels.containsKey(robot))
        {
            return panels.get(robot) ? 1L : 0L;
        }
        else
        {
            return 0L;
        }
    }

    public static Boolean isDebug()
    {
        return debug;
    }
}

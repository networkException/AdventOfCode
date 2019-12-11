package de.jakobniklas.adventofcode.day11;

import de.jakobniklas.adventofcode.day09.parser.Memory;
import de.jakobniklas.adventofcode.day09.parser.instruction.InstructionRegistry;
import de.jakobniklas.adventofcode.day09.parser.instruction.impl.*;
import de.jakobniklas.adventofcode.day11.instruction.ImplementationInputInstruction;
import de.jakobniklas.adventofcode.day11.instruction.ImplementationOutputInstruction;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Parser(Boolean debug, Boolean part2)
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

        panels.put(new Position(0, 0), part2);

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

        Log.print(String.valueOf(unique));
        print();
    }

    private void print()
    {
        List<Position> xList = panels.keySet().stream().sorted(Comparator.comparingInt(Position::getX)).collect(Collectors.toList());
        List<Position> yList = panels.keySet().stream().sorted(Comparator.comparingInt(Position::getY)).collect(Collectors.toList());

        int fromX = xList.get(0).getX();
        int toX = xList.get(xList.size() - 1).getX();
        int fromY = yList.get(0).getY();
        int toY = yList.get(yList.size() - 1).getY();

        IntStream.range(fromY, toY).forEach((y) ->
        {
            IntStream.range(fromX, toX).forEach((x) -> panels.forEach((position, white) ->
            {
                if(x == position.getX() && y == position.getY())
                {
                    System.out.print(white ? "X " : "  ");
                }
            }));

            System.out.println();
        });
    }

    private void processOutput(Long output)
    {
        //Color
        if(outputMode)
        {
            if(panels.containsKey(new Position(robot.getX(), robot.getY())))
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

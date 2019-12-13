package de.jakobniklas.adventofcode.year2019.day13.parser;

import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.InstructionRegistry;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.ParameterMode;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.impl.*;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.Position;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.Tile;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.TileType;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser
{
    private Memory memory;
    private InstructionRegistry instructionRegistry;
    private Boolean ended;
    public static Boolean debug;

    private List<Long> outputs;
    private List<Tile> tiles;

    public Parser(Boolean debug)
    {
        Parser.debug = debug;
        this.ended = false;
        this.memory = new Memory();
        this.instructionRegistry = new InstructionRegistry(memory);

        outputs = new ArrayList<>();
        tiles = new ArrayList<>();

        instructionRegistry.registerInstruction(1, new AddInstruction());
        instructionRegistry.registerInstruction(2, new MultiplyInstruction());
        instructionRegistry.registerInstruction(3, new ScannerInputInstruction());
        instructionRegistry.registerInstruction(4, new ImplementationOutputInstruction((output) -> outputs.add(output)));
        instructionRegistry.registerInstruction(5, new JumpIfTrueInstruction());
        instructionRegistry.registerInstruction(6, new JumpIfFalseInstruction());
        instructionRegistry.registerInstruction(7, new LessThanInstruction());
        instructionRegistry.registerInstruction(8, new EqualsInstruction());
        instructionRegistry.registerInstruction(9, new RelativeAdjustmentInstruction());
        instructionRegistry.registerInstruction(99, new EndInstruction(() -> ended = true));
    }

    public Long part1(String path)
    {
        memory.loadInitial(path);

        while(!ended)
        {
            ended = instructionRegistry.parseInstruction(String.valueOf(memory.getAtPointer()));
        }

        int head = 0;
        while(head < outputs.size())
        {
            tiles.add(new Tile(outputs.subList(head, head + 3)));

            head += 3;
        }

        return tiles.stream().filter((tile) -> tile.getType().equals(TileType.BLOCK)).count();
    }

    public Long part2(String path)
    {
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger head = new AtomicInteger();

        instructionRegistry.registerInstruction(4, new ImplementationOutputInstruction((output) ->
        {
            outputs.add(output);

            if(counter.get() % 3 == 0 && counter.get() != 0)
            {
                if(outputs.get(head.get()) == -1 && outputs.get(head.get() + 2) == 0)
                {
                    Log.print("Score: " + outputs.get(head.get() + 3));
                }
                else
                {
                    tiles.add(new Tile(outputs.subList(head.get(), head.get() + 3)));
                }

                head.getAndIncrement();
                head.getAndIncrement();
                head.getAndIncrement();
            }

            counter.getAndIncrement();
        }));

        instructionRegistry.registerInstruction(3, new ImplementationAndScannerInputInstruction(() ->
        {
            print();
            return 0L;
        }));

        memory.loadInitial(path);
        memory.set(new Parameter(ParameterMode.POSITION, 0L), 2L);

        while(!ended)
        {
            ended = instructionRegistry.parseInstruction(String.valueOf(memory.getAtPointer()));
        }

        return tiles.stream().filter((tile) -> tile.getType().equals(TileType.BLOCK)).count();
    }

    private void print()
    {
        List<Position> xList = tiles.stream().map(Tile::getPosition).sorted(Comparator.comparingInt(Position::getX)).collect(Collectors.toList());
        List<Position> yList = tiles.stream().map(Tile::getPosition).sorted(Comparator.comparingInt(Position::getY)).collect(Collectors.toList());

        int fromX = xList.get(0).getX();
        int toX = xList.get(xList.size() - 1).getX() + 1;
        int fromY = yList.get(0).getY();
        int toY = yList.get(yList.size() - 1).getY() + 1;

        IntStream.range(fromY, toY).forEach((y) ->
        {
            IntStream.range(fromX, toX).forEach((x) ->
            {
                AtomicReference<String> out = new AtomicReference<>(" ");

                tiles.forEach((tile) ->
                {
                    if(x == tile.getPosition().getX() && y == tile.getPosition().getY())
                    {
                        switch(tile.getType())
                        {
                            case BLOCK: out.set("B"); break;
                            case BALL: out.set("O"); break;
                            case WALL: out.set("W"); break;
                            case EMPTY: out.set(" "); break;
                            case HPADDLE: out.set("P"); break;
                        }
                    }
                });

                System.out.print(out.get());
            });

            System.out.println();
        });
    }

    public static Boolean isDebug()
    {
        return debug;
    }
}

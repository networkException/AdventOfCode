package de.jakobniklas.adventofcode.year2019.day13.parser;

import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.InstructionRegistry;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.ParameterMode;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.impl.*;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.Position;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.Tile;
import de.jakobniklas.adventofcode.year2019.day13.parser.tile.TileType;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.*;
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
    private Map<Position, Tile> tiles;

    private Integer score;

    public Parser(Boolean debug)
    {
        Parser.debug = debug;
        this.ended = false;
        this.memory = new Memory();
        this.instructionRegistry = new InstructionRegistry(memory);

        outputs = new ArrayList<>();
        tiles = new HashMap<>();
        score = 0;

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

        parseTiles();

        return tiles.values().stream().filter((tile) -> tile.getType().equals(TileType.BLOCK)).count();
    }

    private void parseTiles()
    {
        int head = 0;
        while(head < outputs.size())
        {
            Tile tile = new Tile(outputs.subList(head, head + 3));

            if(tile.getType() == null)
            {
                score = Math.toIntExact(outputs.subList(head, head + 3).get(2));

                head += 3;
                continue;
            }

            if(tiles.containsKey(tile.getPosition()))
            {
                tiles.replace(tile.getPosition(), tile);
            }
            else
            {
                tiles.put(tile.getPosition(), tile);
            }

            head += 3;
        }
    }

    public Integer part2(String path)
    {
        memory.loadInitial(path);
        memory.set(new Parameter(ParameterMode.POSITION, 0L), 2L);

        instructionRegistry.registerInstruction(3, new ImplementationInputInstruction(() ->
        {
            parseTiles();
            //print(tiles);

            Integer ballX = tiles.values().stream().filter((tile) -> tile.getType().equals(TileType.BALL)).collect(Collectors.toList()).get(0).getPosition().getX();
            Integer paddleX = tiles.values().stream().filter((tile) -> tile.getType().equals(TileType.HPADDLE)).collect(Collectors.toList()).get(0).getPosition().getX();

            if(ballX.equals(paddleX))
            {
                return 0L;
            }
            else if(ballX > paddleX)
            {
                return 1L;
            }
            else
            {
                return -1L;
            }
        }));

        while(!ended)
        {
            ended = instructionRegistry.parseInstruction(String.valueOf(memory.getAtPointer()));
        }

        return Math.toIntExact(outputs.get(outputs.size() - 1));
    }

    private void print(Map<Position, Tile> tiles)
    {
        List<Position> xList = tiles.values().stream().map(Tile::getPosition).sorted(Comparator.comparingInt(Position::getX)).collect(Collectors.toList());
        List<Position> yList = tiles.values().stream().map(Tile::getPosition).sorted(Comparator.comparingInt(Position::getY)).collect(Collectors.toList());

        int fromX = xList.get(0).getX();
        int toX = xList.get(xList.size() - 1).getX() + 1;
        int fromY = yList.get(0).getY();
        int toY = yList.get(yList.size() - 1).getY() + 1;

        IntStream.range(fromY, toY).forEach((y) ->
        {
            IntStream.range(fromX, toX).forEach((x) ->
            {
                AtomicReference<String> out = new AtomicReference<>(" ");

                tiles.forEach((position, tile) ->
                {
                    if(x == tile.getPosition().getX() && y == tile.getPosition().getY())
                    {
                        if(tile.getType() == null)
                        {
                            Log.print(tile.toString());
                        }

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

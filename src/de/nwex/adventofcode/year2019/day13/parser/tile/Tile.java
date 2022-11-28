package de.nwex.adventofcode.year2019.day13.parser.tile;

import java.util.List;

public class Tile
{
    private TileType type;
    private Position position;

    public Tile(List<Long> args)
    {
        this.type = TileType.valueOf(Math.toIntExact(args.get(2)));
        this.position = new Position(Math.toIntExact(args.get(0)), Math.toIntExact(args.get(1)));
    }

    @Override
    public String toString()
    {
        return "Tile{" +
            "type=" + type +
            ", position=" + position +
            '}';
    }

    public Position getPosition()
    {
        return position;
    }

    public TileType getType()
    {
        return type;
    }
}

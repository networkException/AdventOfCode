package de.jakobniklas.adventofcode.year2019.day13.parser.tile;

import java.util.HashMap;
import java.util.Map;

public enum TileType
{
    EMPTY(0),
    WALL(1),
    BLOCK(2),
    HPADDLE(3),
    BALL(4);

    private int id;

    private static Map<Integer, TileType> map = new HashMap<>();

    TileType(int id)
    {
        this.id = id;
    }

    /*
     * Fills {@link #map} on class load
     */
    static
    {
        for(TileType tileType : TileType.values())
        {
            map.put(tileType.id, tileType);
        }
    }

    public static TileType valueOf(int id)
    {
        return map.get(id);
    }

    public int getId()
    {
        return id;
    }
}

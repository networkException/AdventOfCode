package de.nwex.adventofcode.year2019.day13.parser.tile;

import java.util.HashMap;
import java.util.Map;

public enum TileType
{
    EMPTY(0),
    WALL(1),
    BLOCK(2),
    HPADDLE(3),
    BALL(4);

    private static Map<Integer, TileType> map = new HashMap<>();

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

    private int id;

    TileType(int id)
    {
        this.id = id;
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

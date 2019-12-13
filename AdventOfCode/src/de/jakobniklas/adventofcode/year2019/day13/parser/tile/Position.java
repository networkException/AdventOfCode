package de.jakobniklas.adventofcode.year2019.day13.parser.tile;

public class Position
{
    private Integer x;
    private Integer y;

    public Position(Integer x, Integer y)
    {
        this.x = x;
        this.y = y;
    }

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }

    public void setY(Integer y)
    {
        this.y = y;
    }

    public void setX(Integer x)
    {
        this.x = x;
    }

    @Override
    public String toString()
    {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}

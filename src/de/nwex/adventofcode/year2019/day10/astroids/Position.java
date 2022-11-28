package de.nwex.adventofcode.year2019.day10.astroids;

import java.util.Objects;

public class Position
{
    private Integer x;
    private Integer y;

    public Position(Integer x, Integer y)
    {
        this.x = x;
        this.y = y;
    }

    public Boolean isDirectNeighbour(Position position)
    {
        return eitherOneOrZero(position.getX() - x) && eitherOneOrZero(position.getY() - y);
    }

    private Boolean eitherOneOrZero(Integer input)
    {
        if(Math.abs(input) == 1)
        {
            return true;
        }
        else { return Math.abs(input) == 0; }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) &&
            Objects.equals(y, position.y);
    }

    @Override
    public String toString()
    {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }
}

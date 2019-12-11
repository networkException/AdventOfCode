package de.jakobniklas.adventofcode.day11;

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

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }

    public void addX(int x)
    {
        this.x += x;
    }

    public void addY(int y)
    {
        this.y += y;
    }

    @Override
    public String toString()
    {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
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
    public int hashCode()
    {
        return Objects.hash(x, y);
    }
}

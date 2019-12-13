package de.jakobniklas.adventofcode.year2019.day12.system;

import java.util.Objects;

public class Position
{
    private Integer x;
    private Integer y;
    private Integer z;

    public Position()
    {

    }

    public Integer getPotentialEnergy()
    {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    public Integer getX()
    {
        return x;
    }

    public Integer getY()
    {
        return y;
    }

    public Integer getZ()
    {
        return z;
    }

    public void setX(Integer x)
    {
        this.x = x;
    }

    public void setY(Integer y)
    {
        this.y = y;
    }

    public void setZ(Integer z)
    {
        this.z = z;
    }

    public void addToX(Integer x)
    {
        this.x += x;
    }

    public void addToY(Integer y)
    {
        this.y += y;
    }

    public void addToZ(Integer z)
    {
        this.z += z;
    }

    @Override
    public String toString()
    {
        return "Gravity{" +
            "x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) &&
            Objects.equals(y, position.y) &&
            Objects.equals(z, position.z);
    }

    public String stringCode()
    {
        return String.format("%d%d%d", x, y, z);
    }
}

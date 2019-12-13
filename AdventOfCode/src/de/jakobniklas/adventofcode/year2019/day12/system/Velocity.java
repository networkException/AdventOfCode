package de.jakobniklas.adventofcode.year2019.day12.system;

import java.util.Objects;

public class Velocity
{
    private Integer x;
    private Integer y;
    private Integer z;

    public Velocity()
    {
        x = 0;
        y = 0;
        z = 0;
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

    public Integer getKineticEnergy()
    {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    @Override
    public String toString()
    {
        return "Velocity{" +
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
        Velocity velocity = (Velocity) o;
        return Objects.equals(x, velocity.x) &&
            Objects.equals(y, velocity.y) &&
            Objects.equals(z, velocity.z);
    }

    public String stringCode()
    {
        return String.format("%d%d%d", x, y, z);
    }

    public Boolean isZero()
    {
        return x == 0 && y == 0 && z == 0;
    }
}

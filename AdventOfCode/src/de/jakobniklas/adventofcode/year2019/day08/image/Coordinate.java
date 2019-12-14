package de.jakobniklas.adventofcode.year2019.day08.image;

import java.util.Objects;

public class Coordinate
{
    private Integer u;
    private Integer v;

    public Coordinate(Integer u, Integer v)
    {
        this.u = u;
        this.v = v;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(u, v);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(u, that.u) &&
            Objects.equals(v, that.v);
    }

    @Override
    public String toString()
    {
        return "Coordinate{" +
            "u=" + u +
            ", v=" + v +
            '}';
    }

    public Integer getU()
    {
        return u;
    }

    public Integer getV()
    {
        return v;
    }
}

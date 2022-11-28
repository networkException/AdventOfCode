package de.nwex.adventofcode.year2019.day10.astroids;

import java.util.Objects;

public class Target
{
    private Position position;
    private Double angle;
    private Boolean vaporised;

    public Target(Position position, Double angle)
    {
        this.position = position;
        this.angle = angle;
    }

    public void vaporize()
    {
        vaporised = true;
    }

    public Boolean isVaporised()
    {
        return vaporised;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(angle);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(angle, target.angle);
    }

    @Override
    public String toString()
    {
        return "Target{" +
            "position=" + position +
            ", angle=" + angle +
            '}';
    }

    public Double getAngle()
    {
        return angle;
    }

    public void setAngle(Double angle)
    {
        this.angle = angle;
    }

    public Position getPosition()
    {
        return position;
    }
}

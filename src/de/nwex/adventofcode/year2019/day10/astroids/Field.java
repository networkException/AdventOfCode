package de.nwex.adventofcode.year2019.day10.astroids;

public class Field
{
    private Boolean asteroid;

    public Field(String input)
    {
        asteroid = input.equals("#");
    }

    public Boolean isAsteroid()
    {
        return asteroid;
    }

    @Override
    public String toString()
    {
        return "Field{" +
            "asteroid=" + asteroid +
            '}';
    }
}

package de.jakobniklas.adventofcode.year2019.day08.image;

public class Pixel
{
    private Coordinate coordinate;
    private Boolean white;

    public Pixel(Coordinate coordinate, Boolean white)
    {
        this.coordinate = coordinate;
        this.white = white;
    }

    public Boolean isWhite()
    {
        return white;
    }

    @Override
    public String toString()
    {
        return "Pixel{" +
            "coordinate=" + coordinate +
            ", white=" + white +
            '}';
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }
}

package de.jakobniklas.adventofcode.day08.image;

public class Pixel
{
    private Coordinate coordinate;
    private Boolean white;

    public Pixel(Coordinate coordinate, Boolean white)
    {
        this.coordinate = coordinate;
        this.white = white;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
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
}

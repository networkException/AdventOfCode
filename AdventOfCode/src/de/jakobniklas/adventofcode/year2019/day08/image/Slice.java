package de.jakobniklas.adventofcode.year2019.day08.image;

import java.util.ArrayList;
import java.util.List;

public class Slice
{
    private Coordinate coordinate;
    private List<Integer> pixels;

    public Slice(Coordinate coordinate)
    {
        this.coordinate = coordinate;
        this.pixels = new ArrayList<>();
    }

    public void addPixel(Integer pixel)
    {
        pixels.add(pixel);
    }

    public Pixel processSlice()
    {
        for(Integer pixel : getPixels())
        {
            switch(pixel)
            {
                case 0: return new Pixel(getCoordinate(), false);
                case 1: return new Pixel(getCoordinate(), true);
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return "Slice{" +
            "coordinate=" + coordinate +
            ", pixels=" + pixels +
            '}';
    }

    public List<Integer> getPixels()
    {
        return pixels;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }
}

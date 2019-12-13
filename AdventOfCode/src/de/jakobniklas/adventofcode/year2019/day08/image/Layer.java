package de.jakobniklas.adventofcode.year2019.day08.image;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Layer
{
    private Map<Integer, AtomicInteger> pixelCounts;
    private Map<Coordinate, Integer> pixels;

    public Layer()
    {
        pixelCounts = new HashMap<>();
        pixels = new HashMap<>();
    }

    private void countPixel(Integer pixel)
    {
        if(!pixelCounts.containsKey(pixel))
        {
            pixelCounts.put(pixel, new AtomicInteger(1));
        }
        else
        {
            pixelCounts.get(pixel).getAndIncrement();
        }
    }

    public void addPixel(Coordinate coordinate, Integer pixel)
    {
        pixels.put(coordinate, pixel);

        countPixel(pixel);
    }

    public int pixelCount(Integer pixel)
    {
        return pixelCounts.get(pixel).get();
    }

    public Map<Coordinate, Integer> getPixels()
    {
        return pixels;
    }

    @Override
    public String toString()
    {
        return "Layer{" +
            "pixelCounts=" + pixelCounts +
            ", pixels=" + pixels +
            '}';
    }
}

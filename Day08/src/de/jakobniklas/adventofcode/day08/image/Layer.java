package de.jakobniklas.adventofcode.day08.image;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Layer
{
    private Map<Integer, AtomicInteger> pixels;

    public Layer()
    {
        pixels = new HashMap<>();
    }

    public void addPixel(Integer pixel)
    {
        if(!pixels.containsKey(pixel))
        {
            pixels.put(pixel, new AtomicInteger(1));
        }
        else
        {
            pixels.get(pixel).getAndIncrement();
        }
    }

    public int pixelCount(Integer pixel)
    {
        return pixels.get(pixel).get();
    }

    @Override
    public String toString()
    {
        return "Layer{" +
            "pixels=" + pixels +
            '}';
    }
}

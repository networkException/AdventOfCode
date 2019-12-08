package de.jakobniklas.adventofcode.day08.image;

import de.jakobniklas.applicationlib.commonutil.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser
{
    private List<Layer> layers;
    private List<Integer> pixels;
    private List<String> input;

    public Parser(String path)
    {
        AtomicInteger parsedPixelCount = new AtomicInteger(0);
        AtomicInteger parsedLayerCount = new AtomicInteger(0);

        layers = new ArrayList<>();
        pixels = new ArrayList<>();

        input = Arrays.asList(FileUtil.getTextContent(path).replace("\n", "").split(""));
        input.forEach((pixel) -> pixels.add(Integer.parseInt(pixel)));

        while(parsedPixelCount.get() < input.size())
        {
            layers.add(new Layer());

            IntStream.range(0, 6).forEach((i) -> IntStream.range(0, 25).forEach((j) ->
            {
                layers.get(parsedLayerCount.get()).addPixel(pixels.get(parsedPixelCount.get()));

                parsedPixelCount.getAndIncrement();
            }));

            parsedLayerCount.getAndIncrement();
        }
    }

    public int getPartOneResult()
    {
        Layer targetLayer = layers.stream()
            .sorted(Comparator.comparingInt(a -> a.pixelCount(0)))
            .collect(Collectors.toList())
            .get(0);

        return targetLayer.pixelCount(1) * targetLayer.pixelCount(2);
    }
}

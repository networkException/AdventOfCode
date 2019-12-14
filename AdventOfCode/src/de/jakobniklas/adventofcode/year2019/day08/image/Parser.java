package de.jakobniklas.adventofcode.year2019.day08.image;

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
    private List<Slice> slices;

    public Parser(String path, Integer x, Integer y)
    {
        AtomicInteger parsedPixelCount = new AtomicInteger(0);
        AtomicInteger parsedLayerCount = new AtomicInteger(0);

        layers = new ArrayList<>();
        pixels = new ArrayList<>();
        slices = new ArrayList<>();

        input = Arrays.asList(FileUtil.getTextContent(path).replace("\n", "").split(""));
        input.forEach((pixel) -> pixels.add(Integer.parseInt(pixel)));

        while(parsedPixelCount.get() < input.size())
        {
            layers.add(new Layer());

            IntStream.range(0, y).forEach((i) -> IntStream.range(0, x).forEach((j) ->
            {
                layers.get(parsedLayerCount.get()).addPixel(new Coordinate(j, i), pixels.get(parsedPixelCount.get()));

                parsedPixelCount.getAndIncrement();
            }));

            parsedLayerCount.getAndIncrement();
        }

        layers.get(0).getPixels().keySet().forEach(((coordinate) ->
        {
            Slice slice = new Slice(coordinate);
            layers.forEach((layer) -> slice.addPixel(layer.getPixels().get(coordinate)));
            slices.add(slice);
        }));
    }

    public void printPartTwo(int x, int y)
    {
        List<Pixel> pixels = new ArrayList<>();
        slices.forEach((slice) -> pixels.add(slice.processSlice()));

        AtomicInteger xPrint = new AtomicInteger(0);
        AtomicInteger yPrint = new AtomicInteger(0);

        IntStream.range(0, y).forEach((i) ->
        {
            IntStream.range(0, x).forEach((j) ->
            {
                pixels.forEach((pixel) ->
                {
                    if(pixel.getCoordinate().getU().equals(xPrint.get()) && pixel.getCoordinate().getV().equals(yPrint.get()))
                    {
                        System.out.print(pixel.isWhite() ? "X " : "  ");
                    }
                });

                xPrint.getAndIncrement();
            });

            System.out.println();

            xPrint.set(0);
            yPrint.getAndIncrement();
        });
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

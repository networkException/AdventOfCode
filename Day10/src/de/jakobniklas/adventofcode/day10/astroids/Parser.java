package de.jakobniklas.adventofcode.day10.astroids;

import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser
{
    private Map<Position, Field> fields;
    private Map<Position, Integer> parsed;
    private Integer rows;
    private Integer columns;

    public Parser(String path)
    {
        fields = new HashMap<>();
        parsed = new HashMap<>();

        AtomicInteger rowCount = new AtomicInteger(0);
        AtomicInteger columnCount = new AtomicInteger(0);

        Arrays.asList(FileUtil.getTextContent(path).split("\n")).forEach((row) ->
        {
            Arrays.asList(row.split("")).forEach((field) ->
            {
                fields.put(new Position(columnCount.get(), rowCount.get()), new Field(field));

                columnCount.getAndIncrement();
            });

            rowCount.getAndIncrement();
            columns = columnCount.get();
            columnCount.set(0);
        });

        rows = rowCount.get();

        fields.forEach((position, field) -> parsed.put(position, field.isAsteroid() ? 0 : -1));
    }

    public void parse()
    {
        AtomicReference<Position> asteroid = new AtomicReference<>(new Position(-1, -1));
        AtomicInteger highest = new AtomicInteger(0);

        fields.forEach((position, field) ->
        {
            if(field.isAsteroid())
            {
                int visible = getVisible(position);

                if(visible > highest.get())
                {
                    highest.set(visible);
                    asteroid.set(position);
                }
            }
        });

        print(asteroid.get());
        Log.print(String.valueOf(highest.get()), asteroid.toString());
    }

    public void print(Position highlight)
    {
        AtomicInteger xPrint = new AtomicInteger(0);
        AtomicInteger yPrint = new AtomicInteger(0);

        IntStream.range(0, rows).forEach((i) ->
        {
            IntStream.range(0, columns).forEach((j) ->
            {
                parsed.forEach((position, field) ->
                {
                    if(position.getX().equals(xPrint.get()) && position.getY().equals(yPrint.get()))
                    {
                        if(position.equals(highlight))
                        {
                            System.out.print(String.format("[%d]", field));
                        }
                        else
                        {
                            System.out.print(field == -1 ? " . " : String.format(" %d ", field));
                        }
                    }
                });

                xPrint.getAndIncrement();
            });

            System.out.println();

            xPrint.set(0);
            yPrint.getAndIncrement();
        });
    }

    private Integer getVisible(Position origin)
    {
        List<Position> asteroids = new ArrayList<>();

        fields.forEach((position, field) ->
        {
            if(field.isAsteroid())
            {
                asteroids.add(position);
            }
        });

        List<Double> angles = new ArrayList<>();
        AtomicInteger blocked = new AtomicInteger(0);

        //Get a list of sorted relative asteroids (manhattan distance), excluding the origin
        asteroids.stream().sorted(Comparator.comparingInt(a -> Math.abs(a.getX() - origin.getX()) + Math.abs(a.getY() - origin.getY()))).filter((asteroid) -> !asteroid.equals(origin)).collect(Collectors.toList()).forEach((asteroid) ->
        {
            Double angle = getAngle(origin, asteroid);

            if(!angles.contains(angle))
            {
                angles.add(angle);
            }
            else
            {
                if(!origin.isDirectNeighbour(asteroid))
                {
                    blocked.getAndIncrement();
                }
            }
        });

        parsed.replace(origin, asteroids.size() - blocked.get() - 1);

        return asteroids.size() - blocked.get() - 1;
    }

    private Double getAngle(Position origin, Position ray)
    {
        float angle = (float) Math.toDegrees(Math.atan2(ray.getY() - origin.getY(), ray.getX() - origin.getX()));

        if(angle < 0)
        {
            angle += 360;
        }

        return (double) angle;
    }
}

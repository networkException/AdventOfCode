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

    public void vaporize(Position origin, List<Target> targets)
    {
        int totalTargets = targets.size();
        List<Target> vaporizedTargets = new ArrayList<>();
        targets.forEach((target) -> target.setAngle(target.getAngle()));

        //Sorted by angle
        while(vaporizedTargets.size() < totalTargets)
        {
            targets.stream().sorted(Comparator.comparingDouble(Target::getAngle)).distinct().collect(Collectors.toList()).forEach((distinctAngle) ->
            {
                List<Target> angleTargets = targets.stream().filter((a) -> a.getAngle().equals(distinctAngle.getAngle())).collect(Collectors.toList());

                if(angleTargets.size() == 1)
                {
                    vaporizedTargets.add(angleTargets.get(0));
                    targets.remove(angleTargets.get(0));
                }
                else
                {
                    Target nearestTarget = angleTargets.stream().sorted(Comparator.comparingInt(a -> Math.abs(a.getPosition().getX() - origin.getX()) + Math.abs(a.getPosition().getY() - origin.getY()))).collect(Collectors.toList()).get(0);

                    vaporizedTargets.add(nearestTarget);
                    targets.remove(nearestTarget);
                }
            });
        }

        AtomicInteger count = new AtomicInteger(0);

        parsed.forEach((position, integer) -> parsed.replace(position, -1));
        vaporizedTargets.forEach((target) -> parsed.replace(target.getPosition(), count.getAndIncrement()));

        print(origin);

        Log.print(String.format("The 1st asteroid to be vaporized is at %d,%d", vaporizedTargets.get(0).getPosition().getX(), vaporizedTargets.get(0).getPosition().getY()));
        Log.print(String.format("The 200th asteroid to be vaporized is at %d,%d ((%d * 100) + %d = %d)", vaporizedTargets.get(199).getPosition().getX(), vaporizedTargets.get(199).getPosition().getY(), vaporizedTargets.get(199).getPosition().getX(), vaporizedTargets.get(199).getPosition().getY(), (vaporizedTargets.get(199).getPosition().getX() * 100) + vaporizedTargets.get(199).getPosition().getY()));
    }

    public void parse()
    {
        List<Target> targets = new ArrayList<>();

        AtomicReference<Position> asteroid = new AtomicReference<>(new Position(-1, -1));
        AtomicInteger highest = new AtomicInteger(0);

        fields.forEach((position, field) ->
        {
            List<Target> localTargets = new ArrayList<>();

            if(field.isAsteroid())
            {
                int visible = getVisible(position, localTargets);

                if(visible > highest.get())
                {
                    highest.set(visible);
                    asteroid.set(position);
                    targets.clear();
                    targets.addAll(localTargets);
                }
            }
        });

        print(asteroid.get());
        Log.print(String.valueOf(highest.get()), asteroid.toString());

        vaporize(asteroid.get(), targets);
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
                        String fieldStr = padLeftZeros(String.valueOf(field));

                        if(position.equals(highlight))
                        {
                            System.out.print(String.format("%s]", fieldStr));
                        }
                        else
                        {
                            System.out.print(field == -1 ? "   . " : String.format("%s ", fieldStr));
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

    private Integer getVisible(Position origin, List<Target> targets)
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

        //Get a list of sorted asteroids (manhattan distance), relative to the origin, excluding the origin
        asteroids.stream().sorted(Comparator.comparingInt(a -> Math.abs(a.getX() - origin.getX()) + Math.abs(a.getY() - origin.getY()))).filter((asteroid) -> !asteroid.equals(origin)).collect(Collectors.toList()).forEach((asteroid) ->
        {
            Double angle = getAngle(origin, asteroid);
            targets.add(new Target(asteroid, angle));

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
        double angle = Math.toDegrees(Math.atan2(ray.getY() - origin.getY(), ray.getX() - origin.getX()));

        if(angle < -90)
        {
            angle += 360;
        }

        return angle;
    }

    private String padLeftZeros(String inputString)
    {
        if(inputString.length() >= 4)
        {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while(sb.length() < 4 - inputString.length())
        {
            sb.append(' ');
        }
        sb.append(inputString);

        return sb.toString();
    }
}

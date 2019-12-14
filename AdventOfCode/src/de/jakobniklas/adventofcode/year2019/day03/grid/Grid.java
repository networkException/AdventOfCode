package de.jakobniklas.adventofcode.year2019.day03.grid;

import de.jakobniklas.applicationlib.commonutil.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to store parsed points of wires as well as calculate intersections
 *
 * @author networkException
 * @see #points
 * @see #intersections
 * @see #path
 * @see #Grid(String)
 * @see #nearestCrossSection(boolean)
 * @see #parseWires()
 */
public class Grid
{
    /**
     * A map of points, storing the points' position and wires storing the steps taken as well as which wire it is
     * parsed from the given input wires
     */
    private Map<Point, Wire> points;

    /**
     * A map of found intersections and the steps taken to the point by every wire
     */
    private Map<Point, Wire> intersections;

    /**
     * A path to the wire input file
     */
    private String path;

    /**
     * Creates a new grid and and calls {@link #parseWires()}
     *
     * @param path The path to the file containing the instructions for wires
     */
    public Grid(String path)
    {
        this.path = path;
        points = new HashMap<>();
        intersections = new HashMap<>();

        parseWires();
    }

    /**
     * Returns the nearest intersection after parsing.
     *
     * @param air If the distance to the intersection should be by air or by steps (actual point can differ)
     *
     * @return The distance / steps to the intersection
     */
    public int nearestCrossSection(boolean air)
    {
        List<Integer> distances = new ArrayList<>();

        intersections.forEach((intersection, wire) ->
        {
            if(air)
            {
                int distance = Math.abs(intersection.getX()) + Math.abs(intersection.getY());

                if(!distances.contains(distance))
                {
                    distances.add(distance);
                }
            }
            else
            {
                distances.add(wire.getSteps());
            }
        });

        return Collections.min(distances);
    }

    /**
     * Parses the wires from the input file
     */
    private void parseWires()
    {
        //The wire is unique (ignore intersections with the same wire)
        AtomicInteger wireId = new AtomicInteger();

        //For each wire in the input
        Arrays.asList(FileUtil.getTextContent(path).split("\n")).forEach((wire) ->
        {
            //Keeps track of the position of the wire
            AtomicInteger xr = new AtomicInteger();
            AtomicInteger yr = new AtomicInteger();
            AtomicInteger steps = new AtomicInteger();

            //For each instruction in the input
            Arrays.asList(wire.split(",")).forEach(instruction ->
            {
                int delta = Integer.parseInt(instruction.substring(1));

                for(int i = 0; i < delta; i++)
                {
                    switch(instruction.substring(0, 1))
                    {
                        case "L":
                            xr.set(xr.get() - 1);
                            break;
                        case "R":
                            xr.set(xr.get() + 1);
                            break;
                        case "D":
                            yr.set(yr.get() - 1);
                            break;
                        case "U":
                            yr.set(yr.get() + 1);
                            break;
                    }

                    steps.getAndIncrement();

                    Point point = new Point(xr.get(), yr.get());

                    //No other wire was at that point before
                    if(!points.containsKey(point))
                    {
                        points.put(point, new Wire(wireId.get(), steps.get()));
                    }
                    else
                    {
                        //Another wire was at the point before
                        if(points.get(point).getWireId() != wireId.get())
                        {
                            //Add to intersections
                            if(!intersections.containsKey(point))
                            {
                                //Set steps
                                intersections.put(point, new Wire(points.get(point).getWireId(), points.get(point).getSteps()));
                                intersections.get(point).addSteps(steps.get());
                                points.get(point).setSteps(0);
                            }
                        }
                    }
                }
            });

            wireId.getAndIncrement();
        });
    }
}

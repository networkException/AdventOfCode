package de.jakobniklas.adventofcode.day06;

import de.jakobniklas.adventofcode.day06.orbit.SpaceObject;
import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrbitMapper
{
    private List<SpaceObject> spaceObjects;

    public OrbitMapper(String path)
    {
        spaceObjects = new ArrayList<>();

        //Parses input
        Arrays.asList(FileUtil.getTextContent(path).split("\n")).forEach((orbit) -> spaceObjects.add(new SpaceObject(orbit.split("\\)")[0], orbit.split("\\)")[1])));
    }

    public int calculate(boolean debug, boolean outputSanToYou)
    {
        AtomicInteger count = new AtomicInteger();

        //For each found object
        spaceObjects.forEach((spaceObject) ->
        {
            //Trace every nodes track to the origin
            SpaceObject parent = getParent(spaceObject);

            while(!parent.getSpaceObject().equals("COM"))
            {
                spaceObject.getTrace().add(parent);
                parent = getParent(parent);
                count.getAndIncrement();
            }

            count.getAndIncrement();
        });

        if(outputSanToYou)
        {
            SpaceObject you = get("YOU");
            SpaceObject santa = get("SAN");
            SpaceObject intersection = findTraceIntersection(you, santa);

            int transfersFromYou = transfersToObject(intersection, you);
            int transfersFromSanta = transfersToObject(intersection, santa);

            count.set(transfersFromYou + transfersFromSanta);
        }

        return count.get();
    }

    public int transfersToObject(SpaceObject to, SpaceObject from)
    {
        AtomicInteger count = new AtomicInteger(0);

        from.getTrace().forEach((traceElement) ->
        {
            if(traceElement.getSpaceObject().equals(to.getSpaceObject()))
            {
                count.getAndIncrement();

                Log.print(from.getSpaceObject(), to.getSpaceObject());
            }
        });

        return count.get();
    }

    public SpaceObject findTraceIntersection(SpaceObject first, SpaceObject second)
    {
        AtomicInteger highest = new AtomicInteger(0);

        first.getTrace().forEach((traceElement) ->
        {
            int indexOf = second.getTrace().indexOf(traceElement);

            if(indexOf != -1)
            {
                if(indexOf < highest.get())
                {
                    highest.set(indexOf);
                }
            }
        });

        return second.getTrace().get(highest.get() + 1);
    }

    public SpaceObject get(String spaceObject)
    {
        return spaceObjects
            .stream()
            .filter(filter -> filter.getSpaceObject().equals(spaceObject))
            .findFirst()
            .orElse(new SpaceObject("", "COM"));
    }

    public SpaceObject getParent(SpaceObject spaceObject)
    {
        return spaceObjects
            .stream()
            .filter(filter -> filter.getSpaceObject().equals(spaceObject.getParent()))
            .findFirst()
            .orElse(new SpaceObject("", "COM"));
    }
}

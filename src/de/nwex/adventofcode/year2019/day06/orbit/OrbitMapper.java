package de.nwex.adventofcode.year2019.day06.orbit;

import de.nwex.applicationlib.commonutil.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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
                AtomicBoolean filledTrace = new AtomicBoolean(false);

                //Prevent double trace fill
                spaceObject.getTrace().forEach((traceElement) ->
                {
                    if(traceElement.getParent().equals("COM"))
                    {
                        filledTrace.set(true);
                    }
                });

                if(!filledTrace.get())
                {
                    spaceObject.getTrace().add(parent);
                }

                //Recursion
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

            count.set(transfersFromYou + transfersFromSanta - 2);
        }

        return count.get();
    }

    public int transfersToObject(SpaceObject to, SpaceObject from)
    {
        return from.getTrace().indexOf(to);
    }

    public SpaceObject findTraceIntersection(SpaceObject first, SpaceObject second)
    {
        AtomicInteger highest = new AtomicInteger(0);

        for(SpaceObject traceElement : first.getTrace())
        {
            int indexOf = second.getTrace().indexOf(traceElement);

            if(indexOf != -1)
            {
                highest.set(indexOf);
                break;
            }
        }

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

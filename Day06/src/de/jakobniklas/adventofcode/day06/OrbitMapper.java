package de.jakobniklas.adventofcode.day06;

import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrbitMapper
{
    private List<String> orbits;
    private Map<String, List<String>> orbitRelations;

    public OrbitMapper(String path)
    {
        orbits = new ArrayList<>();
        orbitRelations = new HashMap<>();
        orbits.addAll(Arrays.asList(FileUtil.getTextContent(path).split("\n")));
    }

    public int calculate(boolean debug)
    {
        AtomicInteger count = new AtomicInteger();

        orbits.forEach((orbit) ->
        {
            if(!orbitRelations.containsKey(orbit.split("\\)")[0]))
            {
                orbitRelations.put(orbit.split("\\)")[0], new ArrayList<>());
                orbitRelations.get(orbit.split("\\)")[0]).add(orbit.split("\\)")[1]);
            }
            else
            {
                orbitRelations.get(orbit.split("\\)")[0]).add(orbit.split("\\)")[1]);
            }
        });

        Log.print(orbitRelations.toString());
        Log.print(orbits.toString());

        orbitRelations.forEach((origin, objects) ->
        {
            objects.forEach((object) ->
            {
                while(true)
                {
                    //The object has no origin (COM)
                    if(object.contains("COM"))
                    {
                        if(debug)
                        {
                            Log.print(String.format("'%s' has no origin", object));
                        }

                        break;
                    }
                    else
                    {
                        //Get the origin of the object
                        if(debug)
                        {
                            Log.print(String.format("'%s'", object));
                        }

                        object = new ArrayList<>(getKeys(orbitRelations, object)).get(0);
                        count.getAndIncrement();
                    }
                }
            });
        });

        return count.get();
    }

    public Set<String> getKeys(Map<String, List<String>> map, String value)
    {
        Set<String> keys = new HashSet<>();

        for(Map.Entry<String, List<String>> entry : map.entrySet())
        {
            if(entry.getValue().contains(value))
            {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}

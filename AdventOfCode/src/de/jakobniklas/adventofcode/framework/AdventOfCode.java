package de.jakobniklas.adventofcode.framework;

import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AdventOfCode
{
    private Map<Integer, Day> days;
    private Boolean debug;

    public AdventOfCode(List<String> args, String year)
    {
        days = new HashMap<>();
        debug = args.contains("--debug");

        registerDays(days);

        List<Integer> aDaysToExecute = args.stream().filter((arg) -> arg.startsWith("--ADay")).map((arg) -> Integer.parseInt(arg.split("--ADay")[1])).collect(Collectors.toList());
        List<Integer> bDaysToExecute = args.stream().filter((arg) -> arg.startsWith("--BDay")).map((arg) -> Integer.parseInt(arg.split("--BDay")[1])).collect(Collectors.toList());

        Log.measureTime("Running day(s)", "aocProcess");

        if(args.size() == 0)
        {
            days.keySet().forEach((day) ->
            {
                Log.print(String.format("%s/Day%d/A", year, day), days.get(day).partA(debug));
                Log.print(String.format("%s/Day%d/B", year, day), days.get(day).partB(debug));
            });
        }

        aDaysToExecute.forEach((day) -> Log.print(String.format("%s/Day%d/A", year, day), days.get(day).partA(debug)));
        bDaysToExecute.forEach((day) -> Log.print(String.format("%s/Day%d/B", year, day), days.get(day).partB(debug)));

        Log.done("aocProcess");
    }

    public abstract void registerDays(Map<Integer, Day> days);
}

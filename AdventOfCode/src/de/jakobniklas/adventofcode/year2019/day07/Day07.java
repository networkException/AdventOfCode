package de.jakobniklas.adventofcode.year2019.day07;

import de.jakobniklas.adventofcode.framework.Day;

public class Day07 extends Day
{
    public Day07()
    {
        aPath = "res/2019/day07.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The highest value the thrusters can output with 5 amps is '%d'", Amplifiers.highest(aPath, 0, 5));
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("The highest value the thrusters can output with looped amps is '%d'", Amplifiers.highestLooped(aPath, 5, 10, debug));
    }
}

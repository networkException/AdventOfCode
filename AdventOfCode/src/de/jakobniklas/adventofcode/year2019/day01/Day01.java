package de.jakobniklas.adventofcode.year2019.day01;

import de.jakobniklas.adventofcode.framework.Day;

public class Day01 extends Day
{
    public Day01()
    {
        aPath = "res/2019/day01.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The amount of fuel required is '%d'", FuelCalculator.calculate(aPath, false));
    }

    @Override
    public String partB(Boolean debug)
    {
        return String.format("The amount of fuel required is '%d'", FuelCalculator.calculate(aPath, true));
    }
}

package de.jakobniklas.adventofcode.day01;

import de.jakobniklas.applicationlib.commonutil.FileUtil;

import java.util.Arrays;

/**
 * Class to calculate the fuel of a given list of mass values
 *
 * @author networkException
 */
public class FuelCalculator
{
    /**
     * Calculates the amount of fuel required for a list of mass values.
     *
     * @param path A path to a text file containing the list of mass values. Values should be line seperated
     *
     * @return The total amount of fuel required
     */
    public static int calculate(String path)
    {
        return Arrays.stream(FileUtil.getTextContent(path).split("\n")).mapToInt(line -> getFuel(Integer.parseInt(line))).sum();
    }

    /**
     * Calculates the amount of fuel required for a given mass
     *
     * @param mass The given mass
     *
     * @return The calculated fuel amount
     */
    private static int getFuel(int mass)
    {
        return Math.round(mass / 3) - 2;
    }
}

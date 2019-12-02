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
     * @param path               A path to a text file containing the list of mass values. Values should be line
     *                           separated
     * @param calculateFuelsMass If the algorithm should respect the mass of the fuel itself
     *
     * @return The total amount of fuel required
     *
     * @see #getFuel(int)
     * @see #getFuelIncludingMass(int)
     */
    public static int calculate(String path, boolean calculateFuelsMass)
    {
        if(!calculateFuelsMass)
        {
            return Arrays.stream(FileUtil.getTextContent(path).split("\n")).mapToInt(line -> getFuel(Integer.parseInt(line))).sum();
        }
        else
        {
            return Arrays.stream(FileUtil.getTextContent(path).split("\n")).mapToInt(line -> getFuelIncludingMass(Integer.parseInt(line))).sum();
        }
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

    /**
     * Calculates the amount of fuel required for a given mass, while also calculating the amount of fuel required for a
     * given amount of fuel (as the fuel has mass itself)
     *
     * @param mass The given mass
     *
     * @return The calculated fuel amount
     */
    public static int getFuelIncludingMass(int mass)
    {
        int totalFuel = 0;

        while(mass > 0)
        {
            mass = getFuel(mass);

            //Ignores a negative mass
            totalFuel += Math.max(mass, 0);
        }

        return totalFuel;
    }
}

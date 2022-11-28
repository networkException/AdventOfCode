package de.nwex.applicationlib.math;

public class NumberUtil
{
    public static int randomNumber(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1) + min);
    }
}

package de.jakobniklas.adventofcode.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Password
{
    public static List<Integer> find(int rangeBase, int range, boolean allowBlocks)
    {
        List<Integer> matchingInRange = new ArrayList<>();

        IntStream.range(rangeBase, range).forEach((i) ->
        {
            if(matching(i, allowBlocks))
            {
                matchingInRange.add(i);
            }
        });

        return matchingInRange;
    }

    public static boolean stepPattern(int value)
    {
        String number = String.valueOf(value);
        int biggestDigit = 0;

        for(int i = 0; i < number.length(); i++)
        {
            int digit = Character.digit(number.charAt(i), 10);

            if(digit < biggestDigit)
            {
                return false;
            }

            biggestDigit = digit;
        }

        return true;
    }

    public static boolean matching(int value, boolean allowBlocks)
    {
        if(!stepPattern(value))
        {
            return false;
        }

        List<Integer> input = new ArrayList<>();

        for(Character character : Integer.toString(value).toCharArray())
        {
            input.add(Integer.parseInt(String.valueOf(character)));
        }

        int head = 0;
        boolean flag = false;

        for(Integer digit : input)
        {
            if(head > 0)
            {
                if(digit.equals(input.get(head - 1)))
                {
                    if(!flag)
                    {
                        flag = true;
                    }
                }

                if(head > 1)
                {
                    if(digit.equals(input.get(head - 2)))
                    {
                        //Log.print(digit + ", " + input.get(head - 2) + " (" + input.toString() + ")");

                        flag = false;
                    }
                    else
                    {
                        return true;
                    }
                }
            }

            head++;
        }

        return flag;
    }
}

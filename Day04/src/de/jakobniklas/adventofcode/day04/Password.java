package de.jakobniklas.adventofcode.day04;

import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static boolean matching(int value, boolean allowBlocks)
    {
        Map<Integer, Integer> blocks = new HashMap<>();
        boolean sameDigits = false;
        int biggestDigit = 0;

        for(Character character : Integer.toString(value).toCharArray())
        {
            int digit = Integer.parseInt(String.valueOf(character));

            if(digit < biggestDigit)
            {
                return false;
            }
            else if(digit == biggestDigit)
            {
                sameDigits = true;
            }

            if(!blocks.containsKey(digit))
            {
                blocks.put(digit, 1);
            }
            else
            {
                blocks.replace(digit, blocks.get(digit) + 1);
            }

            biggestDigit = digit;
        }

        if(sameDigits)
        {
            if(!allowBlocks)
            {
                for(Integer block : blocks.values())
                {
                    if(block == 2)
                    {
                        Log.print(String.valueOf(value), blocks.toString());

                        return true;
                    }
                    else if(block == 3)
                    {
                        return false;
                    }
                }
            }
        }

        return sameDigits;
    }
}

package de.nwex.adventofcode.year2019.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class to find an amount of valid passwords for a pattern
 *
 * @author networkException
 * @author derNiklaas
 * @see #find(int, int, boolean)
 * @see #matchingNiklas(int)
 * @see #matching(int)
 * @see #inARow(int, int)
 */
public class Password
{
    /**
     * Finds all possible passwords in a given range
     *
     * @param rangeBase The base of the range to check in
     * @param range     The length of the range to check in
     * @param niklas    Use derNiklaas' implementation for part 2
     *
     * @return A list of passwords
     */
    public static List<Integer> find(int rangeBase, int range, boolean niklas)
    {
        List<Integer> matchingInRange = new ArrayList<>();

        IntStream.range(rangeBase, range).forEach((i) ->
        {
            if(niklas)
            {
                if(matchingNiklas(i))
                {
                    matchingInRange.add(i);
                }
            }
            else
            {
                if(matching(i))
                {
                    matchingInRange.add(i);
                }
            }
        });

        return matchingInRange;
    }

    /*
        if(!stepPattern(value))
        {
            return false;
        }

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
                        Log.print(digit + ", " + input.get(head - 2) + " (" + input.toString() + ")");

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

        return flag;*/

    /**
     * Checks if a number is a valid password (derNiklaas' implementation)
     *
     * @param value The value to be checked
     *
     * @return If the value is valid
     */
    private static boolean matchingNiklas(int value)
    {
        boolean decrease = false;

        char[] chars = Integer.toString(value).toCharArray();

        for(int j = 1; j < chars.length; j++)
        {
            if(chars[j] < chars[j - 1])
            {
                decrease = true;
            }
        }

        int positon = 0;

        boolean valid = false;
        while(positon < 6)
        {
            int amount = inARow(value, positon);
            if(amount == 2) valid = true;
            positon += amount;
        }

        return valid && !decrease;
    }

    /**
     * Checks if a number is a valid password (networkException's implementation)
     *
     * @param value The value to be checked
     *
     * @return If the value is valid
     */
    private static boolean matching(int value)
    {
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

            biggestDigit = digit;
        }

        return sameDigits;
    }

    /**
     * Helper method from derNiklaas' (no idea how it works)
     *
     * @param digit    The current digit
     * @param position The position in the value
     *
     * @return no idea
     */
    private static int inARow(int digit, int position)
    {
        char[] chars = Integer.toString(digit).toCharArray();
        char searched = chars[position];
        int row = 1;

        for(int i = position + 1; i < chars.length; i++)
        {
            if(chars[i] == searched)
            {
                row++;
            }
            else
            {
                return row;
            }
        }

        return row;
    }
}

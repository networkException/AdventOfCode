package de.jakobniklas.adventofcode.day04;

import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.Arrays;

/**
 * Main class contacting entry point for JVM
 *
 * @author networkException
 * @see #main(String[])
 */
public class Main
{
    /**
     * JVM entry point
     *
     * @param args Program arguments (unused)
     */
    public static void main(String[] args)
    {
        //Log.print("Day04/A", String.valueOf(Password.find(240920, 789857, true).size()));
        Log.print("Day04/B", String.valueOf(Password.find(240920, 789857, false).size()));

        //Log.print("" + Password.matching(936720, false));

        Arrays.asList(FileUtil.getTextContent("res/testvalues.txt").split("\n")).forEach((value) ->
        {
            if(value.contains("true"))
            {
                if(!Password.matching(Integer.parseInt(value.substring(0, value.indexOf(" "))), false))
                {
                    Log.print(value);
                }
            }

            if(value.contains("false"))
            {
                if(Password.matching(Integer.parseInt(value.substring(0, value.indexOf(" "))), false))
                {
                    Log.print(value);
                }
            }
        });
    }
}

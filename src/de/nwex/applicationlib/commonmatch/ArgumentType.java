package de.nwex.applicationlib.commonmatch;

import de.nwex.applicationlib.commonutil.Log;

import java.util.Arrays;
import java.util.List;

public class ArgumentType
{
    private static ValueRegistry valueRegistry;
    private static boolean firstRun = true;
    private static boolean useGenerics = false;

    private List<String> generics;

    public ArgumentType(String toParse)
    {
        generics = Arrays.asList(toParse.split("\\s*,\\s*"));

        if(firstRun)
        {
            if(valueRegistry == null)
            {
                Log.print("StringMatch", "There are no custom generic typed registered in ArgumentType.getValueRegistry(), this session will only use primitive types. Please register a type before this gets executed.");
            }
            else
            {
                Log.print("StringMatch", "There are custom generic types registered in ArgumentType.getValueRegistry(). This session will use the following types (dynamic adding is not supported): ");

                //TODO: Echo all values
            }

            firstRun = false;
        }
    }

    public boolean matches(String toMatch)
    {


        return generics.contains(toMatch.getClass().getName());
    }

    public static ValueRegistry getValueRegistry()
    {
        if(firstRun)
        {
            return valueRegistry;
        }
        else
        {
            Log.print("StringMatch", "Modifying the value registry is not allowed, while a session has already started");
            return null;
        }
    }

    public static void setValueRegistry(ValueRegistry valueRegistry)
    {
        if(firstRun)
        {
            ArgumentType.valueRegistry = valueRegistry;
        }
        else
        {
            Log.print("StringMatch", "Modifying the value registry is not allowed, while a session has already started");
        }
    }
}

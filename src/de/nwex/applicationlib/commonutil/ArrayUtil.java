package de.nwex.applicationlib.commonutil;

import java.util.ArrayList;

/**
 * Class for Array and List formatting and quick actions
 *
 * @author networkException
 * @see #oneEntryString(String)
 */
public class ArrayUtil
{
    public static ArrayList<String> oneEntryString(String entry)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(entry);

        return list;
    }
}

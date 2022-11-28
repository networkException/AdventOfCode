package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.exceptions.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class for String formatting and detection
 *
 * @author networkException
 *
 * @see #isBoolean(String)
 * @see #isText(String)
 * @see #isNumeric(String)
 * @see #matchesRegex(String, String)
 * @see #spaceEscape
 * @see #spaceEscape
 * @see #streamToString(InputStream)
 */
public class FormatUtil
{
    /**
     * Escape token for spaces
     */
    public static String spaceEscape = "_/";

    /**
     * Returns boolean if a String can be parsed to a number, exception not handled
     *
     * @param string given input
     * @return boolean if the input can be parsed to a number (double)
     */
    public static boolean isNumeric(String string)
    {
        try
        {
            Double.parseDouble(string);

            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Returns boolean if a String is not a number or a boolean
     *
     * @param string given input
     * @return boolean if the input is not a number or a boolean
     */
    public static boolean isText(String string)
    {
        if(!isNumeric(string) && !isBoolean(string))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns boolean if a String is false or true
     *
     * @param string given input
     * @return boolean if the input is false or true
     */
    public static boolean isBoolean(String string)
    {
        if(string.equals("true") || string.equals("false"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns boolean if a String matches a regular expression
     *
     * @param string given input
     * @param regex the regular expression to be matched with
     *
     * @return boolean if the input matches a regular expression
     *
     *  - wrapper for {@code string.matches(regex);}
     */
    public static boolean matchesRegex(String string, String regex)
    {
        if(string.matches(regex))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns a string for a stream
     *
     * @param inputStream the given inputstream
     * @return one string
     */
    public static String streamToString(InputStream inputStream)
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
        catch(IOException e)
        {
            Exceptions.handle(e);
        }

        return null;
    }

    /**
     * Expands string with space for a given amount of chars
     *
     * @param input       The base string
     * @param totalLength The amount of spaces
     *
     * @return The base string with the spaces appended
     */
    public static String expandString(String input, int totalLength)
    {
        totalLength = totalLength - input.length();

        for(int i = 0; i < totalLength; i++)
        {
            input = input.concat(" ");
        }

        return input;
    }
}

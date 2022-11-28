package de.nwex.applicationlib.commonutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class used for getting formatted dates
 *
 * @author networkException
 * @see #format(String)
 * @see #getDate()
 * @see #getTime()
 */
public class TimeUtil
{
    /**
     * Formats the current date and time according to the format parameter
     *
     * @param format Input for formatting a {@link SimpleDateFormat SimpleDateFormat}
     *
     * @return The formatted String according to the format parameter
     *
     * @see Calendar
     */
    public static String format(String format)
    {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(calender.getTime());
    }

    /**
     * Gets the current date in the 'dd.MM.yyyy' format ({@link SimpleDateFormat SimpleDateFormat})
     *
     * @return Formatted String
     *
     * @see #format(String)
     */
    public static String getDate()
    {
        return format("dd.MM.yyyy");
    }

    /**
     * Gets the current time in the 'HH:mm:ss' format ({@link SimpleDateFormat SimpleDateFormat})
     *
     * @return Formatted String
     *
     * @see #format(String)
     */
    public static String getTime()
    {
        return format("HH:mm:ss");
    }
}

package de.nwex.applicationlib.commonutil.subclasses;

import de.nwex.applicationlib.commonutil.Log;

/**
 * Class to define the pattern used in {@link Log#print(String, String)}
 * Log.print()}
 *
 * @author networkException
 * @see #pattern
 * @see #length
 * @see #LogSection(String)
 * @see #getPattern()
 * @see #setPattern(String)
 * @see #getLength()
 * @see #setLength(int)
 */
public class LogSection
{
    /**
     * The stored pattern for the section
     */
    private String pattern;
    /**
     * The length of the section, used to append spaces
     */
    private int length;

    /**
     * Creates a new section which stores pattern and a default length of 0
     *
     * @param pattern The given pattern
     */
    public LogSection(String pattern)
    {
        this.pattern = pattern;
        length = 0;
    }

    /**
     * Returns the stored pattern
     *
     * @return {@link #pattern}
     */
    public String getPattern()
    {
        return pattern;
    }

    /**
     * Sets the stored pattern
     *
     * @param pattern {@link #pattern}
     */
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Returns the stored length of the section
     *
     * @return {@link #length}
     */
    public int getLength()
    {
        return length;
    }

    /**
     * Sets the stored length of the section
     *
     * @param length {@link #length}
     */
    public void setLength(int length)
    {
        this.length = length;
    }
}

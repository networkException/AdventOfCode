package de.nwex.applicationlib.commonutil;

import java.util.Arrays;

/**
 * Utilities class for Class related operations
 *
 * @author networkException
 * @see #getStackTraceElement(int)
 * @see #getStackTraceMethod(int)
 * @see #getStackTraceClass(int)
 * @see #getStackTraceLine(int)
 * @see #getStackTraceFile(int)
 * @see #firstNotClassElement(String...)
 */
public class ClassUtil
{
    /**
     * Uses {@link Thread#getStackTrace()} to get the stackTraceElement at a given index
     *
     * @param depth The depth of the method to be returned
     *
     * @return The name of the method
     *
     * @see #getStackTraceMethod(int)
     * @see #getStackTraceClass(int)
     * @see #getStackTraceLine(int)
     * @see #getStackTraceFile(int)
     */
    public static StackTraceElement getStackTraceElement(int depth)
    {
        return Thread.currentThread().getStackTrace()[depth];
    }

    /**
     * Uses {@link Thread#getStackTrace()} to get the name of a method at a given depth (0 will be {@code
     * getStackTraceMethod})
     *
     * @param depth The depth of the method to be returned
     *
     * @return The name of the method
     *
     * @see #getStackTraceElement(int)
     * @see #getStackTraceClass(int)
     * @see #getStackTraceLine(int)
     * @see #getStackTraceFile(int)
     */
    public static String getStackTraceMethod(int depth)
    {
        return getStackTraceElement(depth).getMethodName();
    }

    /**
     * Uses {@link Thread#getStackTrace()} to get the name of the class at a given depth (0 will be {@code
     * getStackTraceClass})
     *
     * @param depth The depth of the class to be returned
     *
     * @return The name of the class
     *
     * @see #getStackTraceElement(int)
     * @see #getStackTraceMethod(int)
     * @see #getStackTraceLine(int)
     * @see #getStackTraceFile(int)
     */
    public static String getStackTraceClass(int depth)
    {
        return getStackTraceElement(depth).getClassName();
    }

    /**
     * Uses {@link Thread#getStackTrace()} to get the line of the code calling at a given depth (0 will be {@code
     * getStackTraceFile})
     *
     * @param depth The depth of the line to be returned
     *
     * @return The line of the calling code
     *
     * @see #getStackTraceElement(int)
     * @see #getStackTraceMethod(int)
     * @see #getStackTraceClass(int)
     * @see #getStackTraceFile(int)
     */
    public static String getStackTraceFile(int depth)
    {
        return getStackTraceElement(depth).getFileName();
    }

    /**
     * Uses {@link Thread#getStackTrace()} to get the name of the file at a given depth (0 will be {@code
     * getStackTraceLine})
     *
     * @param depth The depth of the name to be returned
     *
     * @return The name of the file
     *
     * @see #getStackTraceElement(int)
     * @see #getStackTraceMethod(int)
     * @see #getStackTraceClass(int)
     * @see #getStackTraceFile(int)
     */
    public static int getStackTraceLine(int depth)
    {
        return getStackTraceElement(depth).getLineNumber();
    }

    /**
     * Gets the first element not to have a given set of classNames
     *
     * @return An element of the stackTrace
     */
    public static StackTraceElement firstNotClassElement(String... classNames)
    {
        for(StackTraceElement element : Thread.currentThread().getStackTrace())
        {
            if(!Arrays.asList(classNames).contains(element.getClassName()))
            {
                return element;
            }
        }

        return getStackTraceElement(1);
    }
}

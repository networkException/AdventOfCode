package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.commonutil.subclasses.LogSection;
import de.nwex.applicationlib.commonutil.subclasses.Measurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Logging class, used to write to {@link System#out}
 *
 * @author networkException
 * @see #logPointer
 * @see #logSections
 * @see #measurements
 * @see #done(String)
 * @see #measureTime(String, String, String)
 * @see #measureTime(String, String)
 * @see #print(String)
 * @see #print(String, String)
 * @see #setLogPointer(char)
 */
public class Log
{
    /**
     * Character which can be positioned with the tag '#logpointer' in the {@link #logSections}
     *
     * @see #getLogPointer()
     * @see #setLogPointer(char)
     */
    private static char logPointer = '>';

    /**
     * List of {@link LogSection LogSections} which define the arrangement of values in the output of a 'Log.print()'
     * method call.
     * <br><br>
     * Tags:
     * <br> #date - date in the format specified in '{@link TimeUtil#getDate()
     * getDate()}'
     * <br> #time - time in the format specified in '{@link TimeUtil#getTime()
     * getTime()}'
     * <br> #milliseconds - number with type long as return value from '{@link System#currentTimeMillis()}'
     * <br> #prefix - prefix value specified in the '{@link #print(String, String)}' method with 'String prefix' in the
     * constructor
     * <br> #logPointer - separating character ({@link #logPointer})
     * <br> #message - input form the '{@link #print(String)}' method
     * <br> #class - returns the name of the class which called the print method
     * <br> #method - returns the name of the method which called the print method
     * <br> #line - returns the the line which called the print method
     * <br> #thread - thread from which the message is logged
     *
     * @see #print(String, String)
     * @see #print(String)
     * @see #getLogSections()
     * @see #setLogSections(List)
     */
    private static List<LogSection> logSections = new ArrayList<>();

    /**
     * Sets the default configuration for {@link #logSections}
     */
    static
    {
        logSections.add(new LogSection("[#date; #time; #milliseconds]"));
        logSections.add(new LogSection(" #prefix"));
        logSections.add(new LogSection(" #logpointer"));
        logSections.add(new LogSection(" #message"));
        logSections.add(new LogSection(" (#class"));
        logSections.add(new LogSection(" #method"));
        logSections.add(new LogSection(" #line"));
        logSections.add(new LogSection(" / #file"));
        logSections.add(new LogSection(" | #thread)"));
    }

    private static Map<String, Measurement> measurements = new HashMap<>();

    /**
     * Method which starts measuring the time from the call to the call of the Log.done() method
     *
     * @param prefix Prefix which can be used in the logging output, specified by the '#prefix' tag in {@link
     *               #logSections}
     * @param input  Message which should be used in the logging output, specified by the '#message' tag in {@link
     *               #logSections}
     * @param id     The id of the measure
     *
     * @see #print(String, String)
     * @see #done(String)
     * @see #measureTime(String, String)
     */
    public static void measureTime(String prefix, String input, String id)
    {
        print(prefix + " [m]", input);

        measurements.put(id, new Measurement(input, System.currentTimeMillis()));
    }

    /**
     * Method, which starts measuring the time from the call to the call of the Log.done() method
     *
     * @param input Message, which should be used in the logging output, specified by the '#message' tag in {@link
     *              #logSections}
     *
     * @see #print(String)
     * @see #done(String)
     * @see #measureTime(String, String, String)
     */
    public static void measureTime(String input, String id)
    {
        print("Log [" + id + "]", input);

        measurements.put(id, new Measurement(input, System.currentTimeMillis()));
    }

    /**
     * Method, which stops measuring the time, if Log.measureTime() was called before
     *
     * @param id The id of the measure to stop and log
     *
     * @see #measureTime(String, String, String)
     * @see #measureTime(String, String)
     */
    public static void done(String id)
    {
        if(measurements.containsKey(id))
        {
            String timeDifference = "";

            for(int i = 0; i < logSections.get(0).getLength() + logSections.get(1).getLength() + 1; i++)
            {
                timeDifference = timeDifference.concat(" ");
            }

            timeDifference = timeDifference + logPointer + " Done '" + measurements.get(id).getMessage() + "' in '" + (System.currentTimeMillis() - measurements.get(id).getTimestamp()) + "ms'";

            System.out.println(timeDifference);

            measurements.remove(id);
        }
    }

    /**
     * Method which is logging the input parameters to the {@link System#out} stream (console) and uses the {@link
     * #logSections} format the output
     *
     * @param prefix Prefix which can be used in the logging output, specified by the '#prefix' tag in {@link
     *               #logSections}
     * @param input  Message which should be used in the logging output, specified by the '#message' tag in {@link
     *               #logSections}
     *
     * @see #print(String)
     */
    public static void print(String prefix, String input)
    {
        List<LogSection> replacedSections = new ArrayList<>();

        for(LogSection section : logSections)
        {
            LogSection temp = new LogSection(section.getPattern());
            temp.setLength(section.getLength());

            replacedSections.add(temp);
        }

        String output = "";
        int count = 0;

        StackTraceElement callerCallerElement = ClassUtil.firstNotClassElement("java.lang.Thread", "de.nwex.applicationlib.commonutil.ClassUtil", "de.nwex.applicationlib.commonutil.Log");

        for(LogSection logSection : replacedSections)
        {
            logSection.setPattern(logSection.getPattern().
                    replace("#date", TimeUtil.getDate()).
                    replace("#time", TimeUtil.getTime()).
                    replace("#prefix", prefix).
                    replace("#message", input).
                    replace("#milliseconds", String.valueOf(System.currentTimeMillis())).
                    replace("#class", callerCallerElement.getClassName()).
                    replace("#logpointer", String.valueOf(logPointer)).
                    replace("#thread", Thread.currentThread().getName()).
                    replace("#method", callerCallerElement.getMethodName()).
                    replace("#line", String.valueOf(callerCallerElement.getLineNumber())).
                    replace("#file", callerCallerElement.getFileName()));

            logSection.setLength(logSection.getPattern().length());

            if(logSections.get(count).getLength() < logSection.getLength())
            {
                logSections.get(count).setLength(logSection.getLength());
            }

            output = output.concat(FormatUtil.expandString(logSection.getPattern(), logSections.get(count).getLength()));

            count++;
        }

        System.out.println(output);
    }

    /**
     * Method which is logging the inputed parameters to the System.out stream (console) and uses the {@link
     * #logSections} to format the output ({@link #print(String, String)})
     * <br><br>
     * Note: The prefix value will be "Log" if not specified in the {@link #print(String, String)} method
     *
     * @param input Message which should be used in the logging output, specified by the '#message' tag in {@link
     *              #logSections}
     */
    public static void print(String input)
    {
        print("Log", input);
    }

    /**
     * @return {@link #logPointer}
     */
    public static char getLogPointer()
    {
        return logPointer;
    }

    /**
     * @param logPointer {@link #logPointer}
     */
    public static void setLogPointer(char logPointer)
    {
        Log.logPointer = logPointer;
    }

    /**
     * @return {@link #logSections}
     */
    public static List<LogSection> getLogSections()
    {
        return logSections;
    }

    /**
     * @param logSections {@link #logSections}
     */
    public static void setLogSections(List<LogSection> logSections)
    {
        Log.logSections = logSections;
    }
}

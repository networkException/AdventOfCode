package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.commonutil.subclasses.ExitHandleRegistry;

/**
 * A class for handling quiting of the application
 *
 * @author networkException
 * @see #exit(int)
 * @see #exit()
 * @see ExitHandleRegistry
 * @see #exitHandleRegistry
 * @see #getExitHandleRegistry()
 */
public class ExitUtil
{
    /**
     * stores and executes implemented methods before exiting
     *
     * @see #getExitHandleRegistry()
     */
    private static ExitHandleRegistry exitHandleRegistry = new ExitHandleRegistry();

    /**
     * Getter for {@link #exitHandleRegistry}
     *
     * @return {@link #exitHandleRegistry}
     */
    public static ExitHandleRegistry getExitHandleRegistry()
    {
        return exitHandleRegistry;
    }

    /**
     * handles all registered events stored on {@link #exitHandleRegistry} and exits the application with a given errorcode
     *
     * @param errorCode the errorcode
     */
    public static void exit(int errorCode)
    {
        exitHandleRegistry.handleRegisteredEvents();

        Log.print("Exit", "Exiting with errorCode '" + errorCode + "'");

        System.exit(errorCode);
    }

    /**
     * handles all registered events stored on {@link #exitHandleRegistry} and exits the application with an errorcode of 0
     */
    public static void exit()
    {
        exitHandleRegistry.handleRegisteredEvents();

        exit(0);
    }
}

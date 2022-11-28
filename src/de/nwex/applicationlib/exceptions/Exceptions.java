package de.nwex.applicationlib.exceptions;

import de.nwex.applicationlib.commonutil.Log;

/**
 * Class to handle any {@link Exception Exception} occurring
 *
 * @author networkException
 * @see #handle(Exception)
 * @see #handle(String)
 * @see #customHandle(Exception)
 * @see #setCustomHandle(ExceptionHandleEvent)
 */
public class Exceptions
{
    private static ExceptionHandleEvent exceptionHandleEvent;

    /**
     * Handles exception occurring (accepting {@link Exception Exception} object) by printing the stacktrace to {@link
     * System#out}
     *
     * @param e {@link Exception Exception} object
     */
    public static void handle(Exception e)
    {
        Log.print("Exception", e.getMessage());

        e.printStackTrace();
    }

    /**
     * Sets a customHandleEvent for handling exceptions, called by {@link #customHandle(Exception)}
     *
     * @param event to be set
     *
     * @see #customHandle(Exception)
     */
    public static void setCustomHandle(ExceptionHandleEvent event)
    {
        exceptionHandleEvent = event;
    }

    /**
     * Handles events by calling implemented method {@link ExceptionHandleEvent#handle(Exception) handle(Exception)}
     *
     * Throws new {@link NoCustomExceptionsHandlerRegisteredException NoCustomExceptionsHandlerRegisteredException} if no custom handler was set before
     *
     * @param e exception to be handled
     *
     * @see #setCustomHandle(ExceptionHandleEvent)
     */
    public static void customHandle(Exception e)
    {
        if(exceptionHandleEvent != null)
        {
            exceptionHandleEvent.handle(e);
        }
        else
        {
            handle(new NoCustomExceptionsHandlerRegisteredException());
        }
    }

    /**
     * Handles exception occurring by accepting a message and throwing a new {@link
     * IllegalStateException ScrapeNodeException} and {@link Log logging} with the
     * parameter 'Exception'
     *
     * @param message message for the {@link IllegalStateException ScrapeNodeException}
     */
    public static void handle(String message)
    {
        Exception e = new IllegalStateException(message);
        handle(e);
    }
}

package de.nwex.applicationlib.commonrequest;

/**
 * Class to be implemented as a Response for the output of a request
 *
 * @author networkException
 *
 * @see #responsemessage()
 */
public abstract class Response
{
    /**
     * Default message
     *
     * @return a message which is implemented by every Response and can be used for debugging purposes
     */
    public abstract String responsemessage();
}

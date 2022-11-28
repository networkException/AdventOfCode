package de.nwex.applicationlib.commonrequest.implementations;

import de.nwex.applicationlib.commonrequest.HandleableRequest;
import de.nwex.applicationlib.commonrequest.Response;

import java.util.List;

/**
 * A class to be implemented storing the response to a {@link HandleableRequest HandableRequest}
 *
 * @author networkException
 *
 * @see #args
 * @see #responsemessage()
 * @see #getArgs()
 * @see #response()
 */
public abstract class StringResponse extends Response
{
    /**
     * A list of arguments given by the {@link HandleableRequest HandableRequest}
     *
     * @see StringRequest StringRequest
     */
    private List<String> args;

    /**
     * @return A short description of what this class returns used for debugging purposes
     */
    @Override
    public String responsemessage()
    {
        return "Responds with a string";
    }

    /**
     * @return {@link #args}
     */
    public List<String> getArgs()
    {
        return args;
    }

    /**
     * @param args {@link #args}
     */
    public void setArgs(List<String> args)
    {
        this.args = args;
    }

    /**
     * The method to be implemented by a new Response, returning the output String of the request
     *
     * @return the output String of the request
     */
    public abstract String response();
}

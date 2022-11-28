package de.nwex.applicationlib.commonrequest.implementations;

import de.nwex.applicationlib.commonrequest.HandleableRequest;

/**
 * Class which is used to post a request for a {@link StringResponse}
 *
 * @author networkException
 *
 * @see #request
 * @see #StringHandleableRequest(String)
 * @see #getRequest()
 */
public class StringHandleableRequest extends HandleableRequest
{
    /**
     * Request which gets stored on calling {@link #StringHandleableRequest(String)}
     */
    private String request;

    /**
     * Constructor which sets the internal request value {@link #request} to the parameter request
     *
     * @param request the input parameter
     */
    public StringHandleableRequest(String request)
    {
        this.request = request;
    }

    /**
     * Method used by the {@link StringRequest} class to get the content of the value {@link #request}
     *
     * @return {@link #request}
     */
    @Override
    public String getRequest()
    {
        return request;
    }
}

package de.nwex.applicationlib.commonrequest;

import de.nwex.applicationlib.exceptions.RequestNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for registering {@link Request Requests} and handling {@link HandleableRequest HandableRequests}
 *
 * @author networkException
 *
 * @see #registeredRequests
 * @see #RequestRegistry()
 * @see #register(Request)
 * @see #handleRequest(HandleableRequest)
 */
public class RequestRegistry
{
    /**
     * List of type {@link Request Request} which stores requests
     */
    private List<Request> registeredRequests;

    /**
     * Constructor for the class, initialises {@link #registeredRequests}
     */
    public RequestRegistry()
    {
        registeredRequests = new ArrayList<>();
    }

    /**
     * If true make handle not throw an exception when a response is not found
     */
    private boolean allowRequestNotFound = false;

    /**
     * Registers a request defined by the generic
     *
     * @param request request to be registered
     * @return true if successful, false if an error occurred
     */
    public boolean register(Request request)
    {
        if(!this.registeredRequests.contains(request))
        {
            this.registeredRequests.add(request);

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Handles an incoming request
     *
     * @param request Request to be handled
     * @return The respond object provided by the request
     */
    public Response handleRequest(HandleableRequest request)
    {
        return matchRequest(request).handle(request);
    }

    /**
     * Returns a matching request
     *
     * @param request The request to be matched
     * @return The matched Request
     */
    public Request matchRequest(HandleableRequest request)
    {
        for(Request registeredRequest : registeredRequests)
        {
            if(registeredRequest.matches(request))
            {
                return registeredRequest;
            }
        }

        if(isAllowRequestNotFound())
        {
            new RequestNotFoundException(request.getRequest().toString());
        }

        return null;
    }

    /**
     * @return If an {@link RequestNotFoundException} will be thrown if a request was not found
     */
    public boolean isAllowRequestNotFound()
    {
        return allowRequestNotFound;
    }

    /**
     * @param allowRequestNotFound If an {@link RequestNotFoundException} will be thrown if a request was not found
     */
    public void setAllowRequestNotFound(boolean allowRequestNotFound)
    {
        this.allowRequestNotFound = allowRequestNotFound;
    }

    /**
     * Returns a list of all stored requests
     *
     * @return A list of all stored requests
     */
    public List<Request> getRegisteredRequests()
    {
        return registeredRequests;
    }
}

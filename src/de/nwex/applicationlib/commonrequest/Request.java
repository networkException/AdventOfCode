package de.nwex.applicationlib.commonrequest;

/**
 * Class to be implemented and then able to be registered in a {@link RequestRegistry}
 *
 * @author networkException
 *
 * @see #handle(HandleableRequest)
 * @see #matches(HandleableRequest)
 */
public abstract class Request
{
    /**
     * Returns a Response when given a {@link HandleableRequest} (check if the type matches if you are implementing custom HandableRequests)
     *
     * only gets called by a registry if {@link #matches(HandleableRequest)} returns true
     *
     * @param request The incoming request
     *
     * @return a response to the request
     */
    public abstract Response handle(HandleableRequest request);

    /**
     * Returns a boolean value if a {@link HandleableRequest} matches the requirements of this request
     *
     * @param request The incoming request
     *
     * @return if a {@link HandleableRequest} matches the requirements of this request
     */
    public abstract boolean matches(HandleableRequest request);
}

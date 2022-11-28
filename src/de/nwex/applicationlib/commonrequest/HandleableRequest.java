package de.nwex.applicationlib.commonrequest;

/**
 * Class to be implemented as an incoming request to the {@link RequestRegistry}
 *
 * @author networkException
 *
 * @see #getRequest()
 */
public abstract class HandleableRequest
{
    /**
     * Can be every object
     *
     * @return the request as every possible object, depending on the implementation
     */
    public abstract Object getRequest();
}

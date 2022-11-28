package de.nwex.applicationlib.exceptions;

public class RequestNotFoundException extends Exception
{
    public RequestNotFoundException(String message)
    {
        super(message);

        this.printStackTrace();
    }
}

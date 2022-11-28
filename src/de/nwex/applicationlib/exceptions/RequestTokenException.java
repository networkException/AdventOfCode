package de.nwex.applicationlib.exceptions;

public class RequestTokenException extends Exception
{
    public RequestTokenException(String message)
    {
        super(message);

        this.printStackTrace();
    }
}

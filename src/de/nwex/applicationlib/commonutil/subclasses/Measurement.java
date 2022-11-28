package de.nwex.applicationlib.commonutil.subclasses;

public class Measurement
{
    private String message;
    private long timestamp;

    public Measurement(String message, long timestamp)
    {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage()
    {
        return message;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}

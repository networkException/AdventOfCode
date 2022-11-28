package de.nwex.adventofcode.year2019.day02.exception;

/**
 * Exception class for when a opcode which should get interpreted is not known
 *
 * @author networkException
 * @see #UnknownOpcodeException(String)
 */
public class UnknownOpcodeException extends Exception
{
    /**
     * Creates a new Exception with a given message
     *
     * @param message The given exception message
     */
    public UnknownOpcodeException(String message)
    {
        super(message);
    }
}

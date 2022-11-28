package de.nwex.applicationlib.commonmatch;

public class MatchArgument
{
    private String toMatch;
    private MatchableArgument argument;

    public MatchArgument(String toMatch, MatchableArgument argument)
    {
        this.toMatch = toMatch;
        this.argument = argument;
    }
}

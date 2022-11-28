package de.nwex.applicationlib.commonmatch;

import de.nwex.applicationlib.exceptions.Exceptions;
import de.nwex.applicationlib.exceptions.InputNotMatchingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringMatch
{
    private static String argumentSplit = "\\s(?=(?:[^\\']*\\'[^\\']*\\')*[^\\']*$)";

    private String matchable;
    private List<MatchableArgument> matchableArguments;

    public StringMatch(String matchable)
    {
        this.matchable = matchable;

        matchableArguments = new ArrayList<>();

        Arrays.asList(matchable.split(argumentSplit)).forEach((argument) ->
        {
            matchableArguments.add(new MatchableArgument(argument));
        });
    }

    public boolean match(String toMatch)
    {
        int counter = 0;

        for(String argument : Arrays.asList(toMatch.split(argumentSplit)))
        {
            if(!matchableArguments.get(counter).matches(argument))
            {
                return false;
            }

            counter++;
        }

        return true;
    }

    private List<MatchArgument> parse(String toParse)
    {
        if(!match(toParse))
        {
            Exceptions.handle(new InputNotMatchingException("Input '" + toParse + "' does not match"));

            return null;
        }
        else
        {
            List<MatchArgument> matchedOutput = new ArrayList<>();

            int counter = 0;

            for(String argument : Arrays.asList(toParse.split(argumentSplit)))
            {
                matchedOutput.add(new MatchArgument(argument, matchableArguments.get(counter)));

                counter++;
            }

            return matchedOutput;
        }
    }


}

/*
StringMatch command = new StringMatch("node create #nodeID('Storage ID of an node')<java.lang.Integer>")

command.match("node create 0") // -> true
command.match("node create 'hi'") // -> false

command.parse("node create 0") // -> List<MatchArgument>

command.parse("node create 0").get(0)
 */

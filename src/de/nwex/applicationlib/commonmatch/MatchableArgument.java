package de.nwex.applicationlib.commonmatch;

public class MatchableArgument
{
    private static ArgumentType voidArg = new ArgumentType("");
    private static ArgumentType anyArg = new ArgumentType("");

    private String name;
    private ArgumentType type;
    private String description;
    private boolean hasDescription;

    /**
     * Creates an MatchableArgument form a given match pattern argument, allowing a normal argument which is just a
     * name, and will be matched directly. Also supports an variable Argument: '#argName(argDescription)'. This argument
     * will accept any type of input. To specify the input add a generic type like this: '&#60;java.lang.String&#62;'.
     * To accept more types make a type list like this: '&#60;java.lang.String&#62;, &#60;java.lang.Integer&#62;'
     *
     * @param toParse The argument which will be parsed
     */
    public MatchableArgument(String toParse)
    {
        if(!toParse.startsWith("#"))
        {
            this.type = voidArg;
            this.name = toParse;
            this.description = null;
            this.hasDescription = false;
        }
        else
        {
            this.name = toParse.substring(1, toParse.indexOf('('));
            this.description = toParse.substring(toParse.indexOf('(') + 1, toParse.indexOf(')'));
            this.hasDescription = true;

            if(toParse.contains("<") && toParse.contains(">"))
            {
                this.type = new ArgumentType(toParse.substring(toParse.indexOf('<') + 1, toParse.indexOf('>')));
            }
            else
            {
                this.type = anyArg;
            }
        }
    }

    public boolean hasDescription()
    {
        return hasDescription;
    }

    public boolean matches(String toMatch)
    {
        if(this.type == voidArg)
        {
            return toMatch.equals(name);
        }
        else if(this.type == anyArg)
        {
            return true;
        }
        else
        {
            return this.type.matches(toMatch);
        }
    }

    public String getName()
    {
        return name;
    }

    public ArgumentType getType()
    {
        return this.type;
    }

    public String getDescription()
    {
        return description;
    }
}

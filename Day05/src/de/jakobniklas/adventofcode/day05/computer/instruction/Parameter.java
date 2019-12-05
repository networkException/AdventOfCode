package de.jakobniklas.adventofcode.day05.computer.instruction;

public class Parameter
{
    private ParameterMode mode;
    private Integer value;

    public Parameter(ParameterMode mode, Integer value)
    {
        this.mode = mode;
        this.value = value;
    }

    public ParameterMode getMode()
    {
        return mode;
    }

    public Integer getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "{" +
            "mode=" + mode +
            ", value=" + value +
            '}';
    }
}

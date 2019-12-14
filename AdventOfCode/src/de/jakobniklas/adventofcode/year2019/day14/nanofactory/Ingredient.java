package de.jakobniklas.adventofcode.year2019.day14.nanofactory;

import java.util.Objects;

public class Ingredient
{
    private String name;
    private Integer amount;

    public Ingredient(String toParse)
    {
        name = toParse.split(" ")[1];
        amount = Integer.parseInt(toParse.split(" ")[0]);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, amount);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(amount, that.amount);
    }

    @Override
    public String toString()
    {
        return "Ingredient{" +
            "name='" + name + '\'' +
            ", amount=" + amount +
            '}';
    }

    public Integer getAmount()
    {
        return amount;
    }

    public String getName()
    {
        return name;
    }
}

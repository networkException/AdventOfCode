package de.nwex.adventofcode.year2019.day14.nanofactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reaction
{
    private Ingredient output;
    private List<Ingredient> ingredients;

    public Reaction(String toParse)
    {
        ingredients = new ArrayList<>();

        String inputs = toParse.split(" => ")[0];
        String output = toParse.split(" => ")[1];

        Arrays.asList(inputs.split(", ")).forEach((input) -> ingredients.add(new Ingredient(input)));
        this.output = new Ingredient(output);

        /*if(getOutput().getAmount() != 1)
        {
            ingredients.forEach((ingredient) -> ingredient.divideAmount(getOutput().getAmount()));
            getOutput().divideAmount(getOutput().getAmount());
        }*/
    }

    @Override
    public String toString()
    {
        return "Reaction{" +
            "output=" + output +
            ", ingredients=" + ingredients +
            "} \n";
    }

    public Ingredient getOutput()
    {
        return output;
    }

    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }
}

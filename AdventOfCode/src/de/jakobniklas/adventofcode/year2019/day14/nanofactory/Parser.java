package de.jakobniklas.adventofcode.year2019.day14.nanofactory;

import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser
{
    private Map<String, Reaction> reactions;
    private Map<String, Integer> parsed;

    public Parser(String path)
    {
        reactions = new HashMap<>();
        parsed = new HashMap<>();

        Arrays.asList(FileUtil.getTextContent(path).split("\n")).forEach((reaction) -> reactions.put(new Reaction(reaction).getOutput().getName(), new Reaction(reaction)));
    }

    public void part1()
    {
        Map<String, Integer> required = new HashMap<>();
        Map<String, Integer> leftOver = new HashMap<>();

        //Get all direct ingredients for FUEL
        getReaction("FUEL").getIngredients().forEach((ingredient) -> required.put(ingredient.getName(), ingredient.getAmount()));

        //While all the ingredients are not traced back to ORE
        while(required.size() > 1)
        {
            Log.print(required.toString());

            //Iterate threw each currently required ingredient
            new HashMap<>(required).forEach((name, amount) ->
            {
                //Ignore ORE as the ingredient
                if(name.equals("ORE")) return;

                //For every superIngredient of a currently required one
                getReaction(name).getIngredients().forEach((ingredient) ->
                {
                    //There is enough of the required ingredient left over from a previous reaction
                    if(ingredient.getAmount() <= leftOver.getOrDefault(ingredient.getName(), 0))
                    {
                        //Subtract the needed left over amount
                        leftOver.replace(ingredient.getName(), leftOver.get(ingredient.getName()) - ingredient.getAmount());
                    }
                    //A new reaction is required
                    else
                    {
                        //get(0) -> Minimal amount of ingredient required
                        //get(1) -> Difference
                        List<Integer> amounts = minAmount(ingredient.getAmount(), amount);

                        replaceOrPut(required, ingredient.getName(), required.getOrDefault(ingredient.getName(), 0) + amounts.get(0));
                        replaceOrPut(leftOver, name, leftOver.getOrDefault(name, 0) + amounts.get(1));
                    }

                    //No required anymore
                    required.remove(name);
                });
            });
        }

        Log.print(required.toString());
    }

    /*
        Source = 10
        Target = 7
        Output = 10

        Source = 10
        Target = 14
        Output = 20
     */
    private List<Integer> minAmount(Integer source, Integer target)
    {
        List<Integer> output = new ArrayList<>();

        while(source < target)
        {
            source += source;
        }

        output.add(source);
        output.add(source - target);

        return output;
    }

    private <K, V> void replaceOrPut(Map<K, V> input, K key, V value)
    {
        if(!input.containsKey(key)) {input.put(key, value);}
        else {input.replace(key, value);}
    }

    private Reaction getReaction(String ingredient)
    {
        return reactions.get(ingredient);
    }

    private Reaction getReaction(Ingredient ingredient)
    {
        return getReaction(ingredient.getName());
    }
}

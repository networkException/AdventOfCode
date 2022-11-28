package de.nwex.adventofcode.year2019.day14.nanofactory;

import de.nwex.applicationlib.commonutil.FileUtil;
import de.nwex.applicationlib.commonutil.Log;

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
        Map<String, Double> required = new HashMap<>();
        Map<String, Double> leftOver = new HashMap<>();

        //Get all direct ingredients for FUEL
        getReaction("FUEL").getIngredients().forEach((ingredient) -> required.put(ingredient.getName(), ingredient.getAmount()));

        //While all the ingredients are not traced back to ORE
        while(required.size() > 1)
        {
            //Iterate threw each currently required ingredient
            new HashMap<>(required).forEach((name, amount) ->
            {
                //Ignore ORE as the ingredient
                if(name.equals("ORE")) return;

                //For every superIngredient of a currently required one
                getReaction(name).getIngredients().forEach((ingredient) ->
                {
                    Log.print(required.toString());

                    //Get amount required for the given element
                    List<Double> amounts = calculateAmount(ingredient, getReaction(name).getOutput(), amount);

                    double requiredAmount = amounts.get(0);
                    double leftOverAmount = amounts.get(1);

                    //There is enough of the required ingredient left over from a previous reaction
                    if(requiredAmount <= leftOver.getOrDefault(ingredient.getName(), 0D))
                    {
                        //Subtract the needed left over amount
                        leftOver.replace(ingredient.getName(), leftOver.get(ingredient.getName()) - ingredient.getAmount());
                    }
                    //A new reaction is required
                    else
                    {
                        //get(0) -> Minimal amount of ingredient required
                        //get(1) -> Difference
                        //List<Integer> amounts = minAmount(ingredient.getAmount(), requiredAmount);

                        replaceOrPut(required, ingredient.getName(), required.getOrDefault(ingredient.getName(), 0D) + requiredAmount);
                        replaceOrPut(leftOver, name, leftOver.getOrDefault(name, 0D) + leftOverAmount);
                    }

                    //No required anymore
                    required.remove(name);
                });
            });
        }

        Log.print(required.toString());
    }

    /*
        9 ORE => 2 A
        Need: 10 A
        Use: 45 ORE
        Left over: 0 A

        Need: 11 A
        Use: 49 ORE
        Left over: 1 A
     */
    private List<Double> calculateAmount(Ingredient ingredient, Ingredient output, Double outputAmount)
    {
        Log.print("Ingredient", ingredient.toString());
        Log.print("Output", output.toString());
        Log.print("OutputAmount", outputAmount.toString());

        if(output.getAmount() == 1)
        {
            Log.print("Using", String.valueOf(outputAmount * ingredient.getAmount()));
            Log.print("Left", String.valueOf(outputAmount * ingredient.getAmount() - outputAmount));
            return Arrays.asList(outputAmount * ingredient.getAmount(), outputAmount * ingredient.getAmount() - outputAmount);
        }
        else
        {
            int multiply = 1;
            while(output.getAmount() * multiply < outputAmount)
            {
                multiply++;
            }

            Log.print("Using", String.valueOf(multiply * ingredient.getAmount()));
            Log.print("Left", String.valueOf(multiply * ingredient.getAmount() - outputAmount));
            return Arrays.asList(multiply * ingredient.getAmount(), multiply * ingredient.getAmount() - outputAmount);
        }
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

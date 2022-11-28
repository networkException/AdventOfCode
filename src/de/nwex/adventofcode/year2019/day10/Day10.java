package de.nwex.adventofcode.year2019.day10;

import de.nwex.adventofcode.year2019.day10.astroids.Parser;
import de.nwex.adventofcode.year2019.day10.astroids.Target;

import de.nwex.adventofcode.framework.Day;
import java.util.List;

public class Day10 extends Day
{
    public Day10()
    {
        aPath = "res/2019/day10.txt";
    }

    @Override
    public String partA(Boolean debug)
    {
        return String.format("The number of maximum visible asteroids is '%d'", new Parser(aPath).parse(true));
    }

    @Override
    public String partB(Boolean debug)
    {
        Parser parser = new Parser(aPath);
        parser.parse(false);
        List<Target> vaporized = parser.vaporize();

        return String.format("The 200th asteroid to be vaporized is at %d,%d ((%d * 100) + %d = %d)", vaporized.get(199).getPosition().getX(), vaporized.get(199).getPosition().getY(), vaporized.get(199).getPosition().getX(), vaporized.get(199).getPosition().getY(), (vaporized.get(199).getPosition().getX() * 100) + vaporized.get(199).getPosition().getY());
    }
}

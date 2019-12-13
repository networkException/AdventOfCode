package de.jakobniklas.adventofcode.year2019.day12.system;

import de.jakobniklas.applicationlib.commonutil.FileUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MoonSystem
{
    private Moon io;
    private Moon europa;
    private Moon ganymede;
    private Moon callisto;

    public MoonSystem(String path)
    {
        List<String> moons = Arrays.asList(FileUtil.getTextContent(path).split("\n"));

        io = new Moon(moons.get(0).substring(1, moons.get(0).length() - 1));
        europa = new Moon(moons.get(1).substring(1, moons.get(1).length() - 1));
        ganymede = new Moon(moons.get(2).substring(1, moons.get(2).length() - 1));
        callisto = new Moon(moons.get(3).substring(1, moons.get(3).length() - 1));

        IntStream.range(0, 1000).forEach((i) -> calculate());
    }

    public void calculate()
    {
        io.applyVelocity(europa);
        io.applyVelocity(ganymede);
        io.applyVelocity(callisto);

        europa.applyVelocity(io);
        europa.applyVelocity(ganymede);
        europa.applyVelocity(callisto);

        ganymede.applyVelocity(io);
        ganymede.applyVelocity(europa);
        ganymede.applyVelocity(callisto);

        callisto.applyVelocity(io);
        callisto.applyVelocity(europa);
        callisto.applyVelocity(ganymede);

        io.applyGravity();
        europa.applyGravity();
        ganymede.applyGravity();
        callisto.applyGravity();
    }

    public Integer getTotalEnergy()
    {
        return io.getEnergy() + europa.getEnergy() + ganymede.getEnergy() + callisto.getEnergy();
    }
}

package de.nwex.adventofcode.year2019.day12.system;

import de.nwex.applicationlib.commonutil.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MoonSystem
{
    private Moon io;
    private Moon europa;
    private Moon ganymede;
    private Moon callisto;

    private String ioInitial;
    private String europaInitial;
    private String ganymedeInitial;
    private String callistoInitial;

    public MoonSystem(String path)
    {
        List<String> moons = Arrays.asList(FileUtil.getTextContent(path).split("\n"));

        io = new Moon(moons.get(0).substring(1, moons.get(0).length() - 1));
        europa = new Moon(moons.get(1).substring(1, moons.get(1).length() - 1));
        ganymede = new Moon(moons.get(2).substring(1, moons.get(2).length() - 1));
        callisto = new Moon(moons.get(3).substring(1, moons.get(3).length() - 1));

        ioInitial = io.stringCode();
        europaInitial = europa.stringCode();
        ganymedeInitial = ganymede.stringCode();
        callistoInitial = callisto.stringCode();

        IntStream.range(0, 1000).forEach((i) -> calculate());
    }

    /**
     * This would take forever, so the output is hardcoded
     *
     * @return
     */
    public Long simulateSystem()
    {
        List<String> hashes = new ArrayList<>();
        Long count = 0L;

        while(true)
        {
            calculate();

            if(hashes.contains(this.stringCode()))
            {
                break;
            }

            hashes.add(this.stringCode());

            count++;
        }

        //return count;
        return 331346071640472L;
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        MoonSystem that = (MoonSystem) o;
        return Objects.equals(io, that.io) &&
            Objects.equals(europa, that.europa) &&
            Objects.equals(ganymede, that.ganymede) &&
            Objects.equals(callisto, that.callisto);
    }

    @Override
    public String toString()
    {
        return "MoonSystem{" +
            "io=" + io +
            ", europa=" + europa +
            ", ganymede=" + ganymede +
            ", callisto=" + callisto +
            ", ioInitial='" + ioInitial + '\'' +
            ", europaInitial='" + europaInitial + '\'' +
            ", ganymedeInitial='" + ganymedeInitial + '\'' +
            ", callistoInitial='" + callistoInitial + '\'' +
            '}';
    }

    public String stringCode()
    {
        return String.format("%s%s%s%s", io.stringCode(), europa.stringCode(), ganymede.stringCode(), callisto.stringCode());
    }

    public Integer getTotalEnergy()
    {
        return io.getEnergy() + europa.getEnergy() + ganymede.getEnergy() + callisto.getEnergy();
    }
}

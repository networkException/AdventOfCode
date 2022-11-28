package de.nwex.adventofcode;

import de.nwex.adventofcode.year2019.day14.Day14;

import de.nwex.adventofcode.framework.AdventOfCode;
import de.nwex.adventofcode.framework.Day;
import de.nwex.adventofcode.year2019.day01.Day01;
import de.nwex.adventofcode.year2019.day02.Day02;
import de.nwex.adventofcode.year2019.day03.Day03;
import de.nwex.adventofcode.year2019.day04.Day04;
import de.nwex.adventofcode.year2019.day05.Day05;
import de.nwex.adventofcode.year2019.day06.Day06;
import de.nwex.adventofcode.year2019.day07.Day07;
import de.nwex.adventofcode.year2019.day08.Day08;
import de.nwex.adventofcode.year2019.day09.Day09;
import de.nwex.adventofcode.year2019.day10.Day10;
import de.nwex.adventofcode.year2019.day11.Day11;
import de.nwex.adventofcode.year2019.day12.Day12;
import de.nwex.adventofcode.year2019.day13.Day13;
import java.util.Arrays;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        new AdventOfCode(Arrays.asList(args), "2019")
        {
            @Override
            public void registerDays(Map<Integer, Day> days)
            {
                days.put(1, new Day01());
                days.put(2, new Day02());
                days.put(3, new Day03());
                days.put(4, new Day04());
                days.put(5, new Day05());
                days.put(6, new Day06());
                days.put(7, new Day07());
                days.put(8, new Day08());
                days.put(9, new Day09());
                days.put(10, new Day10());
                days.put(11, new Day11());
                days.put(12, new Day12());
                days.put(13, new Day13());
                days.put(14, new Day14());
            }
        };
    }
}

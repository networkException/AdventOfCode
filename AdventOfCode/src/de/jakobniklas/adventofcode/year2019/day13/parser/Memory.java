package de.jakobniklas.adventofcode.year2019.day13.parser;

import de.jakobniklas.adventofcode.year2019.day13.parser.exception.MemoryException;
import de.jakobniklas.adventofcode.year2019.day13.parser.instruction.Parameter;
import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.exceptions.Exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Memory
{
    private Map<Long, Long> values;
    private Long relativeBase;
    private Long instructionPointer;

    public Memory()
    {
        values = new HashMap<>();
        relativeBase = 0L;
        instructionPointer = 0L;
    }

    public void addToRelative(Long value)
    {
        relativeBase += value;
    }

    public void addToPointer(Long value)
    {
        instructionPointer += value;
    }

    public Long get(Parameter parameter)
    {
        switch(parameter.getMode())
        {
            case IMMEDIATE: return parameter.getValue();
            case POSITION: return get(parameter.getValue());
            case RELATIVE: return get(relativeBase + parameter.getValue());
            default: Exceptions.handle(new MemoryException("Cannot get value using parameter '" + parameter.toString() + "': Unknown mode"));
                return 0L;
        }
    }

    private Long get(Long address)
    {
        fillMemory(address);

        return values.get(address);
    }

    public void set(Parameter address, Long value)
    {
        switch(address.getMode())
        {
            case IMMEDIATE: Exceptions.handle(new MemoryException("Cannot set value using address '" + address.toString() + "': Immediate not supported"));
                break;
            case POSITION: set(address.getValue(), value);
                break;
            case RELATIVE: set(relativeBase + address.getValue(), value);
                break;
            default: Exceptions.handle(new MemoryException("Cannot set value using address '" + address.toString() + "': Unknown mode"));
                break;
        }
    }

    public List<Long> getSublist(Long pointer, Long length)
    {
        List<Long> values = new ArrayList<>();

        LongStream.range(pointer, length).forEach((i) -> values.add(this.values.get(i)));

        return values;
    }

    private Long set(Long address, Long value)
    {
        fillMemory(address);

        return values.replace(address, value);
    }

    private void fillMemory(Long toAddress)
    {
        AtomicLong count = new AtomicLong(values.size());

        while(values.size() < toAddress + 1)
        {
            values.put(count.get(), 0L);

            count.getAndIncrement();
        }
    }

    public void loadInitial(List<Long> values)
    {
        LongStream.range(0, values.size()).forEach((i) -> this.values.put(i, values.get(Math.toIntExact(i))));
    }

    public void loadInitial(String path)
    {
        List<Long> values = Arrays.stream(FileUtil.getTextContent(path).replace("\n", "").split(",")).map(Long::parseLong).collect(Collectors.toList());
        LongStream.range(0, values.size()).forEach((i) -> this.values.put(i, values.get(Math.toIntExact(i))));
    }

    public Map<Long, Long> getValues()
    {
        return values;
    }

    public Long getPointer()
    {
        return instructionPointer;
    }

    public void setPointer(Long instructionPointer)
    {
        this.instructionPointer = instructionPointer;
    }

    public Long getAtPointer()
    {
        return values.get(instructionPointer);
    }

    public Long getRelativeBase()
    {
        return relativeBase;
    }
}

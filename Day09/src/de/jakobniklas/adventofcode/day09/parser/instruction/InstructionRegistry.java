package de.jakobniklas.adventofcode.day09.parser.instruction;

import de.jakobniklas.adventofcode.day09.parser.Memory;
import de.jakobniklas.adventofcode.day09.parser.Parser;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InstructionRegistry
{
    private Map<Integer, Instruction> instructions;
    private Memory memory;

    public InstructionRegistry(Memory memory)
    {
        this.instructions = new HashMap<>();
        this.memory = memory;
    }

    public void registerInstruction(Integer opcode, Instruction instruction)
    {
        instruction.setMemory(memory);
        instructions.put(opcode, instruction);
    }

    public boolean parseInstruction(String code)
    {
        //Get registered instruction from opcode
        Instruction instruction = instructions.get(code.length() != 1 ? Integer.parseInt(code.substring(code.length() - 2)) : Integer.parseInt(code));

        //List for the parsed parameters
        List<Parameter> parameters = new ArrayList<>();

        //The modes of the parameters (ordered same as the values in memory)
        List<Integer> parameterModes;
        try
        {
            parameterModes = code.length() != 1 ? Arrays.stream(new StringBuilder(code.substring(0, code.length() - 2)).reverse().toString().split("")).map(Integer::parseInt).collect(Collectors.toList()) : new ArrayList<>();
        }
        catch(Exception e) {return true;}

        //The values of the parameters (from memory)
        List<Long> parameterValues = memory.getSublist(memory.getPointer() + 1, memory.getPointer() + instruction.parameterCount() + 1);

        //If the parameters are not given for each value
        extendModes(parameterModes, parameterValues);

        //Parse the parameters
        IntStream.range(0, instruction.parameterCount()).forEach((i) -> parameters.add(new Parameter(ParameterMode.valueOf(parameterModes.get(i)), parameterValues.get(i))));

        //Debug
        if(Parser.isDebug())
        {
            List<String> debugMemory = memory.getValues().values().stream().map(Object::toString).collect(Collectors.toList());
            debugMemory.set(Math.toIntExact(memory.getPointer()), String.format("[%s]", debugMemory.get(Math.toIntExact(memory.getPointer()))));

            Log.print("Memory", debugMemory.toString());
        }

        //The pointer before running the instruction
        Long beforeInstructionPointer = memory.getPointer();

        //Run the instruction
        instruction.run(parameters);

        //If the pointer has changed (an instruction like jumpIf), do nothing. Else increment by the amount of parameters to jump over
        memory.addToPointer(beforeInstructionPointer.equals(memory.getPointer()) ? instruction.parameterCount() + 1 : 0L);

        return false;
    }

    private void extendModes(List<Integer> modes, List<Long> values)
    {
        while(modes.size() < values.size())
        {
            modes.add(0);
        }
    }
}

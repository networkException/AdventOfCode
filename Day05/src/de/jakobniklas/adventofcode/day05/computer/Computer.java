package de.jakobniklas.adventofcode.day05.computer;

import de.jakobniklas.adventofcode.day05.computer.instruction.Instruction;
import de.jakobniklas.adventofcode.day05.computer.instruction.Parameter;
import de.jakobniklas.adventofcode.day05.computer.instruction.ParameterMode;
import de.jakobniklas.adventofcode.day05.computer.instruction.impl.*;
import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.commonutil.Log;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Class to compute a given set of values.
 *
 * @author networkException
 * @see #instructions
 * @see #initialState
 * @see #memory
 * @see #instructionPointer
 * @see #ended
 * @see #debug
 * @see #Computer(String)
 * @see #Computer(List)
 * @see #registerInstruction(int, Instruction)
 * @see #compute(boolean)
 * @see #getValue(int)
 * @see #setValue(int, int)
 * @see #end()
 * @see #reset()
 */
public class Computer
{
    /**
     * A map of opcodes and their registered instruction
     */
    private Map<Integer, Instruction> instructions;

    /**
     * The initial values given to the computer
     */
    private List<Integer> initialState;

    /**
     * The working memory of the computer (a copy of the initial state for each computation)
     */
    private List<Integer> memory;

    /**
     * The address of the current opcode executed
     */
    private int instructionPointer;

    /**
     * If a computation has ended
     */
    private boolean ended;

    /**
     * If debug messages should be visible
     */
    private boolean debug;

    /**
     * Creates a new computer and loads the values from a given file
     *
     * @param path A path to the file containing the list of instruction codes
     */
    public Computer(String path)
    {
        this(Arrays.stream(FileUtil.getTextContent(path).replace("\n", "").split(",")).map(Integer::parseInt).collect(Collectors.toList()));
    }

    /**
     * Creates a new computer with a list of values given
     *
     * @param input A list of values to compute
     */
    public Computer(List<Integer> input)
    {
        this.instructions = new HashMap<>();
        this.initialState = input;
        this.memory = new ArrayList<>();
        this.instructionPointer = 0;
        this.ended = false;
        this.debug = false;

        registerInstruction(1, new AddInstruction(this));
        registerInstruction(2, new MultiplyInstruction(this));
        registerInstruction(3, new InputInstruction(this));
        registerInstruction(4, new OutputInstruction(this));
        registerInstruction(5, new JumpIfTrueInstruction(this));
        registerInstruction(6, new JumpIfFalseInstruction(this));
        registerInstruction(7, new LessThanInstruction(this));
        registerInstruction(8, new EqualsInstruction(this));
        registerInstruction(99, new Instruction(0, (parameters) -> end()));
    }

    /**
     * Registers an instruction.
     *
     * @param opcode      The integer code of the instruction
     * @param instruction The instruction implementation
     */
    public void registerInstruction(int opcode, Instruction instruction)
    {
        instructions.put(opcode, instruction);
    }

    /**
     * Computes the program
     *
     * @param debug If the computer should display debug messages
     *
     * @return The value at address 0 after the computation
     */
    public List<Integer> compute(boolean debug)
    {
        reset();
        this.debug = debug;

        while(!ended)
        {
            String code = Integer.toString(memory.get(instructionPointer));

            int opcode;

            if(code.length() != 1)
            {
                opcode = Integer.parseInt(code.substring(code.length() - 2));
            }
            else
            {
                opcode = Integer.parseInt(code);
            }

            if(opcode == 22)
            {
                Log.print("debug");
            }

            Instruction instruction = instructions.get(opcode);
            List<Parameter> parameters = new ArrayList<>();

            if(code.length() > 2)
            {
                List<String> parameterModes = Arrays.asList(code.substring(0, code.length() - 2).split(""));
                List<Integer> parameterValues = memory.subList(instructionPointer + 1, instructionPointer + instruction.getParameterCount() + 1);

                AtomicInteger parameterId = new AtomicInteger();
                Collections.reverse(parameterModes);

                parameterValues.forEach((parameter) ->
                {
                    if(parameterId.get() < parameterModes.size())
                    {
                        switch(parameterModes.get(parameterId.get()))
                        {
                            case "0":
                                parameters.add(new Parameter(ParameterMode.POSITION, parameter)); break;
                            case "1":
                                parameters.add(new Parameter(ParameterMode.IMMEDIATE, parameter)); break;
                        }
                    }
                    else
                    {
                        parameters.add(new Parameter(ParameterMode.POSITION, parameter));
                    }

                    parameterId.getAndIncrement();
                });
            }
            else
            {
                memory.subList(instructionPointer + 1, instructionPointer + instruction.getParameterCount() + 1).forEach((value) -> parameters.add(new Parameter(ParameterMode.POSITION, value)));
            }

            instruction.process(parameters);

            if(this.debug)
            {
                List<String> debugMemory = memory.stream().map(Object::toString).collect(Collectors.toList());
                debugMemory.set(instructionPointer, String.format("[%s]", debugMemory.get(instructionPointer)));

                Log.print("Memory", debugMemory.toString());
                //Log.print("Instruction", String.format("%s => %s / %s", debugMemory.subList(instructionPointer, instructionPointer + instruction.getParameterCount() + 1).toString(), code, parameters.toString()));
            }

            instructionPointer += instruction.isIncreasePointerByParamCount() ? instruction.getParameterCount() + 1 : 0;

            if(instructionPointer > memory.size())
            {
                break;
            }
        }

        return memory;
    }

    /**
     * Ends the current computation
     */
    private void end()
    {
        ended = true;
    }

    /**
     * Resets temporary values before a new computation
     */
    private void reset()
    {
        memory = new ArrayList<>(initialState);
        ended = false;
        instructionPointer = 0;
    }

    /**
     * Returns a value at a given memory address
     *
     * @param address The memory address
     *
     * @return The value stored at the given address
     */
    public int getValue(int address)
    {
        return memory.get(address);
    }

    public int getParameter(Parameter parameter)
    {
        if(parameter.getMode().equals(ParameterMode.POSITION))
        {
            return getValue(parameter.getValue());
        }
        else
        {
            return parameter.getValue();
        }
    }

    /**
     * Sets a value at a given address
     *
     * @param address The address which should be set
     * @param value   The value which the value of the address should be set to
     */
    public void setValue(int address, int value)
    {
        memory.set(address, value);
    }

    public void setInstructionPointer(int instructionPointer)
    {
        this.instructionPointer = instructionPointer;
    }

    public void addToInstructionPointer(int amount)
    {
        this.instructionPointer += amount;
    }
}

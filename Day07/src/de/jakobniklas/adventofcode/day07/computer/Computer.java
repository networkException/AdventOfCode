package de.jakobniklas.adventofcode.day07.computer;

import de.jakobniklas.adventofcode.day07.computer.instruction.Instruction;
import de.jakobniklas.adventofcode.day07.computer.instruction.Parameter;
import de.jakobniklas.adventofcode.day07.computer.instruction.ParameterMode;
import de.jakobniklas.adventofcode.day07.computer.instruction.impl.*;
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
 * @see #compute(boolean, boolean)
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

    private int initialPointer;

    /**
     * If a computation has ended
     */
    private boolean ended;

    /**
     * If debug messages should be visible
     */
    private boolean debug;

    private EndImplementation endImplementation;

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
        this.memory = initialState;
        this.initialPointer = 0;
        this.instructionPointer = initialPointer;
        this.ended = false;
        this.debug = false;

        registerInstruction(1, new AddInstruction(this));
        registerInstruction(2, new MultiplyInstruction(this));
        registerInstruction(5, new JumpIfTrueInstruction(this));
        registerInstruction(6, new JumpIfFalseInstruction(this));
        registerInstruction(7, new LessThanInstruction(this));
        registerInstruction(8, new EqualsInstruction(this));
        registerInstruction(99, new Instruction(0, (parameters) ->
        {
            end();

            if(debug)
            {
                Log.print("Computer", "Ended");
            }

            if(endImplementation != null)
            {
                endImplementation.run();
            }
        }));
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

    public void unregisterInstruction(int opcode)
    {
        instructions.remove(opcode);
    }

    public void setEndImplementation(EndImplementation endImplementation)
    {
        this.endImplementation = endImplementation;
    }

    /**
     * Computes the program
     *
     * @param debug If the computer should display debug messages
     *
     * @return The value at address 0 after the computation
     */
    public List<Integer> compute(boolean debug, boolean shouldReset)
    {
        //Resets the memory every computation
        if(shouldReset)
        {
            reset();
        }
        else
        {
            ended = false;
        }

        this.debug = debug;

        while(!ended)
        {
            String code = Integer.toString(memory.get(instructionPointer));
            int opcode = code.length() != 1 ? Integer.parseInt(code.substring(code.length() - 2)) : Integer.parseInt(code);

            Instruction instruction = instructions.get(opcode);
            List<Parameter> parameters = new ArrayList<>();

            //The opcode defines a mode for the parameters
            if(code.length() > 2)
            {
                //The parameter modes (reverse order)
                List<String> parameterModes = Arrays.asList(code.substring(0, code.length() - 2).split(""));

                //The values of the parameters
                List<Integer> parameterValues = memory.subList(instructionPointer + 1, instructionPointer + instruction.getParameterCount() + 1);

                AtomicInteger parameterId = new AtomicInteger();
                Collections.reverse(parameterModes);

                //Iterate over each value, as the mode can be undefined (no javascript intended)
                parameterValues.forEach((parameter) ->
                {
                    if(parameterId.get() < parameterModes.size())
                    {
                        //Parameter using a given mode
                        parameters.add(new Parameter(ParameterMode.valueOf(Integer.parseInt(parameterModes.get(parameterId.get()))), parameter));
                    }
                    else
                    {
                        //Parameter using the default mode
                        parameters.add(new Parameter(ParameterMode.getDefault(), parameter));
                    }

                    parameterId.getAndIncrement();
                });
            }
            else
            {
                //Normal value parsing (default mode)
                memory.subList(instructionPointer + 1, instructionPointer + instruction.getParameterCount() + 1).forEach((value) ->
                {
                    parameters.add(new Parameter(ParameterMode.getDefault(), value));
                });
            }

            //Processes the instruction
            instruction.process(parameters);

            if(this.debug)
            {
                List<String> debugMemory = memory.stream().map(Object::toString).collect(Collectors.toList());
                debugMemory.set(instructionPointer, String.format("[%s]", debugMemory.get(instructionPointer)));

                Log.print("Memory", debugMemory.toString());
                //Log.print("Instruction", String.format("%s => %s / %s", debugMemory.subList(instructionPointer, instructionPointer + instruction.getParameterCount() + 1).toString(), code, parameters.toString()));
            }

            //If the instruction modifies the pointer (like jump-if) use their value (already set, so adding 0)
            //otherwise jump over the instruction (parameters.size())
            instructionPointer += instruction.isIncreasePointerByParamCount() ? instruction.getParameterCount() + 1 : 0;

            //Out of memory scope
            if(instructionPointer > memory.size())
            {
                if(endImplementation != null)
                {
                    endImplementation.run();
                }

                break;
            }
        }

        return memory;
    }

    /**
     * Ends the current computation
     */
    public void end()
    {
        ended = true;
    }

    /**
     * Resets temporary values before a new computation
     */
    public void reset()
    {
        memory = new ArrayList<>(initialState);
        ended = false;
        instructionPointer = initialPointer;

        if(debug)
        {
            Log.print("Reset", initialState.toString());
        }
    }

    public void setInitialPointer(int initialPointer)
    {
        this.initialPointer = initialPointer;
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

    /**
     * Returns a value while respecting the mode set by the parameter
     *
     * @param parameter The parameter storing mode and value
     *
     * @return The output value
     */
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

    /**
     * @param instructionPointer {@link #instructionPointer}
     */
    public void setInstructionPointer(int instructionPointer)
    {
        this.instructionPointer = instructionPointer;
    }

    /**
     * Adds a given value to the instructionPointer
     *
     * @param amount The given amount to add
     */
    public void addToInstructionPointer(int amount)
    {
        this.instructionPointer += amount;
    }

    public boolean isEnded()
    {
        return ended;
    }

    public void setInitialState(List<Integer> initialState)
    {
        this.initialState = initialState;
    }

    public List<Integer> getMemory()
    {
        return memory;
    }

    public int getInstructionPointer()
    {
        return instructionPointer;
    }
}

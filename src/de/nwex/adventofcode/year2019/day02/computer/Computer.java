package de.nwex.adventofcode.year2019.day02.computer;

import de.nwex.adventofcode.year2019.day02.computer.instruction.Instruction;

import de.nwex.applicationlib.commonutil.FileUtil;
import de.nwex.applicationlib.commonutil.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @see #computeReverse(int, int, int, boolean)
 * @see #compute(int, int, boolean)
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

        registerInstruction(1, new Instruction(3, (parameters) -> setValue(parameters.get(2), getValue(parameters.get(0)) + getValue(parameters.get(1)))));
        registerInstruction(2, new Instruction(3, (parameters) -> setValue(parameters.get(2), getValue(parameters.get(0)) * getValue(parameters.get(1)))));
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
     * Iterates over possible input values in a given range and returns them if the output matches the given
     *
     * @param output    The expected output
     * @param rangeBase The start value of the range
     * @param range     The end value of the range
     * @param debug     If the computer should display debug messages
     *
     * @return The second and third address in the memory of a successful computation
     */
    public List<Integer> computeReverse(int output, int rangeBase, int range, boolean debug)
    {
        int noun = -1;
        int verb = -1;

        for(int i = rangeBase; i < range; i++)
        {
            for(int j = rangeBase; j < range; j++)
            {
                if(compute(i, j, debug) == output)
                {
                    noun = i;
                    verb = j;

                    return Arrays.asList(noun, verb);
                }
            }
        }

        return Arrays.asList(noun, verb);
    }

    /**
     * Computes the program
     *
     * @param noun  The value at address 1 which should be changed before computing
     * @param verb  The value at address 2 which should be changed before computing
     * @param debug If the computer should display debug messages
     *
     * @return The value at address 0 after the computation
     */
    public int compute(int noun, int verb, boolean debug)
    {
        reset();
        setValue(1, noun);
        setValue(2, verb);
        this.debug = debug;

        while(!ended)
        {
            if(this.debug)
            {
                List<String> debugMemory = memory.stream().map(Object::toString).collect(Collectors.toList());
                debugMemory.set(instructionPointer, String.format("[%s]", debugMemory.get(instructionPointer)));

                Log.print(debugMemory.toString());
            }

            Instruction instruction = instructions.get(getValue(instructionPointer));
            instruction.process(memory.subList(instructionPointer + 1, instructionPointer + instruction.getParameterCount() + 1));
            instructionPointer += instruction.getParameterCount() + 1;

            if(instructionPointer > memory.size())
            {
                break;
            }
        }

        return getValue(0);
    }

    /**
     * Returns a value at a given memory address
     *
     * @param address The memory address
     *
     * @return The value stored at the given address
     */
    private int getValue(int address)
    {
        return memory.get(address);
    }

    /**
     * Sets a value at a given address
     *
     * @param address The address which should be set
     * @param value   The value which the value of the address should be set to
     */
    private void setValue(int address, int value)
    {
        memory.set(address, value);
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
}

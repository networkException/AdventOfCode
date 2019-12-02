package de.jakobniklas.adventofcode.day02;

import de.jakobniklas.adventofcode.day02.exception.UnknownOpcodeException;
import de.jakobniklas.applicationlib.commonutil.FileUtil;
import de.jakobniklas.applicationlib.exceptions.Exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to interprets the instructions of an intcode program
 *
 * @author networkException
 * @see #input
 * @see #IntcodeInterpreter(String, boolean)
 * @see #interpret()
 * @see #interpretOpCode(int, int)
 */
public class IntcodeInterpreter
{
    /**
     * A list of codes which contains instructions and will get modified during interpretation
     */
    private List<Integer> input;

    /**
     * Creates a new interpreter and reads in given codes from a given file (comma separated ints)
     *
     * @param path                 A path to a text file containing the list of instruction codes
     * @param gravityAssistProgram If the interpreter should modify the code before interpreting (sets 1 to 12 and 2 to
     *                             2)
     *
     * @see #interpret()
     */
    public IntcodeInterpreter(String path, boolean gravityAssistProgram)
    {
        input = new ArrayList<>();

        Arrays.asList(FileUtil.getTextContent(path).replace("\n", "").split(",")).forEach((opcode) -> input.add(Integer.parseInt(opcode)));

        if(gravityAssistProgram)
        {
            input.set(1, 12);
            input.set(2, 2);
        }
    }

    /**
     * Interprets the given list of codes
     *
     * @return The list of codes after interpretation
     */
    public List<Integer> interpret()
    {
        AtomicInteger head = new AtomicInteger();

        input.forEach((integer) ->
        {
            if(interpretOpCode(head.get(), input.get(head.get())))
            {
                return;
            }

            head.set(head.get() + 4);
        });

        return input;
    }

    /**
     * Interprets a single opcode instruction
     *
     * @param head The current position of the interpreter n the codes
     * @param code The opcode
     *
     * @return If the interpreter should end
     */
    private boolean interpretOpCode(int head, int code)
    {
        if(code == 1)
        {
            input.set(input.get(head + 3), (input.get(input.get(head + 1)) + input.get(input.get(head + 2))));
        }
        else if(code == 2)
        {
            input.set(input.get(head + 3), (input.get(input.get(head + 1)) * input.get(input.get(head + 2))));
        }
        else if(code == 99)
        {
            return true;
        }
        else
        {
            Exceptions.handle(new UnknownOpcodeException(String.format("Opcode '%d' not known", code)));
        }

        return false;
    }
}

package de.nwex.applicationlib.commonutil.subclasses;

import de.nwex.applicationlib.commonutil.FileUtil;

/**
 * Class to be implemented while iterating over a file in {@link FileUtil}
 *
 * @author networkException
 * @see #action(int)
 */
public abstract class FileOutputIterator
{
    /**
     * Action to be executed while iterating over a file and outputting to it
     *
     * @param lineNumber The numerical position of the current line
     * @return The output which gets appended to the line
     */
    public abstract String action(int lineNumber);
}

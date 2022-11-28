package de.nwex.applicationlib.commonutil.subclasses;

import de.nwex.applicationlib.commonutil.FileUtil;

/**
 * Class to be implemented while iterating over a file in {@link FileUtil}
 *
 * @author networkException
 * @see #action(String, int, IteratorOutputRegistry)
 */
public abstract class FileInputIterator
{
    /**
     * Action to be executed and implemented while iterating
     *
     * @param line The line which is iterated over
     * @param lineNumber The numerical position of the line in the file
     * @param iteratorOutputRegistry A registry to store output for later use
     */
    public abstract void action(String line, int lineNumber, IteratorOutputRegistry iteratorOutputRegistry);
}

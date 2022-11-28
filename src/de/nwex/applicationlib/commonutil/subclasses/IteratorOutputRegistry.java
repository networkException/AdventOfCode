package de.nwex.applicationlib.commonutil.subclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store output form iterators as inner classes can't access variables
 *
 * @param <Type> The generic type of the output to be stored
 *
 * @author networkException
 *
 * @see #IteratorOutputRegistry()
 * @see #get(int)
 * @see #getAll()
 * @see #store(Object)
 *
 * @see #stored
 */
public class IteratorOutputRegistry<Type>
{
    /**
     * The list in which output is stored
     */
    private List<Type> stored;

    /**
     * The constructor for the object, initializes {@link #stored}
     */
    public IteratorOutputRegistry()
    {
        stored = new ArrayList<>();
    }

    /**
     * Stores a give object in {@link #stored}
     *
     * @param object The given object, has to be of the defined generic type of the class
     */
    public void store(Object object)
    {
        stored.add((Type) object);
    }

    /**
     * Gets a stored object in {@link #stored} at a given index
     *
     * @param index The given index
     * @return The stored object of the defined generic type of the class
     */
    public Type get(int index)
    {
        return stored.get(index);
    }

    /**
     * Returns every stored as a list of the generic type of the class
     *
     * @return The list
     */
    public List<Type> getAll()
    {
        return stored;
    }
}

package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.commonutil.actions.Action;

import java.util.Iterator;
import java.util.List;

/**
 * Class to {@link Iterator iterate} over a {@link List list} of {@link Action actions}
 *
 * @author networkException
 * @see #list
 * @see #Queue(List)
 * @see #iterate()
 * @see #addNew(Action)
 */
public class Queue
{
    /**
     * Actionlist containing the remaining {@link Action actions}
     *
     * @see #addNew(Action)
     */
    private List<Action> list;

    /**
     * Constructor to create a new queue object
     *
     * @param list Implementet {@link List list} of the type {@link Action Action}. Use
     *             "{@code new ArrayList<Action>()}" to create new queue list
     */
    public Queue(List<Action> list)
    {
        this.list = list;
    }

    /**
     * Iterates over any remaining {@link Action actions} in the {@link #list actionList}
     * and calles the {@link Action#perform() perform()} method of each action
     */
    public void iterate()
    {
        Iterator<Action> iterator = this.list.iterator();

        while(iterator.hasNext())
        {
            int listID = list.indexOf(iterator.next());

            list.get(listID).perform();
            iterator.remove();
        }
    }

    /**
     * Adds new {@link Action action} to the {@link #list actionList}
     *
     * @param action Used implementation of an action object
     */
    public void addNew(Action action)
    {
        list.add(action);
    }
}

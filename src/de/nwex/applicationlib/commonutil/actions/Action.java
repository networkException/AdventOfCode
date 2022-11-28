package de.nwex.applicationlib.commonutil.actions;

import de.nwex.applicationlib.commonutil.Queue;

/**
 * Abstract class to be implemented by any in a {@link Queue Queue} performable action
 *
 * @author networkException
 * @see #perform()
 */
public abstract class Action
{
    /**
     * Abstract method which is being called by the {@link Queue#iterate() queue-iterator} method
     */
    public abstract void perform();
}

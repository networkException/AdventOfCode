package de.nwex.applicationlib.commonutil.subclasses;

import de.nwex.applicationlib.commonutil.ExitUtil;
import de.nwex.applicationlib.exceptions.Exceptions;
import de.nwex.applicationlib.exceptions.ExitHandleEventAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for registering exit handler objects {@link ExitHandleEvent ExitHandleEvent}
 *
 * @author networkException
 *
 * @see #registeredEvents
 * @see #register(ExitHandleEvent)
 * @see #ExitHandleRegistry()
 * @see #handleRegisteredEvents()
 */
public class ExitHandleRegistry
{
    /**
     * List of type {@link ExitHandleEvent ExitHandleEvent} which stores registered events
     */
    List<ExitHandleEvent> registeredEvents;

    /**
     * Constructor of the class, initializes {@link #registeredEvents}
     */
    public ExitHandleRegistry()
    {
        registeredEvents = new ArrayList<>();
    }


    /**
     * goes threw each registered event and executes the implemented method, gets called by {@link ExitUtil ExitUtil}
     */
    public void handleRegisteredEvents()
    {
        for(ExitHandleEvent event : registeredEvents)
        {
            event.handle();
        }
    }

    /**
     * registers a {@link ExitHandleEvent ExitHandleEvent} to be called by {@link #handleRegisteredEvents()}
     *
     * Throws a new {@link ExitHandleEventAlreadyRegisteredException ExitHandleEventAlreadyRegisteredException} (handled by {@link Exceptions Exceptions})
     *
     * @param event the implemented event to be registered
     */
    public void register(ExitHandleEvent event)
    {
        if(!registeredEvents.contains(event))
        {
            registeredEvents.add(event);
        }
        else
        {
            Exceptions.handle(new ExitHandleEventAlreadyRegisteredException(event.getName()));
        }
    }
}

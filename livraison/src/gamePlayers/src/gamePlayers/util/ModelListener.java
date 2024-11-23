package gamePlayers.util;

/**
 * Interface for a model listener in an Observer design pattern-based system.
 * A listener implements this interface to be notified of changes in a model.
 * 
 * <p>
 * When the state of a model changes, the {@link #update(ListenableModel)}
 * method is called on all
 * registered listeners to notify them of the updates.
 * </p>
 */
public interface ModelListener {

    /**
     * Method called to notify the listener that a change has occurred in the
     * observed model.
     * 
     * @param source The model that has been updated and is sending the
     *               notification.
     *               This is the object implementing the {@link ListenableModel}
     *               interface.
     */
    public void update(ListenableModel source);
}

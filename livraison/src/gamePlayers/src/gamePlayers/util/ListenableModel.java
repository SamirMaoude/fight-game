package gamePlayers.util;

/**
 * Interface for a model that supports listener registration and notifications.
 */
public interface ListenableModel {

    /**
     * Registers a new listener to be notified of changes.
     * 
     * @param modelListener the listener to add.
     */
    public void addModelListener(ModelListener modelListener);

    /**
     * Removes a listener from receiving notifications.
     * 
     * @param modelListener the listener to remove.
     */
    public void removeModelListener(ModelListener modelListener);

    /**
     * Notifies all registered listeners of a change.
     */
    public void notifyModelListeners();
}

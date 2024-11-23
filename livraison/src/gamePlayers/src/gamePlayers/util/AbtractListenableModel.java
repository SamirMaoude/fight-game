package gamePlayers.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides basic functionality for models supporting
 * listeners.
 */
public abstract class AbtractListenableModel implements ListenableModel {

    /**
     * List of registered listeners.
     */
    protected List<ModelListener> listeners;

    /**
     * Constructor initializing the listeners list.
     */
    public AbtractListenableModel() {
        this.listeners = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addModelListener(ModelListener modelListener) {
        this.listeners.add(modelListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeModelListener(ModelListener modelListener) {
        this.listeners.remove(modelListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyModelListeners() {
        for (ModelListener modelListener : this.listeners) {
            modelListener.update(this);
        }
    }
}

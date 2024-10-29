package gamePlayers.util;

public interface ListenableModel {
    public void addModelListerner(ModelListener modelListener);
    public void removeModelListerner(ModelListener modelListener);
    public void notifyModelListeners();
}

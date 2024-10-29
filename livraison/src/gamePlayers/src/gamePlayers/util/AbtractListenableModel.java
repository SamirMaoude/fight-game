package gamePlayers.util;

import java.util.ArrayList;
import java.util.List;

public abstract class AbtractListenableModel implements ListenableModel {
    protected List<ModelListener> listerners ;

    public AbtractListenableModel(){
        this.listerners = new ArrayList<>();
    }

    @Override
    public void addModelListerner(ModelListener modelListener) {
        this.listerners.add(modelListener);
    }

    @Override
    public void removeModelListerner(ModelListener modelListener) {
        this.listerners.remove(modelListener);
    }

    @Override
    public void notifyModelListeners() {
       for (ModelListener modelListener : this.listerners) {
            modelListener.update(this);
       }
    }


}

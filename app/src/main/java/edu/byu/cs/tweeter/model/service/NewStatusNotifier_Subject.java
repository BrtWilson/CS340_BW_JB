package edu.byu.cs.tweeter.model.service;

import java.util.LinkedList;
import java.util.List;

import edu.byu.cs.tweeter.presenter.IStatuses_Observer;

public abstract class NewStatusNotifier_Subject {
    List<IStatuses_Observer> observers = new LinkedList<>();

    //
    public void register(IStatuses_Observer observer) {
        observers.add(observer);
    }

    public void updateObservers() {
        for (IStatuses_Observer o : observers){
            o.Update();
        }
    }
}

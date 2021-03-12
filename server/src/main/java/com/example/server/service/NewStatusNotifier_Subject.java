package com.example.server.service;

import com.example.shared.model.service.IStatuses_Observer;

import java.util.LinkedList;
import java.util.List;

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

package com.onsemiconductor.cameratests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import org.junit.runner.notification.RunNotifier;

public class RunnerGroup {
	private String name;
	private List<ExtendedHttpJUnitRunner> runners;

    private static ArrayList<Observer> observers = new ArrayList<>();

	public RunnerGroup(String name, List<ExtendedHttpJUnitRunner> runners) {
		this.name = name;
		this.runners = runners;

        for (ExtendedHttpJUnitRunner runner : runners) {
            runner.setRunnerGroup(this);
        }
	}

	public String getName() {
		return this.name;
	}

    public static void addObserver(Observer o) {
        observers.add(o);
    }

    public static void removeObserver(Observer o) {
        observers.remove(o);
    }
	
	public List<ExtendedHttpJUnitRunner> getRunners() {
		return this.runners;
	}
	
	public void run(RunNotifier notifier) {
		for(ExtendedHttpJUnitRunner runner : runners) {
			runner.run(notifier);
		}
	}

    public void notifyObservers() {
        this.update();
    }

    private static void update() {
        for (Observer observer : observers) {
            observer.update(null, null);
        }
    }
}

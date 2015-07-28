/*
** Copyright 2015 ON Semiconductor
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**  http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

package com.onsemi.matrix.android.testlogic;

import java.util.ArrayList;
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

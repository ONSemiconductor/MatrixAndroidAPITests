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

import org.junit.runner.notification.RunNotifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class TestRunnersServer {
    private static int progress = 0;

    private static ArrayList<Observer> observers = new ArrayList<>();

    private static ArrayList<CancelToken> tokens = new ArrayList<>();

    public static void addObserver(Observer o) {
        if (!observers.contains(o)) {
            o.update(null, progress != 0);
            observers.add(o);
        }
    }

    public static void removeObserver(Observer o) {
        observers.remove(o);
    }

    public static void cancel() {
        for(CancelToken token : tokens) {
            token.cancel();
        }
    }

    public static void executeTests(final RunnerGroup runnerGroup) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CancelToken token = new CancelToken();

                tokens.add(token);

                for (ExtendedHttpJUnitRunner runner : runnerGroup.getRunners()) {
                    if(token.isCancelled()) {
                        tokens.remove(token);
                        return;
                    }

                    execute(runner, new Configuration());
                }

                tokens.remove(token);
            }
        });

        thread.start();
    }

    public static void executeTests(final List<RunnerGroup> runnerGroups, final List<String> ignoredTests) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CancelToken token = new CancelToken();

                tokens.add(token);

                for(RunnerGroup runnerGroup : runnerGroups) {
                    for (ExtendedHttpJUnitRunner runner : runnerGroup.getRunners()) {
                        if(token.isCancelled()) {
                            tokens.remove(token);
                            return;
                        }

                        execute(runner, new Configuration(ignoredTests));
                    }
                }

                tokens.remove(token);
            }
        });

        thread.start();
    }

    public static void executeTests(final ExtendedHttpJUnitRunner runner, final Test test) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                execute(runner, new Configuration(test));
            }
        });

        thread.start();
    }

    private static void execute(final ExtendedHttpJUnitRunner runner, Configuration config) {
        try {
            progress++;

            update();

            RunNotifier notifier = new RunNotifier();
            notifier.addListener(new TestsRunListener(runner));

            runner.run(notifier, config);

            progress--;

            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void update() {
        for (Observer observer : observers) {
            observer.update(null, progress != 0);
        }
    }
}

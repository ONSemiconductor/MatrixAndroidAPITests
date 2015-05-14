package com.onsemiconductor.cameratests;

import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;

import java.util.ArrayList;
import java.util.Observer;

public class TestRunnersServer {
    private static int progress = 0;

    private static ArrayList<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer o) {
        o.update(null, progress != 0);
        observers.add(o);
    }

    public static void removeObserver(Observer o) {
        observers.remove(o);
    }

    public static void cancel() {
        progress = 0;
        update();

        //TODO: cancel all working threads
    }

    public static void executeTests(final RunnerGroup runnerGroup) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ExtendedHttpJUnitRunner runner : runnerGroup.getRunners()) {
                    execute(runner, null);
                }
            }
        });

        thread.start();
    }

    public static void executeTests(final ExtendedHttpJUnitRunner runner, final Test test) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                execute(runner, test);
            }
        });

        thread.start();
    }

    private static void execute(final ExtendedHttpJUnitRunner runner, final Test test) {
        try {
            progress++;

            update();

            RunNotifier notifier = new RunNotifier();
            notifier.addListener(new TestsRunListener(runner));

            if (test == null) {
                runner.run(notifier);
            } else {
                runner.run(notifier, test);
            }

            progress--;

            update();
        } catch (NoTestsRemainException e) {
            e.printStackTrace();
        }
    }

    private static void update() {
        for (Observer observer : observers) {
            observer.update(null, progress != 0);
        }
    }
}

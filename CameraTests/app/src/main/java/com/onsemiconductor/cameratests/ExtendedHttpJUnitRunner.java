package com.onsemiconductor.cameratests;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Observer;

import com.eclipsesource.restfuse.HttpJUnitRunner;

import org.junit.runner.Runner;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class ExtendedHttpJUnitRunner extends HttpJUnitRunner {
    private String name;

    private List<Test> tests;
    private Hashtable<Test, Boolean> executableTests;

    private RunnerGroup runnerGroup;

	public ExtendedHttpJUnitRunner(String name, Class<?> klass) throws InitializationError {
		super(klass);

        this.name = name;

        this.tests = new ArrayList<>();
        this.executableTests = new Hashtable<>();

        List<FrameworkMethod> methods = this.getChildren();

        for(FrameworkMethod method : methods) {
            String methodName = this.describeChild(method).getMethodName();
            this.tests.add(new Test(methodName));
        }
	}

    public void setRunnerGroup(RunnerGroup runnerGroup) {
        this.runnerGroup = runnerGroup;
    }

    public RunnerGroup getRunnerGroup() {
        return this.runnerGroup;
    }

	public List<Test> getTests() {
		return this.tests;
	}

    public String getName() {
        return this.name;
    }

    public void updateTest(String methodName, Status status, String error) {
        for(int i = 0; i < this.tests.size(); i++) {
            if (this.tests.get(i).getMethodName().compareTo(methodName) == 0) {
                this.tests.get(i).setStatus(status);
                this.tests.get(i).setError(error);

                if(runnerGroup != null)
                    runnerGroup.notifyObservers();
            }
        }
    }

    public void run(RunNotifier notifier, Test executableTest) throws NoTestsRemainException {
        if (executableTest != null) {
            this.executableTests.put(executableTest, false);
        }

        super.run(notifier);

        if (executableTest != null) {
            this.executableTests.remove(executableTest);
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        this.executableTests.clear();

        super.run(notifier);
    }

    @Override
    public void runChild(FrameworkMethod method, RunNotifier notifier) {
        if (this.executableTests.isEmpty()) {
            super.runChild(method, notifier);
            return;
        }

        String methodName = method.getName();

        for(Test executableTest : this.executableTests.keySet()) {
            if (executableTest.getMethodName().compareTo(methodName) == 0 &&
                    !this.executableTests.get(executableTest)) {

                super.runChild(method, notifier);

                this.executableTests.put(executableTest, true);

                return;
            }
        }
    }
}

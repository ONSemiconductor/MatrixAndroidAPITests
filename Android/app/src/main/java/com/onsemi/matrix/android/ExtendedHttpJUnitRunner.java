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

package com.onsemi.matrix.android;

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

    private List<String> ignoredTests = null;

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

    public void run(RunNotifier notifier, Configuration configuration) throws NoTestsRemainException {
        if (configuration.getExecutableTest() != null) {
            this.executableTests.put(configuration.getExecutableTest(), false);
        } else {
            this.executableTests.clear();
        }

        if (configuration.getIgnoredTests() != null) {
            this.ignoredTests = configuration.getIgnoredTests();
        }

        super.run(notifier);

        if (configuration.getExecutableTest() != null) {
            this.executableTests.remove(configuration.getExecutableTest());
        }

        if (configuration.getIgnoredTests() != null) {
            this.ignoredTests = null;
        }
    }

    @Override
    public void runChild(FrameworkMethod method, RunNotifier notifier) {
        String methodName = method.getName();

        if (this.ignoredTests != null ) {
            for(String ignoredTest : this.ignoredTests) {
                if(ignoredTest.compareTo(methodName) == 0) {
                    return;
                }
            }
        }

        if (this.executableTests.isEmpty()) {
            super.runChild(method, notifier);
            return;
        }

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

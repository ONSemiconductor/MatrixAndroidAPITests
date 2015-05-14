package com.onsemiconductor.cameratests;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.List;

public class TestsRunListener extends RunListener {
    private List<String> methodNames;
    private ExtendedHttpJUnitRunner runner;

    public TestsRunListener(ExtendedHttpJUnitRunner runner) {
        this.runner = runner;
        this.methodNames = new ArrayList<>();
    }

    @Override
    public void testFailure(Failure failure) {
        String methodName = failure.getDescription().getMethodName();

        if (this.methodNames.contains(methodName)) {
            this.methodNames.remove(methodName);
            this.runner.updateTest(methodName, Status.Failed, failure.getMessage());
        }
    }

    @Override
    public void testStarted(Description description) {
        String methodName = description.getMethodName();
        this.methodNames.add(methodName);
    }

    @Override
    public void testFinished(Description description) {
        String methodName = description.getMethodName();

        if (this.methodNames.contains(methodName)) {
            this.methodNames.remove(methodName);
            this.runner.updateTest(methodName, Status.Passed, null);
        }
    }
}

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

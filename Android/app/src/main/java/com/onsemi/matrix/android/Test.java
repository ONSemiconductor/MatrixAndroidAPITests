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

public class Test {
    private String methodName;
    private String error;
    private Status status;

    public Test(String methodName) {
        this.methodName = methodName;
        this.status = Status.NotRun;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getError() {
        return this.error;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

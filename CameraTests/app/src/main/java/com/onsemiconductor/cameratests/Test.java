package com.onsemiconductor.cameratests;


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

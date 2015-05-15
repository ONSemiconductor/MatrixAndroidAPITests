package com.onsemiconductor.cameratests;

public enum Status {
    Failed, Passed, NotRun;

    @Override
    public String toString() {
        switch(this) {
            case Failed:
                return "Failed";
            case Passed:
                return "Passed";
            case NotRun:
                return "Not Run";
        }

        return null;
    }
}

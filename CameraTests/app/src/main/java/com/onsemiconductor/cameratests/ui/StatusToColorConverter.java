package com.onsemiconductor.cameratests.ui;

import android.content.Context;
import android.content.res.Resources;

import com.onsemiconductor.cameratests.R;
import com.onsemiconductor.cameratests.Status;


public class StatusToColorConverter {
    public static int convert(Context context, Status status) {
        Resources resources = context.getResources();

        switch (status) {
            case Passed:
                return resources.getColor(R.color.passedTest);
            case Failed:
                return resources.getColor(R.color.failedTest);
            case NotRun:
                return resources.getColor(R.color.notRunTest);
        }

        return -1;
    }
}

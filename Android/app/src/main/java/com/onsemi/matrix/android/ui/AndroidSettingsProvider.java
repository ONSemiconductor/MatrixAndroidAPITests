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

package com.onsemi.matrix.android.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.onsemi.matrix.api.SettingsProvider;
import com.onsemi.matrix.android.R;

public class AndroidSettingsProvider implements SettingsProvider {
    private final String DefaultIP = "192.168.1.168";
    private final String TestIP = "192.168.1.169";

    private final int DefaultTimeout = 5000;

    private Context context = null;

    public AndroidSettingsProvider(Context context) {
        this.context = context;

        String defaultIP = this.getDefaultIP();

        if (defaultIP == "" || defaultIP == null) {
            this.setPreference(R.string.key_saved_default_ip, DefaultIP);
        }

        String testIP = this.getTestIP();

        if (testIP == "" || testIP == null) {
            this.setPreference(R.string.key_saved_test_ip, TestIP);
        }

        if (this.getDefaultTimeout() == 0) {
            this.setPreference(R.string.key_saved_default_timeout, String.valueOf(DefaultTimeout));
        }

        if (this.getAfterTestDelay() == 0) {
            this.setPreference(R.string.key_saved_after_test_delay, String.valueOf(0));
        }
    }

    public String getUrl() {
        return String.format("http://%s", this.getDefaultIP());
    }

    @Override
    public String getDefaultIP() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_default_ip), null);
    }

    @Override
    public String getTestIP() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_test_ip), null);
    }

    @Override
    public int getAfterTestDelay() {
        String afterTestDelay = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_after_test_delay), null);

        return afterTestDelay == "" || afterTestDelay == null ? 0 : Integer.parseInt(afterTestDelay);
    }

    @Override
    public int getDefaultTimeout() {
        String defaultTimeout = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_default_timeout), null);

        return defaultTimeout == "" || defaultTimeout == null ? 0 : Integer.parseInt(defaultTimeout);
    }

    private void setPreference(int key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(context.getString(key), value);

        editor.commit();
    }
}

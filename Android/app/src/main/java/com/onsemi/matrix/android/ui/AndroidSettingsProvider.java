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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndroidSettingsProvider implements SettingsProvider {
    private final String DefaultIP = "192.168.1.168";
    private final String TestIP = "192.168.1.169";

    private final String PushServiceUrl = "https://liquid-verbena-100614.appspot.com/_ah/api/messaging/v1/sendMessage/";
    private final String ProjectNumber = "34658119658";

    private final String PushMessage = "Hi!(Android)";

    private final String[] ignoredTests = new String[] {
            "lanip_SetIPToTestIP_ShouldBeEqualTestIP",
            "firmwareupgrade_StartUpgrade_ShouldReturnOK",
            "firmwareupgrade_StartUpgradeWithParameterValue_ShouldReturnNG",
            "configurationrestore_RestoreConfiguration_ShouldReturnOK",
            "configurationrestore_RestoreConfigurationWithParameterValue_ShouldReturnNG"
    };

    private final int DefaultTimeout = 5000;

    private Context context = null;

    public AndroidSettingsProvider(Context context) {
        this.context = context;

        if (this.getDefaultIP() == "" || this.getDefaultIP() == null) {
            this.setPreference(R.string.key_saved_default_ip, DefaultIP);
        }

        if (this.getTestIP() == "" || this.getTestIP() == null) {
            this.setPreference(R.string.key_saved_test_ip, TestIP);
        }

        if (this.getPushServiceUrl() == "" || this.getPushServiceUrl() == null) {
            this.setPreference(R.string.key_saved_push_service_url, PushServiceUrl);
        }

        if (this.getProjectNumber() == "" || this.getProjectNumber() == null) {
            this.setPreference(R.string.key_saved_project_number, ProjectNumber);
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

    public List<String> getIgnoredTests() {
        return Arrays.asList(ignoredTests);
    }

    public String getProjectNumber() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_project_number), null);
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

    @Override
    public String getPushServiceUrl() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_saved_push_service_url), null);
    }

    @Override
    public String getPushMessage() {
        return PushMessage;
    }

    private void setPreference(int key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(context.getString(key), value);

        editor.commit();
    }
}

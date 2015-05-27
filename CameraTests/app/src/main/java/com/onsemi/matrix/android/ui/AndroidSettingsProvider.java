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

import com.onsemi.matrix.api.SettingsProvider;
import com.onsemi.matrix.android.R;

public class AndroidSettingsProvider implements SettingsProvider {

    private final String DefaultUrl = "http://192.168.1.168";

    private Context context = null;

    public void setContext(Context c) {
        context = c;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String getUrl() {
        if (context == null) {
            throw new IllegalStateException("context is null");
        }

        String url = context.getSharedPreferences(context.getString(R.string.settings_file_key),
                Context.MODE_PRIVATE).getString(context.getString(R.string.saved_url), null);

        if (url == null || url == "") {
            return DefaultUrl;
        }

        return url;
    }

    public void setUrl(String url) {
        if (context == null) {
            throw new IllegalStateException("context is null");
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getString(R.string.settings_file_key), Context.MODE_PRIVATE).edit();

        editor.putString(context.getString(R.string.saved_url), url);

        editor.commit();
    }
}

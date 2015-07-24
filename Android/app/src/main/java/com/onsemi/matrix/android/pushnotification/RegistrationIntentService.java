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

package com.onsemi.matrix.android.pushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.example.backend.registration.Registration;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.onsemi.matrix.android.ui.AndroidSettingsProvider;

import java.io.IOException;
import java.net.URL;

public class RegistrationIntentService extends IntentService {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    private static AndroidSettingsProvider settingsProvider = null;

    public RegistrationIntentService() {
        super(TAG);
    }

    public static void setSettingsProvider(AndroidSettingsProvider aSettingsProvider) {
        settingsProvider = aSettingsProvider;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(settingsProvider.getProjectNumber(),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                sendRegistrationToServer(token);

                subscribeTopics(token);
            }
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "Google Cloud Messaging: Registration failed.\n" +
                    "Please check project number.", Toast.LENGTH_LONG).show();
        }
    }

    private void sendRegistrationToServer(String token) {
        try {
            URL url = new URL(settingsProvider.getPushServiceUrl());

            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(String.format("%s://%s/_ah/api/", url.getProtocol(), url.getAuthority()));

            Registration regService = builder.build();

            regService.register(token).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
}

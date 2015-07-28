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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import com.onsemi.matrix.android.testlogic.ExtendedHttpJUnitRunner;
import com.onsemi.matrix.android.testlogic.RunnerGroup;
import com.onsemi.matrix.android.testlogic.Status;
import com.onsemi.matrix.android.testlogic.Test;
import com.onsemi.matrix.android.testlogic.TestRunnersProvider;
import com.onsemi.matrix.android.testlogic.TestRunnersServer;
import com.onsemi.matrix.android.pushnotification.RegistrationIntentService;
import com.onsemi.matrix.api.RecordingTest;
import com.onsemi.matrix.api.Settings;
import com.onsemi.matrix.android.R;
import com.onsemi.matrix.api.AudioTest;
import com.onsemi.matrix.api.MaintenanceTest;
import com.onsemi.matrix.api.NetworkTest;
import com.onsemi.matrix.api.SystemTest;
import com.onsemi.matrix.api.UserTest;
import com.onsemi.matrix.api.VideoTest;


import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    public static List<RunnerGroup> testRunnerGroups = null;

    private AndroidSettingsProvider settingsProvider = null;

    private ProgressBar progressBar = null;
    private TabHost tabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.settingsProvider = new AndroidSettingsProvider(this);
            this.progressBar = (ProgressBar)findViewById(R.id.progressBar);

            Settings.setSettingsProvider(this.settingsProvider);

            if (testRunnerGroups == null) {
                testRunnerGroups = TestRunnersProvider.getTestRunners(
                        new Class<?>[]{AudioTest.class, VideoTest.class, MaintenanceTest.class,
                                SystemTest.class, NetworkTest.class, UserTest.class, RecordingTest.class});
            }

            this.initializeTabHost();

            Button runAllTestsButton = (Button)findViewById(R.id.runAllTestsButton);

            runAllTestsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TestRunnersServer.executeTests(testRunnerGroups,
                            MainActivity.this.settingsProvider.getIgnoredTests());
                }
            });

            RegistrationIntentService.setSettingsProvider(this.settingsProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.startService(new Intent(this, RegistrationIntentService.class));

        TestRunnersServer.addObserver(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        TestRunnersServer.removeObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            this.startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_clean) {
            this.cleanTests();
            return true;
        } else if (id == R.id.action_cancel) {
            TestRunnersServer.cancel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeTabHost() {
        if(testRunnerGroups == null) {
            return;
        }

        this.tabHost = (TabHost)findViewById(android.R.id.tabhost);

        this.tabHost.setup();
        this.tabHost.clearAllTabs();

        for (RunnerGroup testRunnerGroup : testRunnerGroups) {
            TabHost.TabSpec tabSpec = this.tabHost.newTabSpec(testRunnerGroup.getName());

            tabSpec.setIndicator(testRunnerGroup.getName());
            tabSpec.setContent(new TabContentFactory(this, testRunnerGroup));

            this.tabHost.addTab(tabSpec);
        }
    }

    private void cleanTests() {
        if(testRunnerGroups == null) {
            return;
        }

        for(RunnerGroup testRunnerGroup : testRunnerGroups) {
            for(ExtendedHttpJUnitRunner runner : testRunnerGroup.getRunners()) {
                for (Test test : runner.getTests()) {
                    test.setStatus(Status.NotRun);
                    test.setError(null);
                }
            }

            testRunnerGroup.notifyObservers();
        }
    }

    @Override
    public void update(Observable observable, final Object data) {
        if (this.progressBar == null) {
            return;
        }

        this.progressBar.post(new Runnable() {
            @Override
            public void run() {
                if (!((Boolean)data)) {
                    MainActivity.this.progressBar.setVisibility(View.GONE);
                } else {
                    MainActivity.this.progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

class TabContentFactory implements TabHost.TabContentFactory {
    private RunnerGroup runnerGroup;
    private Context context;

    public TabContentFactory(Context context, RunnerGroup runnerGroup) {
        this.runnerGroup = runnerGroup;
        this.context = context;
    }

    @Override
    public View createTabContent(String tag) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tabView = inflater.inflate(R.layout.tab_view, null);

        final ExpandableListView testsListView = (ExpandableListView)tabView.findViewById(R.id.testsListView);
        final ExpandableListAdapter listAdapter = new ExpandableListAdapter(context, runnerGroup.getRunners());

        testsListView.setAdapter(listAdapter);
        testsListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        runnerGroup.addObserver(new Observer(){
            @Override
            public void update(Observable observable, Object data) {
                testsListView.post(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        Button groupBtn = (Button)tabView.findViewById(R.id.groupBtn);

        groupBtn.setText(String.format("Click to run all '%s' tests", runnerGroup.getName()));
        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRunnersServer.executeTests(runnerGroup);
            }
        });

        groupBtn.setVisibility(runnerGroup.getRunners().size() > 1 ? View.VISIBLE : View.GONE);

        return tabView;
    }
}

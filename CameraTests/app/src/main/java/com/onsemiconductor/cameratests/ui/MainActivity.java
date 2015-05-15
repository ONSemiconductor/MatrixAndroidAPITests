package com.onsemiconductor.cameratests.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import com.onsemiconductor.cameratests.R;
import com.onsemiconductor.cameratests.ExtendedHttpJUnitRunner;
import com.onsemiconductor.cameratests.RunnerGroup;
import com.onsemiconductor.cameratests.Status;
import com.onsemiconductor.cameratests.Test;
import com.onsemiconductor.cameratests.TestRunnersProvider;
import com.onsemiconductor.cameratests.TestRunnersServer;
import com.onsemi.matrix.api.AudioTest;
import com.onsemi.matrix.api.AuthenticationTest;
import com.onsemi.matrix.api.MaintenanceTest;
import com.onsemi.matrix.api.NetworkTest;
import com.onsemi.matrix.api.Settings;
import com.onsemi.matrix.api.SystemTest;
import com.onsemi.matrix.api.UserTest;
import com.onsemi.matrix.api.VideoTest;


import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {
    public static List<RunnerGroup> testRunnerGroups = null;

    private ProgressBar progressBar = null;
    private TabHost tabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Settings.setContext(this);

        try {
            this.progressBar = (ProgressBar)findViewById(R.id.progressBar);

            this.tabHost = (TabHost)findViewById(android.R.id.tabhost);
            this.tabHost.setup();


            if (testRunnerGroups == null) {
                testRunnerGroups = TestRunnersProvider.getTestRunners(
                        AudioTest.class, VideoTest.class, MaintenanceTest.class,
                        SystemTest.class, NetworkTest.class, AuthenticationTest.class, UserTest.class);
            }

            for(RunnerGroup testRunnerGroup : testRunnerGroups) {
                TabHost.TabSpec tabSpec = this.tabHost.newTabSpec(testRunnerGroup.getName());

                tabSpec.setIndicator(testRunnerGroup.getName());
                tabSpec.setContent(new TabContentFactory(this, testRunnerGroup));

                this.tabHost.addTab(tabSpec);
            }

            TestRunnersServer.addObserver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            this.setSettings();
            return true;
        } else if (id == R.id.action_clean) {
            this.cleanTests();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void setSettings() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Settings");
        alert.setMessage("Enter IP:");

        final EditText edittext = new EditText(this);

        edittext.setText(Settings.getIP());

        alert.setView(edittext);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Settings.setIP(edittext.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", null);

        alert.show();
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

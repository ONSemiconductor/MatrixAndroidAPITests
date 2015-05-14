package com.onsemiconductor.cameratests.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onsemiconductor.cameratests.ExtendedHttpJUnitRunner;
import com.onsemiconductor.cameratests.TestRunnersServer;
import com.onsemiconductor.cameratests.R;
import com.onsemiconductor.cameratests.Test;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExtendedHttpJUnitRunner> runners;

    public ExpandableListAdapter(Context context, List<ExtendedHttpJUnitRunner> runners) {
        this.context = context;
        this.runners = runners;
    }

    @Override
    public int getGroupCount() {
        return runners.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return runners.get(groupPosition).getTests().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return runners.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return runners.get(groupPosition).getTests().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        Button subgroupBtn = (Button) convertView.findViewById(R.id.subgroupBtn);
        subgroupBtn.setText(String.format("Click to run all '%s' tests", runners.get(groupPosition).getName()));

        subgroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestRunnersServer.executeTests(runners.get(groupPosition), null);
            }
        });

        ExpandableListView listView = (ExpandableListView) viewGroup;
        listView.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        final Test test = runners.get(groupPosition).getTests().get(childPosition);

        TextView testNameTV = (TextView)convertView.findViewById(R.id.testNameTV);
        testNameTV.setText(test.getMethodName());

        TextView testStatusTV = (TextView)convertView.findViewById(R.id.testStatusTV);
        testStatusTV.setText(test.getStatus().toString());
        testStatusTV.setTextColor(StatusToColorConverter.convert(this.context, test.getStatus()));

        Button testStartBtn = (Button)convertView.findViewById(R.id.testStartBtn);
        testStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestRunnersServer.executeTests(runners.get(groupPosition), test);
            }
        });

        final LinearLayout testLogsPanel = (LinearLayout)convertView.findViewById(R.id.testLogsPanel);
        testLogsPanel.setVisibility(View.GONE);

        final TextView testLogsTV = (TextView)convertView.findViewById(R.id.testLogsTV);
        testLogsTV.setText(null);

        Button testLogsBtn = (Button)convertView.findViewById(R.id.testLogsBtn);
        testLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test.getError() != null) {
                    if (testLogsPanel.getVisibility() == View.VISIBLE) {
                        testLogsPanel.setVisibility(View.GONE);
                    } else {
                        testLogsTV.setText(test.getError());
                        testLogsPanel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

package com.applidium.headerlistviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;

public class DemoActivity extends Activity {

    public static final String TAG = "Demo";
    private int currentItemSection;
    private int currentItemRow;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();


        final HeaderListView list = new HeaderListView(new android.view.ContextThemeWrapper(this, R.style.MyListViewStyle));
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //initial test values
        currentItemSection = 0;
        currentItemRow = 3;

        list.setAdapter(new SectionAdapter() {

            @Override
            public int numberOfSections() {
                return 4;
            }

            @Override
            public int numberOfRows(int section) {
                return 35;
            }

            @Override
            public Object getRowItem(int section, int row) {
                return null;
            }

            @Override
            public boolean hasSectionHeaderView(int section) {
                return true;
            }

            @Override
            public View getRowView(int section, int row, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(getResources().getLayout(android.R.layout.simple_list_item_1), null);
//                    ((TextView) convertView).setTextColor(ContextCompat.getColor(DemoActivity.this, R.color.item_text));
//                    convertView.setBackgroundResource(R.drawable.item_bg);
//                    Drawable buttonDrawable = getResources().getDrawable(R.drawable.item_bg);
//                    buttonDrawable.mutate();
//                    convertView.setBackgroundDrawable(buttonDrawable);

                }
                ((TextView) convertView).setText("Section " + section + " Row " + row);
                if (section == currentItemSection && row == currentItemRow) {
                    Log.d(TAG, String.format("Checked row %d under section %d", row, section));
                    list.setItemChecked(section, row, true);
                }
                return convertView;
            }

            @Override
            public int getSectionHeaderViewTypeCount() {
                return 2;
            }

            @Override
            public int getSectionHeaderItemViewType(int section) {
                return section % 2;
            }

            @Override
            public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(getResources().getLayout(android.R.layout.simple_list_item_1), null);
                }
                ((TextView) convertView).setText("Header for section " + section);
                Context context = DemoActivity.this;
                switch (section) {
                    case 0:
                        convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_red_light));
                        break;
                    case 1:
                        convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_orange_light));
                        break;
                    case 2:
                        convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_green_light));
                        break;
                    case 3:
                        convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_blue_light));
                        break;
                }
                return convertView;
            }

            @Override
            public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
                super.onRowItemClick(parent, view, section, row, id);
                currentItemSection = section;
                currentItemRow = row;
                list.setItemChecked(section, row, true);
                Toast.makeText(DemoActivity.this, "Section " + section + " Row " + row, Toast.LENGTH_SHORT).show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DemoActivity.this, "Checked item position is " + list.getListView().getCheckedItemPosition(), Toast.LENGTH_LONG).show();
                    }
                }, 5000);

            }
        });
        setContentView(list);
    }
}

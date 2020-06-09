package com.example.kuro.bloodpressure;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kuro.bloodpressure.DataBase.DataProvider;
import com.example.kuro.bloodpressure.DataBase.DateBaseHelper;
import com.example.kuro.bloodpressure.DataBase.Information;
import com.example.kuro.bloodpressure.DataBase.ListHistoryDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserProfileHistory extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;

    ListView listView;
    ListHistoryDataAdapter listDataAdapter;
    private List<DataProvider> mPersonList;
    DateBaseHelper dateBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursorProfile,cursorHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_history);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("user_name");
        String height = bundle.getString("user_height");
        String weight = bundle.getString("user_weight");
        String age = bundle.getString("user_age");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle(name);

        TextView weight_text = (TextView) findViewById(R.id.weight_person);
        weight_text.setText(weight);

        TextView height_text = (TextView) findViewById(R.id.height_person);
        height_text.setText(height);

        TextView age_text = (TextView) findViewById(R.id.age_person);
        age_text.setText(age);

        listView = (ListView) findViewById(R.id.show_history);
        mPersonList = new ArrayList<>();
        dateBaseHelper = new DateBaseHelper(getApplicationContext());
        sqLiteDatabase = dateBaseHelper.getReadableDatabase();
        cursorHistory = dateBaseHelper.getUserHistory();
        cursorProfile = dateBaseHelper.getUserInformation();

        if (cursorHistory.moveToLast()) {
            do {
                String user_name, recent_bp, recent_hr, recent_timeStamp;
                user_name = name;
                int no_of_measurements;
                if (cursorHistory.getCount() >= 0) {
                    recent_bp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.BP));
                    recent_hr = cursorHistory.getString(cursorHistory.getColumnIndex(Information.HEARTRATE));
                    recent_timeStamp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.DATETIME));
                    no_of_measurements = cursorHistory.getCount();
                    DataProvider dataProvider = new DataProvider(user_name, no_of_measurements, recent_hr, recent_bp, recent_timeStamp);
                    mPersonList.add(dataProvider);
                }
            } while (cursorHistory.moveToPrevious());
        }

//        if (cursorProfile.moveToLast()) {
//            do {
//                String user_name,user_weight, user_height,recent_bp, user_age, recent_hr, recent_timeStamp;
//                int no_of_measurements;
//
//                if (cursorHistory.getCount() >= 0) {
//                    user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
//                    DataProvider dataProvider = new DataProvider(user_name, 0, "0", "0", "0");
//                    mPersonList.add(dataProvider);
//                } else {
//                    user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
//                    user_weight = cursorProfile.getString(cursorProfile.getColumnIndex(Information.WEIGHT));
//                    user_height = cursorProfile.getString(cursorProfile.getColumnIndex(Information.HEIGHT));
//                    user_age = cursorProfile.getString(cursorProfile.getColumnIndex(Information.AGE));
//                    recent_bp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.BP));
//                    recent_hr = cursorHistory.getString(cursorHistory.getColumnIndex(Information.HEARTRATE));
//                    recent_timeStamp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.DATETIME));
//                    no_of_measurements = cursorHistory.getCount();
//                    DataProvider dataProvider = new DataProvider(user_name, no_of_measurements, recent_hr, recent_bp, recent_timeStamp);
//                    mPersonList.add(dataProvider);
//                }
//            } while (cursorProfile.moveToPrevious());
//        }

        listDataAdapter = new ListHistoryDataAdapter(getApplicationContext(), R.layout.peson_history_row, mPersonList);
        listDataAdapter.notifyDataSetChanged();
        listView.setAdapter(listDataAdapter);
        sqLiteDatabase.close();
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, UserProfileHistory.class));
    }
}

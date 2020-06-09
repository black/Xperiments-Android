package com.example.kuro.bloodpressure.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kuro.bloodpressure.DataBase.DataProvider;
import com.example.kuro.bloodpressure.DataBase.DateBaseHelper;
import com.example.kuro.bloodpressure.DataBase.Information;
import com.example.kuro.bloodpressure.DataBase.ListDataAdapter;
import com.example.kuro.bloodpressure.R;
import com.example.kuro.bloodpressure.UserProfileHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment implements AdapterView.OnItemClickListener {

    //ListView listView;
    ListDataAdapter listDataAdapter;
    private List<DataProvider> mPersonList;
    DateBaseHelper dateBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursorProfile, cursorHistory;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.people_listview, container, false);

        return mview;
    }

    public void onStart() {
        Log.d("TAG", "START");
        ListView listView = (ListView) getActivity().findViewById(R.id.show_people);
        listView.setOnItemClickListener(this);
        super.onStart();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "ACTIVITY CREATED");

        ListView listView = (ListView) getActivity().findViewById(R.id.show_people);
        mPersonList = new ArrayList<>();
        dateBaseHelper = new DateBaseHelper(getContext());
        sqLiteDatabase = dateBaseHelper.getReadableDatabase();
        cursorProfile = dateBaseHelper.getUserInformation();
        cursorHistory = dateBaseHelper.getLastEntry();
        if (cursorProfile.moveToLast()) {
            do {
                String user_name, recent_bp, recent_hr, recent_timeStamp;
                int no_of_measurements;

                if (cursorHistory.getCount() <= 0) {
                    user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
                    DataProvider dataProvider = new DataProvider(user_name, 0, "0", "0", "0");
                    mPersonList.add(dataProvider);
                } else {
                    user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
                    recent_bp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.BP));
                    recent_hr = cursorHistory.getString(cursorHistory.getColumnIndex(Information.HEARTRATE));
                    recent_timeStamp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.DATETIME));
                    no_of_measurements = cursorHistory.getCount();
                    DataProvider dataProvider = new DataProvider(user_name, no_of_measurements, recent_hr, recent_bp, recent_timeStamp);
                    mPersonList.add(dataProvider);
                }
            } while (cursorProfile.moveToPrevious());
        }
        listDataAdapter = new ListDataAdapter(getContext(), R.layout.person_row, mPersonList);
        listDataAdapter.notifyDataSetChanged();
        listView.setAdapter(listDataAdapter);
        sqLiteDatabase.close();

    }


    public void updatePersonList() {
        if (sqLiteDatabase != null) {
            mPersonList.clear();
            ListView listView = (ListView) getActivity().findViewById(R.id.show_people);
            dateBaseHelper = new DateBaseHelper(getContext());
            sqLiteDatabase = dateBaseHelper.getReadableDatabase();
            cursorProfile = dateBaseHelper.getUserInformation();
            cursorHistory = dateBaseHelper.getLastEntry();
            if (cursorProfile.moveToLast()) {
                do {
                    String user_name, recent_bp, recent_hr, recent_timeStamp;
                    int no_of_measurements;

                    if (cursorHistory.getCount() <=0) {
                        user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
                        DataProvider dataProvider = new DataProvider(user_name, 0, "0", "0", "0");
                        mPersonList.add(dataProvider);
                    } else {
                        user_name = cursorProfile.getString(cursorProfile.getColumnIndex(Information.NAME));
                        recent_bp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.BP));
                        recent_hr = cursorHistory.getString(cursorHistory.getColumnIndex(Information.HEARTRATE));
                        recent_timeStamp = cursorHistory.getString(cursorHistory.getColumnIndex(Information.DATETIME));
                        no_of_measurements = cursorHistory.getCount();
                        DataProvider dataProvider = new DataProvider(user_name, no_of_measurements, recent_hr, recent_bp, recent_timeStamp);
                        mPersonList.add(dataProvider);
                    }
                } while (cursorProfile.moveToPrevious());
            }
            listDataAdapter = new ListDataAdapter(getContext(), R.layout.person_row, mPersonList);
            listDataAdapter.notifyDataSetChanged();
            listView.setAdapter(listDataAdapter);
            sqLiteDatabase.close();
        }
    }

    @Override
    public void onResume() {
        Log.d("TAG", "RESUME");
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileHistory.class);
                TextView clickedView = (TextView) v.findViewById(R.id.person_name);
                String name = (String) clickedView.getText();

                ListView listView = (ListView) getActivity().findViewById(R.id.show_people);
                String name1, weight, height, age;
                String pos = String.valueOf(listView.getCount()-position);

                Log.d("POSTION: ", pos);

                Cursor currentProfile = dateBaseHelper.getSpecificEntry(pos);

                if (currentProfile.moveToLast()) {
                    do {
                        name1 = currentProfile.getString(currentProfile.getColumnIndex(Information.NAME));
                        String userid = currentProfile.getString(currentProfile.getColumnIndex(Information.USERID));
                        Log.d("POSTION: ",userid);
                        weight = currentProfile.getString(currentProfile.getColumnIndex(Information.WEIGHT));
                        height = currentProfile.getString(currentProfile.getColumnIndex(Information.HEIGHT));
                        age = currentProfile.getString(currentProfile.getColumnIndex(Information.AGE));
                        intent.putExtra("user_name", name1);
                        intent.putExtra("user_weight", weight);
                        intent.putExtra("user_height", height);
                        intent.putExtra("user_age", age);
                        startActivity(intent);
                    } while (currentProfile.moveToPrevious());
                }



                Random r = new Random();
                int hr_r = r.nextInt(100 - 50 + 1) + 50;
                int bpmax = r.nextInt(180 - 150 + 1) + 150;
                int bpmin = r.nextInt(100 - 80 + 1) + 80;

                String hr = String.valueOf(hr_r);
                String bp = String.valueOf(bpmax) + "/" + String.valueOf(bpmin);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
                String time = sdf.format(new Date());
                long result = dateBaseHelper.addUserHistory(hr, bp, time);


            }
        });
    }

    @Override
    public void onPause() {
        Log.d("TAG", "PAUSE");
        super.onPause();
    }

}

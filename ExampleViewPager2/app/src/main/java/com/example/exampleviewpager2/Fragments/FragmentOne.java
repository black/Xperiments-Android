package com.example.exampleviewpager2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exampleviewpager2.R;

public class FragmentOne extends Fragment {
    private String current="";
    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.tv1);
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("CTX 1");
        menu.add(1, 11, 0,"MODIFY"); // 1 = item.getGroupId() , 11 = item.getItemId() , order , Modify = Title of Menu
        menu.add(1, 12, 1, "CONTENT"); // 1 = item.getGroupId() , 12 = item.getItemId() , order , Content = Title of Menu
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getGroupId() == 1) {
            switch (item.getItemId()) {
                case 11:
                    Toast.makeText(getContext(), "Frag 1", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 1", "First");
                    return true;
                case 12:
                    Toast.makeText(getContext(), "Frag 1", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 1", "Second");
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }else return false;
    }
}
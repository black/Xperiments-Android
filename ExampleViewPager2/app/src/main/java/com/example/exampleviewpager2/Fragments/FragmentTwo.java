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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exampleviewpager2.R;

public class FragmentTwo extends Fragment {
    private String current="";
    public FragmentTwo() {
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
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.tv2);
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("CTX 2");
        menu.add(2, 21, 0,"MODIFY");
        menu.add(2, 22, 0, "CONTENT");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getGroupId() == 2) {
            switch (item.getItemId()) {
                case 21:
                    Toast.makeText(getContext(), "Frag 2", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 2", "First");
                    return true;
                case 22:
                    Toast.makeText(getContext(), "Frag 2", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 2", "Second");
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }else return false;
    }
}
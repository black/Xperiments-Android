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

public class FragmentThree extends Fragment {
    private String current="";
    public FragmentThree() {
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
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.tv3);
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("CTX 3");
        menu.add(3, 31, 0,"MODIFY");
        menu.add(3, 32, 0, "CONTENT");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getGroupId() == 3) {
            switch (item.getItemId()) {
                case 31:
                    Toast.makeText(getContext(), "Frag 3", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 3", "First");
                    return true;
                case 32:
                    Toast.makeText(getContext(), "Frag 3", Toast.LENGTH_LONG).show();
                    Log.d("CTXMENU 3", "Second");
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }else return false;
    }
}
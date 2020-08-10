package com.example.keyboardwithdictionary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keyboardwithdictionary.KeyboardInterface.KeyboardRadar;
import com.example.keyboardwithdictionary.KeyboardInterface.Keys;
import com.example.keyboardwithdictionary.KeyboardInterface.OnRVItemClickListener;
import com.example.keyboardwithdictionary.KeyboardInterface.RVAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private List<Keys> keysList = new ArrayList<>();
    private String[] keyVal = {"+", "+", "+", "+", "+", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", "del",
            "Z", "X", "C", "V", "B", "N", "M", "space", "tts", "done",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private TextToSpeech engine;
    private GridView gridView;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private RelativeLayout viewHolder;
    private String msg;
    private String val = "";
    private List<String> wordList = new ArrayList<>();
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine = new TextToSpeech(this, this);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // On JellyBean & above, you can provide a shortcut and an explicit Locale
            UserDictionary.Words.addWord(this, "MadeUpWord", 10, "Mad", Locale.getDefault());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            UserDictionary.Words.addWord(this, "MadeUpWord", 10, UserDictionary.Words.LOCALE_TYPE_CURRENT);
        }*/

        viewHolder = findViewById(R.id.keyboardRadar);
//        gridView = findViewById(R.id.keyboardGrid);
        recyclerView = findViewById(R.id.keyboardGrid);
        for (int i = 0; i < keyVal.length; i++) {
            keysList.add(new Keys(keyVal[i]));
        }
//        keyAdapter = new KeyAdapter(this, keysList);
//        gridView.setAdapter(keyAdapter);
        rvAdapter = new RVAdapter(keysList,this);
        recyclerView.setAdapter(rvAdapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 10);
        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <5) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        message = findViewById(R.id.custommessage);
        rvAdapter.setOnRVItemClickListener(new OnRVItemClickListener() {
            @Override
            public void onClickListener(int pos) {
                switch (keysList.get(pos).getKey()) {
                    case "del":
                        val = val.length() > 0 ? removeLastChar(val) : "";
                        break;
                    case "tts":
                        PlayMessage(val);
                        break;
                    case "space":
                        val = val + " ";
                        break;
                    case "done":
                        val = "";
                        //prevFragment();
                        break;
                    default:
                        val = val + keysList.get(pos).getKey();
                        break;
                }
                message.setText(val);
            }
        });
        RadarControl(recyclerView, viewHolder);

    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void PlayMessage(String msg) {
        engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (engine != null) {
            engine.shutdown();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int results = engine.setLanguage(Locale.US);
            if (results == TextToSpeech.LANG_MISSING_DATA
                    || results == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Not supported", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void RadarControl(final RecyclerView gridView, final RelativeLayout viewHolder) {
        viewHolder.post(new Runnable() {
            @Override
            public void run() {
                KeyboardRadar keyboardRadar = new KeyboardRadar(getApplicationContext(), gridView, viewHolder);
                viewHolder.addView(keyboardRadar);
            }
        });
    }

}
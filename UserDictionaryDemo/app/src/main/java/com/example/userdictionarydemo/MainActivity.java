package com.example.userdictionarydemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    public static final String TAG="Topics";
    private List<Dictionary> dictionaries = new ArrayList<>();
    private ArrayAdapter<Dictionary> itemsAdapter;
    private EditText editText;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // On JellyBean & above, you can provide a shortcut and an explicit Locale
            UserDictionary.Words.addWord(this, "MadeUpWord", 10, "Mad", Locale.getDefault());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            UserDictionary.Words.addWord(this, "MadeUpWord", 10, UserDictionary.Words.LOCALE_TYPE_CURRENT);
        }*/
        editText = findViewById(R.id.new_word);
        lv = findViewById(R.id.word_list);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, dictionaries);
        lv.setAdapter(itemsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllDictionary();
    }

    private void insertDictionary(Dictionary dictionary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDictionary.Words.LOCALE, dictionary.getLocale());
        contentValues.put(UserDictionary.Words.WORD, dictionary.getWord());
        getContentResolver().insert(UserDictionary.Words.CONTENT_URI, contentValues);
    }

    private void getAllDictionary() {
        String[] projection = {UserDictionary.Words.LOCALE, UserDictionary.Words.WORD};
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI, projection, null, null);
        if (cursor != null) {
            int locale_id = cursor.getColumnIndex(UserDictionary.Words.LOCALE);
            int word_id = cursor.getColumnIndex(UserDictionary.Words.WORD);
            try {
                Log.d(TAG, word_id+"");
                while (!cursor.isAfterLast()) {
                    String word = cursor.getString(word_id);
                    String locale = cursor.getString(locale_id);
                    dictionaries.add(new Dictionary(word, locale));
                    itemsAdapter.notifyDataSetChanged();
                    Log.d(TAG,word);
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
    }

    public void addNewWord(View view) {
        String word = editText.getText().toString();
        insertDictionary(new Dictionary(word, "en"));
        getAllDictionary();
    }

}

/*
* https://www.programcreek.com/java-api-examples/?code=dslul%2Fopenboard%2Fopenboard-master%2Fapp%2Fsrc%2Fmain%2Fjava%2Forg%2Fdslul%2Fopenboard%2Finputmethod%2Flatin%2FUserBinaryDictionary.java#
*
*/
package com.example.sqlitedbexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlitedbexample.DataBasePackage.DBHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private boolean status;
    private EditText questionView;
    private TextView questionCount;
    private SQLiteDatabase database;
    private Cursor cursor;
    int l=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionView = findViewById(R.id.questionField);
        questionCount = findViewById(R.id.questionCount);
        disableEditText(questionView,false);

        DBHelper helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        cursor = database.rawQuery("SELECT QUESTION FROM QUESTIONS", null);
        cursor.moveToFirst();
        getItem();
    }

    public void editSave(View view) {
        status = !status;
        ((ImageView) findViewById(R.id.save_edit)).setImageResource(status ? R.drawable.ic_done_black_24dp : R.drawable.ic_mode_edit_black_24dp);
        disableEditText(questionView, status);
        /*  Log.d("Status", status + ""); */
        /*Update question*/
        if (!status && cursor!=null) {
            String question = questionView.getText().toString();
            updateQuestion(question);
        }
    }


    private void disableEditText(EditText editText,boolean status) {
        editText.setEnabled(status);
        editText.setBackgroundColor(status?Color.BLUE:Color.TRANSPARENT);
        editText.setSelection(editText.length());
        editText.requestFocus();
    }

    /*-- update question ---*/
    private void updateQuestion(String question){
        ContentValues values = new ContentValues();
        values.put("QUESTION",question);
        int pos = cursor.getPosition();
        Log.d("POSITION",""+pos);
        String whereClause = "_id ='" + (pos+1) + "'";
        database.update("QUESTIONS",values, whereClause,null);
        cursor = database.rawQuery("SELECT QUESTION FROM QUESTIONS", null);// cursor bydefault move to the first position
        Log.d("POSITION NEW",cursor.getPosition()+"");
        cursor.moveToFirst();
        cursor.moveToPosition(pos);
        getItem();
    }

    /*--yes---*/
    public void yesResponse(View view) {
        if(cursor.moveToNext()) getItem();
    }

    /*--no---*/
    public void noResponse(View view) {
        if(cursor.moveToNext()) getItem();
    }

    /*--skip---*/
    public void skipQuestion(View view) {
        if(cursor.moveToNext()) getItem();
    }

    /*--prev---*/
    public void prevQuestion(View view) {
        if(cursor.moveToPrevious() && cursor.getPosition()>=0) getItem();
    }

    private void getItem(){
        String pos = String.valueOf(cursor.getPosition()+1);
        String question = cursor.getString(cursor.getColumnIndex("QUESTION"));
        questionView.setText(question);
        questionCount.setText(String.format("Q%s:  ", pos));
    }

    public void deleteDatabase(View view) {
        this.deleteDatabase("mydb");
    }

}

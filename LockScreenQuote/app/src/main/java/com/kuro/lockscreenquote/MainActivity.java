package com.kuro.lockscreenquote;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Creating JSON Parser instance
//    String quote, author;
    private TextView quoteText;
    private TextView authText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quoteText = (TextView) findViewById(R.id.quote);
        authText = (TextView) findViewById(R.id.author);

        Button button = (Button) findViewById(R.id.getquote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONParser(quoteText, authText ).execute("http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1&callback=");
            }
        });

    }
}

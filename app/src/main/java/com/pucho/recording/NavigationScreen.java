package com.pucho.recording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationScreen extends AppCompatActivity {

    private Button speakbutton,listenbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_screen);

        //getting the ids

        speakbutton=findViewById(R.id.speakbutton);
        listenbutton=findViewById(R.id.listenbutton);

        //calling the methods
        speakbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavigationScreen.this,startActivity.class);
                intent.putExtra("flag","speak");
                startActivity(intent);
            }
        });

        //listen button
        listenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(NavigationScreen.this,startActivity.class);
                intent.putExtra("flag","listen");
                startActivity(intent);
            }
        });
    }
}

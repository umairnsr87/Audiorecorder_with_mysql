package com.pucho.recording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ListenActivity extends AppCompatActivity {

    private Button btnyes,btnno,btnplay;
    private TextView setteraudiotext;
    private String data_intent=null;
    private String textdata=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        //gtting the ids
        btnno=findViewById(R.id.btnno);
        btnyes=findViewById(R.id.btnyes);
        btnplay=findViewById(R.id.btnlisten);
        setteraudiotext=findViewById(R.id.setteraudiotextlisten);


        Intent intent=getIntent();
         data_intent= intent.getStringExtra("values");
        Log.i("data_intent",data_intent);

        ListenSetter listenSetter=new ListenSetter();
        textdata=listenSetter.setTextView(data_intent);
        setteraudiotext.setVisibility(View.VISIBLE);


        //playing the audio after fetching it from db
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListenActivity.this,"Audio is about to play",Toast.LENGTH_LONG).show();
                String path= Environment.getExternalStorageDirectory().getAbsolutePath();
                path+="/fetched_data.3gp";


                MediaPlayer mPlayer = new MediaPlayer();
                try {
                    listenSetter.retriveaudio();
                    mPlayer.setDataSource(path);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e("exception occured audio",e.getMessage());
                    e.printStackTrace();
                }finally {
                    //emptying the resources after 20seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPlayer.stop();
                            mPlayer.reset();
                            mPlayer.release();
                        }
                    }, 20000);

                }

            }
        });

        //setting the text data to setter text
        setteraudiotext.setText(textdata);





    }
}

package com.pucho.recording;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private Button startbtn, stopbtn, playbtn, stopplay;
    private MediaRecorder mRecorder;
    private Button dbbut;
    private TextView textView;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    Connection conn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbtn=findViewById(R.id.btnPlay);
        startbtn = (Button)findViewById(R.id.btnRecord);
        dbbut=findViewById(R.id.pushtodbbut);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

        textView=findViewById(R.id.setter);
        startbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN )
                {
                    if(CheckPermissions())
                    {
                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mRecorder.setOutputFile(mFileName);
                        try {
                            mRecorder.prepare();
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "prepare() failed");
                        }
                        mRecorder.start();
                        Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        RequestPermissions();
                    }

                }else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                    Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();



                }

                return false;
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String path=Environment.getExternalStorageDirectory().getAbsolutePath();
                    path+="/fetched_data.3gp";

                    textView.setText("fetching start");
                    retriveaudio();
                    textView.setText("fetching done");
                    mPlayer = new MediaPlayer();
                    mPlayer.setDataSource(path);
                    mPlayer.prepare();
                    mPlayer.start();
                        Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("audio tag pucho", e.getMessage());
                    }

                }
        });
//
        dbbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateaudio(mFileName);
                Log.d("length", mFileName);
            }});


}


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public void updateaudio(String path)
    {
        //Uri uri=Uri.fromFile(new File(mFileName));

        Log.d("uri is ",mFileName);

        try {
            String sql = "INSERT INTO  english(textdata, audio) values(?, ?)";
            PreparedStatement statement = null;
            try {
                conn=MyTask.getCon();
                statement = conn.prepareStatement(sql);
                statement.setString(1, "Tom");
               File file=new File(path);
                InputStream inputStream = new FileInputStream(file);
                Log.d("Input stream",inputStream.toString());
                statement.setBinaryStream(2,inputStream,file.length());
                statement.executeUpdate();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
            textView.setText("data inserted");
            textView.setVisibility(View.VISIBLE);


        }catch(Exception e)
        {
            Log.e("Exception occured",e.getMessage());
        }finally
        {
            try {
                conn.close();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public void retriveaudio() throws IOException {
        String path=Environment.getExternalStorageDirectory().getAbsolutePath();
        path+="/fetched_data.3gp";
        File file=new File(path);
        FileOutputStream outputStream=new FileOutputStream(file);
        try {
            String sql = "select * from english where textdata='"+"Tom"+"'";
            Statement statement = null;

            try {
                conn=MyTask.getCon();
                statement = conn.createStatement();
                ResultSet rs=statement.executeQuery(sql);
                while(rs.next())
                {

                    InputStream inputStream=rs.getBinaryStream("audio");
                    byte[] buffer=new byte[1024];
                    while(inputStream.read(buffer)>0)
                    {
                        outputStream.write(buffer);
                    }
                }

                } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
            textView.setText("data inserted");
            textView.setVisibility(View.VISIBLE);


        }catch(Exception e)
        {
            Log.e("Exception occured",e.getMessage());
        }finally
        {
            if(outputStream!=null)
            {
                outputStream.close();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
        }

    }




}


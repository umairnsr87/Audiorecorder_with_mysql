package com.pucho.recording;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.pucho.recording.MyTask;
public class updateAudio {

    Connection conn=null;
    int i=0;
    public int updateaudio(File f)
    {

        //Uri uri=Uri.fromFile(new File(f.getPath()));
        //Log.d("uri is ",uri.getPath());

        try {
            //String sql = "INSERT INTO  registration(username, email) values (?, ?)";
            String sql = "INSERT INTO  english(textdata, audio) values(?, ?)";
            PreparedStatement statement = null;
            try {
                conn=MyTask.getCon();
                statement = conn.prepareStatement(sql);
                statement.setString(1, "Tom");
//                File file=new File(uri.getPath());
                InputStream inputStream = new FileInputStream(f);
                statement.setBinaryStream(2,inputStream,f.length());
                //statement.set
                 i=statement.executeUpdate();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }


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

return i;
    }

}

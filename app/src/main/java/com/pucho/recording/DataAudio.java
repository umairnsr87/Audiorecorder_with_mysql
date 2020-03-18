package com.pucho.recording;

import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAudio extends Thread{
    Connection con=null;

    //setting the textview in audio record activity
    public String textFetcher(String s)
    {
        String data=s;

        String sql="select * from "+s+" where audio is null limit 1";
        Log.i("table name is ",sql);

        try{
            ResultSet rs=null;
            con=MyTask.getCon();
            Statement st=con.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next())
            {
                data=rs.getString(2);
            }
        }
        catch (Exception e)
        {
            Log.e("Data Audio error",e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return data;
    }

    //update the recorded audio by the user
    public void updateaudio(String path, String condition, String tablename) {
        try {
            String sql = "update "+tablename+" set audio = ? where textdata='"+condition+"'";
            PreparedStatement statement = null;
            con=MyTask.getCon();
            statement = con.prepareStatement(sql);
            File file=new File(path);
            InputStream inputStream = new FileInputStream(file);
            statement.setBinaryStream(1,inputStream,file.length());
            statement.executeUpdate();


        }catch(Exception e) {
            Log.e("Exception occured",e.getMessage());
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
        }

    }


}

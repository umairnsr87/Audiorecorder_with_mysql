package com.pucho.recording;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListenSetter {
    Connection con=null;

//setting the textview
    public String setTextView(String s)
    {
            String data=s;

            String sql="select * from "+s+" where flag='"+"no"+"' limit 1";
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


    //function to retrive audio from db to hear
    public void retriveaudio() throws IOException {
        String path= Environment.getExternalStorageDirectory().getAbsolutePath();
        path+="/fetched_data.3gp";
        File file=new File(path);
        FileOutputStream outputStream=new FileOutputStream(file);
        try {
            String sql = "select audio from English where flag='"+"no"+"' limit 1";
            Statement statement = null;
            con=MyTask.getCon();
            statement = con.createStatement();
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
                con.close();
            } catch (SQLException e) {
                Log.e("Exception occured",e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public void verifiedyes(String tablename,String texttomatch){
        //step1:making the connection
        try {
            con = MyTask.getCon();
            String sql = "update " + tablename + " set flag='" + "yes" + "',verified='" + "yes" + "' where textdata='" + texttomatch + "'";
            Statement statement = con.createStatement();
            statement.execute(sql);
        }catch (Exception e)
        {
            Log.i("verifiedyes",e.getMessage());

        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                Log.i("verifiedyes",e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void verifiedno(String tablename,String texttomatch){
        //step1:making the connection
        try {
            con = MyTask.getCon();
            String sql = "update " + tablename + " set flag='" + "yes" + "',verified='" + "no" + "' where textdata='" + texttomatch + "'";
            Statement statement = con.createStatement();
            statement.execute(sql);
        }catch (Exception e)
        {
            Log.i("verifiedno",e.getMessage());

        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                Log.i("verifiedno",e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

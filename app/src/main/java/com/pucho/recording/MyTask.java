package com.pucho.recording;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyTask {
    private static final String url = "jdbc:mysql://104.155.146.57:3306/test";
    private static final String user = "umairnsr87";
    private static final String password = "@@Codechef9@@";
    public static Connection con = null;

    @SuppressLint("NewApi")
    public static Connection getCon() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

            Log.i("connection tag", "connection sucessful" + con);

        } catch (SQLException | ClassNotFoundException e) {
            Log.e("error in connection tag", e.getMessage());
        }
        return con;

    }

}

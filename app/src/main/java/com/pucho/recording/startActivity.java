package com.pucho.recording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class startActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String str;
    private String flag=null;
    private Button navEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        navEnglish=findViewById(R.id.navEnglish);

        Intent intent=getIntent();;
        flag=intent.getStringExtra("flag");

        //going to english Activity
        navEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(str)
                {
                    case "English":
                    {

                        //checking whether data is coming from listen button or speak button
                        if(flag.equals("speak"))
                        {
                            //data coming from speak bitton
                        Intent i = new Intent(startActivity.this, MainActivity.class);
                        i.putExtra("values", str);
                        Log.i("Intent extra",str);
                        startActivity(i);
                        finish();
                        }else if(flag.equals("listen"))
                        {
                            //data coming from listen button
                            Intent i = new Intent(startActivity.this, ListenActivity.class);
                            i.putExtra("values", str);
                            Log.i("Intent extra",str);
                            startActivity(i);
                            finish();

                        }
                        break;
                    }
                    case "Hindi":
                    {
                        //TODO:implement hindi language stuff
                        Toast.makeText(startActivity.this,"Hindi Selected",Toast.LENGTH_SHORT).show();

                        break;
                    }
                    //todo implement rest of the languages
                    default:
                    {
                        Toast.makeText(startActivity.this,"Please select a language...",Toast.LENGTH_SHORT).show();
                    }


                }



            }
        });

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(startActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("--Select Language--");
        categories.add("English");
        categories.add("Bengali");
        categories.add("Hindi");
        categories.add("Gujrati");
        categories.add("Marathi");
        categories.add("Punjabi");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
//
//        switch(id):
//        {
//            case R.id.
//        }


        str=item;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}




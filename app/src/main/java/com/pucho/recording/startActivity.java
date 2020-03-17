package com.pucho.recording;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private Button navEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        navEnglish=findViewById(R.id.navEnglish);


        //going to english Activity
        navEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(str)
                {
                    case "English":
                    {
                        Intent i = new Intent(startActivity.this, MainActivity.class);
                        i.putExtra("values", str);
                        startActivity(i);
                        break;
                    }
                    case "Hindi":
                    {
                        Toast.makeText(startActivity.this,"Hindi Selected",Toast.LENGTH_SHORT).show();

                        break;
                    }
                    default:
                    {
                        Toast.makeText(startActivity.this,"Please select a language...",Toast.LENGTH_SHORT).show();
                    }


                }
                if(str.equals("English")) {

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



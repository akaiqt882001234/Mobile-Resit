package com.example.coursework_resit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

     EditText  _name, _dest, _date, _risk, _desc;
     String id, name, dest, date, risk, desc;
     Button update_button, delete_button;
    MyDbHelper DbHelper = new MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        _name = findViewById(R.id._name_update);
        _dest = findViewById(R.id._dest_update);
        _date = findViewById(R.id._date_update);
        _risk = findViewById(R.id._risk_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        // Set name action bar when click item
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(name);
        }
        // Update Function
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDbHelper dbHelper = new MyDbHelper(UpdateActivity.this);
                name = _name.getText().toString().trim();
                dest = _dest.getText().toString().trim();
                date = _date.getText().toString().trim();
                risk = _risk.getText().toString().trim();
                dbHelper.updateData(id, name, dest, date, risk);
                finish();
            }
        });

        //Delete Function
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkerDialog();
            }
        });
    }

    //Get item data when clicking at ItemView
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("dest")
                && getIntent().hasExtra("date") && getIntent().hasExtra("risk")){

            //Get Data From Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            dest = getIntent().getStringExtra("dest");
            date = getIntent().getStringExtra("date");
            risk = getIntent().getStringExtra("risk");
            //Setting Data
            _name.setText(name);
            _dest.setText(dest);
            _date.setText(date);
            _risk.setText(risk);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }

    }

    void checkerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name);
        builder.setMessage("Want to Delete " + name + "?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDbHelper dbHelper = new MyDbHelper(UpdateActivity.this);
                dbHelper.deleteOneItem(id);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }
}
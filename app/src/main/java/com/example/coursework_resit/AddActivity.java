package com.example.coursework_resit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText _name, _dest, _date, _risk, _desc;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        _name = findViewById(R.id._name);
        _dest = findViewById(R.id._dest);
        _date = findViewById(R.id._date);
        _risk = findViewById(R.id._risk);
        _desc = findViewById(R.id._desc);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDbHelper myDb = new MyDbHelper(AddActivity.this);

                //Check Validation
                if (TextUtils.isEmpty(_name.getText().toString())) {
                    _name.setError("Please enter Name of Trip!");
                } else if (TextUtils.isEmpty(_dest.getText().toString())) {
                    _dest.setError("Please enter Destination!");
                } else if (TextUtils.isEmpty(_date.getText().toString())) {
                    _date.setError("Please enter Date of Trip!");
                } else if (TextUtils.isEmpty(_risk.getText().toString())) {
                    _risk.setError("Please Type YES/NO");
                } else {
                    getInputs();

                    //Insert Value to Database
                    myDb.addTrip(_name.getText().toString().trim(),
                            _dest.getText().toString().trim(),
                            _date.getText().toString().trim(),
                            _risk.getText().toString().trim(),
                            _desc.getText().toString().trim());
                }
            }
        });
    }


    private void getInputs() {
        //Set String input Value
        String name = _name.getText().toString();
        String dest = _dest.getText().toString();
        String date = _date.getText().toString();
        String risk = _risk.getText().toString();
        String desc = _desc.getText().toString();

        displayNextAlert(name, dest, date, risk,desc);
    }

    private void displayNextAlert(String name, String dest, String date, String risk, String desc) {
        new AlertDialog.Builder(this).setTitle("Add New Trip")
                .setMessage(
                        "Trip name: " + name +
                        "\n Destination: " + dest +
                        "\n Date: " + date +
                        "\n Risk Assessment: " + risk +
                        "\n Description: " + desc
                )
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }
}
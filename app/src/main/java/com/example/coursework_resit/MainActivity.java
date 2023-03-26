package com.example.coursework_resit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_tripView;
    TextView no_trip;
    MyDbHelper dbHelper;
    ArrayList<String> _id, _name, _dest, _date, _risk;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.listView);
        add_button = findViewById(R.id.add_button);
        empty_tripView = findViewById(R.id.empty_tripView);
        no_trip = findViewById(R.id.no_trip);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDbHelper(MainActivity.this);
        _id = new ArrayList<>();
        _name = new ArrayList<>();
        _dest = new ArrayList<>();
        _date = new ArrayList<>();
        _risk = new ArrayList<>();


        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this, _id, _name, _dest, _date, _risk);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }

    }

    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllData();
        if( cursor.getCount()==0 ){
            empty_tripView.setVisibility(View.VISIBLE);
            no_trip.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                _id.add(cursor.getString(0));
                _name.add(cursor.getString(1));
                _dest.add(cursor.getString(2));
                _date.add(cursor.getString(3));
                _risk.add(cursor.getString(4));
            }
            empty_tripView.setVisibility(View.GONE);
            no_trip.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.config_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all_items){
            checkerDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    void checkerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Items");
        builder.setMessage("Want to Delete All Items?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDbHelper dbHelper = new MyDbHelper(MainActivity.this);
                dbHelper.deleteAllItem();
                //Refresh
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
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
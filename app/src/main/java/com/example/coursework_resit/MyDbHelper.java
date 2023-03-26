package com.example.coursework_resit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TripManagement_v2.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "my_trip";
    private static final String CL_ID = "_id";
    private static final String CL_NAME = "_name";
    private static final String CL_DEST = "_dest";
    private static final String CL_DATE = "_date";
    private static final String CL_RISK = "_risk";
    private static final String CL_DESC = "_desc";

    MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME,null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CL_NAME + " TEXT, " +
                CL_DEST + " TEXT, " +
                CL_DATE + " TEXT, " +
                CL_RISK + " TEXT, " +
                CL_DESC + " TEXT) ;";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addTrip(String name, String dest, String date, String risk,String desc){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CL_NAME, name);
        cv.put(CL_DEST, dest);
        cv.put(CL_DATE, date);
        cv.put(CL_RISK, risk);
        cv.put(CL_DESC, desc);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Added Trip Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Trip Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Update
    void updateData(String row_id, String name, String dest, String date, String risk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CL_NAME, name);
        cv.put(CL_DEST, dest);
        cv.put(CL_DATE, date);
        cv.put(CL_RISK, risk);
        long result = db.update(TABLE_NAME, cv,"_id= ?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed Updated!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete one item
    void deleteOneItem(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed Deleted!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Success Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete all item
    void deleteAllItem(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

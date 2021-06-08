package com.example.paytoll;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class paymentDataBaseHelper extends SQLiteOpenHelper  {

    private static final String DATABASE_NAME = "USER_RECORD";
    private static final String TABLE_NAME = "USER_Pay_DATA";
    private static final String COL_1 = "id";
    private static final String COL_2 = "orderid";
    private static final String COL_3 = "email";
    private static final String COL_4 = "amount";
    private static final String COL_5 = "status";


    public paymentDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ,  ORDERID , EMAIL TEXT , AMOUNT, STATUSS TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean getPayDetails(String orderid , String email , String amount, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , orderid);
        values.put(COL_3 , email);
        values.put(COL_4 , amount);
        values.put(COL_5, status);

        long result = db.insert(TABLE_NAME , null , values);
        if(result == -1)
            return false;
        else
            return true;
    }
}

package com.example.paytoll;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "USER_RECORD";
    public static final String TABLE_NAME = "USER_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , USERNAME TEXT , EMAIL TEXT , PASSWORD TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean registerUser(String username , String email , String password, String vehicleNumber){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , username);
        values.put(COL_3 , email);
        values.put(COL_4 , password);
        long result = db.insert(TABLE_NAME , null , values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUser(String email , String password){

        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = { COL_1 };
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String [] selectionargs = { email , password};
        Cursor cursor = db.query(TABLE_NAME , columns , selection ,selectionargs , null , null , null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }

    //updateInfor()
    public boolean updateUser(String username, String password, String email) {

        boolean result = true;
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_2, username);
            values.put(COL_4, password);
            values.put(COL_3,email);
            //update using user id
            String[] hello = {String.valueOf(email)};
            System.out.print("Hello string "+ hello);
            result = db.update(TABLE_NAME, values, COL_3 + "=? ", hello) > 0;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public String getUserName(String email) {
        System.out.println("Inside get User name");
        Cursor cursor=this.getReadableDatabase().query(TABLE_NAME, new String[]{COL_2}, "EMAIL=?", new String[]{email}, null, null, null);

        cursor.moveToFirst();
        String user = cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        System.out.print("Username is :"+user);
        return user;
    }
}

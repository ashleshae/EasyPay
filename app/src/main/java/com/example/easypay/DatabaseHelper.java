package com.example.easypay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "EasyPayDB";
    public static final String tableName = "User_Details";
    public static final String COL = "FullName";
    public static final String COL2 = "MobileNo";
    public static final String COL3 = "Email";
    public String currentUser="";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "EasyPayDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE User_Details(UserID DOUBLE(4) NOT NULL PRIMARY KEY, FullName TEXT, Email TEXT, MobileNo TEXT(10), Password TEXT)");
        MyDatabase.execSQL("INSERT INTO User_Details VALUES (1001,'Admin','adminName@gmail.com',9123456789,'admin@123')");
        MyDatabase.execSQL("CREATE TABLE Internet_Details(UserID DOUBLE(4) FOREIGN KEY(UserID) REFERENCES User_Details, FullName TEXT, Email TEXT, MobileNo TEXT(10), Password TEXT)");
        //MyDatabase.execSQL("CREATE FUNCTION GenerateID() RETURNS INTEGER(4) BEGIN DECLARE randomID INTEGER(4); SET randomID = LPAD(CAST(RAND() * 10000 AS UNSIGNED), 4, '0'); RETURN randomID; END;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("DROP TABLE IF EXISTS User_Details");
    }

    public Boolean insertData(String FullName, String Email, String MobileNo, String Password)
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Random random = new Random();

        int UserID = random.nextInt(9000)+1000;
        contentValues.put("UserID", UserID);
        contentValues.put("FullName", FullName);
        contentValues.put("Email", Email);
        contentValues.put("MobileNo", MobileNo);
        contentValues.put("Password", Password);

        long result = MyDatabase.insert("User_Details", null, contentValues);

        if(result == -1){ return false;
        } else { return true; }
    }

    public Boolean checkEmail(String email)
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Cursor cursor = MyDatabase.rawQuery("SELECT * FROM User_Details WHERE email=?", new String[]{email});
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } else { return false;}
    }

    public Boolean checkEmailPassword(String email, String password)
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM User_Details WHERE Email=? AND Password=? ", new String[] {email, password});
        if(cursor != null){
            return true;
        } else { return false; }

    }

    /*public String getCurrentUser(String email) throws SQLException{

        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM User_Details WHERE Email=? ", new String[] {email});
        return currentUser;
    }*/

    public String getUsername() throws SQLException {
        String FullName = "";
        Cursor cursor = this.getReadableDatabase().query(
                tableName, new String[] { COL },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                FullName = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return FullName;
    }

    public String getPhoneNo() throws SQLException {
        String PhoneNo="";
        Cursor cursor = this.getReadableDatabase().query(
                tableName, new String[] { COL2 },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PhoneNo = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return PhoneNo;
    }
    public String getEmail() throws SQLException {
        String Email="";
        Cursor cursor = this.getReadableDatabase().query(
                tableName, new String[] { COL3 },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Email = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return Email;
    }
}

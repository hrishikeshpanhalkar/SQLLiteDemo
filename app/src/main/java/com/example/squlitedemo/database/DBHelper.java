package com.example.squlitedemo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table UserDetails(username TEXT primary key, password TEXT, email TEXT, phone TEXT, role TEXT)");
        db.execSQL("create table PostDetails(postNo INTEGER primary key AUTOINCREMENT, post TEXT, date TEXT, username TEXT, approvedStatus BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists UserDetails");
        db.execSQL("drop table if exists PostDetails");
    }

    public boolean insertUserData(String username, String password, String email, String phone, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("role", role);
        long result = db.insert("UserDetails", null, contentValues);
        return result != -1;
    }

    public boolean insertPostData(String post, String username, String date, boolean approvedStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("post", post);
        contentValues.put("date", date);
        contentValues.put("username", username);
        contentValues.put("approvedStatus", approvedStatus);
        long result = db.insert("PostDetails", null, contentValues);
        return result != -1;
    }

    @SuppressLint("Recycle")
    public boolean updateUserData(String username, String password, String email, String phone, String role){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("role", role);
        Cursor cursor = db.rawQuery("Select * from UserDetails where username == ?", new String[]{username});
        if(cursor.getCount() > 0){
            long result = db.update("UserDetails", contentValues, "name=?", new String[]{username});
            return result != -1;
        }else {
            return false;
        }
    }

    @SuppressLint("Recycle")
    public boolean deleteUserData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Delete from UserDetails where username == ?", new String[]{username});
        if(cursor.getCount() > 0){
            long result = db.delete("UserDetails", "name=?", new String[]{username});
            return result != -1;
        }else {
            return false;
        }
    }

    @SuppressLint("Recycle")
    public boolean deletePostsData(int postNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from PostDetails where postNo == ?", new String[]{String.valueOf(postNo)});
        if(cursor.getCount() > 0){
            long result = db.delete("PostDetails", "postNo=?", new String[] {String.valueOf(postNo)});
            return result != -1;
        }else {
            return false;
        }
    }

    @SuppressLint("Recycle")
    public Cursor getUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from UserDetails", null);
    }

    @SuppressLint("Recycle")
    public Cursor getMyPostsData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from PostDetails where username == ? and approvedStatus == true",new String[]{username}, null);
    }

    @SuppressLint("Recycle")
    public Cursor getUnapprovedPosts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from PostDetails where approvedStatus == false", null);
    }
}

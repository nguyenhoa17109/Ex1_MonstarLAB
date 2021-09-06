package com.example.ex1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteSV extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sinhVien.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteSV(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE sv (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "year TEXT," +
                "phone TEXT UNIQUE," +
                "chuyennganh BOOLEAN," +
                "he TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

    }

    public long addSV(SinhVien s){
        ContentValues v = new ContentValues();
        v.put("name", s.getName());
        v.put("year", s.getDateOfBirth());
        v.put("phone", s.getSdt());
        v.put("chuyennganh", s.getMajor());
        v.put("he", s.getEdu());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("sv", null, v);
    }

    //get ve by id
    public SinhVien getSV(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sv", null,
                whereClause, whereArgs, null, null, null);
        if (cursor.moveToNext()) {
            int idsv = cursor.getInt(0);
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            String phone = cursor.getString(3);
            String chuyennganh = cursor.getString(4);
            String he = cursor.getString(5);
            cursor.close();
            return new SinhVien(name, year, phone, chuyennganh, he);
        }
        return null;
    }

    //update
    public int updateSV(SinhVien s) {
        ContentValues v = new ContentValues();
        v.put("name", s.getName());
        v.put("year", s.getDateOfBirth());
        v.put("phone", s.getSdt());
        v.put("chuyennganh", s.getMajor());
        v.put("he", s.getEdu());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(s.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("sv",
                v, whereClause, whereArgs);
    }

    //delete
    public int deleteSV(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("sv",
                whereClause, whereArgs);
    }

    public List<SinhVien> getStudentByName(String name) {
        List<SinhVien> list=new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" + name + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sv", null, whereClause, whereArgs, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            list.add(CSv(cursor));
        }
        cursor.close();
        return list;
    }

    public List<SinhVien> getAllAsName(){
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sv ORDER BY name";
        SQLiteDatabase r = getReadableDatabase();
        Cursor cursor = r.rawQuery(sql, null);
        while (cursor.moveToNext()){
            list.add(CSv(cursor));
        }
        cursor.close();
        return list;
    }

    public List<SinhVien> getAllAsYearOfBirth(){
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sv ORDER BY year";
        SQLiteDatabase r = getReadableDatabase();
        Cursor cursor = r.rawQuery(sql, null);
        while (cursor.moveToNext()){
            list.add(CSv(cursor));
        }
        cursor.close();
        return list;
    }

    public List<SinhVien> getAllAsPhoneNumber(){
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sv ORDER BY phone";
        SQLiteDatabase r = getReadableDatabase();
        Cursor cursor = r.rawQuery(sql, null);
        while (cursor.moveToNext()){
            list.add(CSv(cursor));
        }
        cursor.close();
        return list;
    }

    private SinhVien CSv(Cursor cursor){
        int idsv = cursor.getInt(0);
        String name = cursor.getString(1);
        String year = cursor.getString(2);
        String phone = cursor.getString(3);
        String chuyennganh = cursor.getString(4);
        String he = cursor.getString(5);
        return new SinhVien(idsv, name, year, phone, chuyennganh, he);
    }
}

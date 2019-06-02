package com.example.attendancemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "am";
    //Subjects - name,att,total
    //TimeTable - day,subname

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //"CREATE TABLE timetable(day TEXT, sub TEXT)"

        db.execSQL("CREATE TABLE IF NOT EXISTS subjects(name TEXT, att INT, total INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS timetable(id INTEGER PRIMARY KEY AUTOINCREMENT, day TEXT, sub TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS subjects");
        db.execSQL("DROP TABLE IF EXISTS timetable");
        // Create tables again
        onCreate(db);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS subjects");
        db.execSQL("DROP TABLE IF EXISTS timetable");
        // Create tables again
        onCreate(db);
    }

    void addSubject(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_NAME, value);
        values.put("name", name); // Subject Name
        values.put("att", 0);
        values.put("total", 0);
        db.insert("subjects", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    void addTimeTable(ttdata x) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("day", x.day);
        values.put("sub", x.sub);
        db.insert("timetable", null, values);
        db.close(); // Closing database connection
    }

    public List<sdata> getAllSubjects() {
        List<sdata> subjectList = new ArrayList<sdata>();
        // Select All Query
        String selectQuery = "SELECT  * FROM subjects";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sdata subject = new sdata();
                subject.name=cursor.getString(0);
                subject.att=Integer.parseInt(cursor.getString(1));
                subject.total=Integer.parseInt(cursor.getString(2));

                // Adding subject to list
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }

        // return subject list
        return subjectList;
    }

    public List<ttdata> getAllTimeTables() {
        List<ttdata> timetableList = new ArrayList<ttdata>();
        // Select All Query
        String selectQuery = "SELECT  * FROM timetable";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ttdata timetable = new ttdata();
                timetable.id=cursor.getInt(0);
                timetable.day=cursor.getString(1);
                timetable.sub=cursor.getString(2);

                // Adding timetable to list
                timetableList.add(timetable);
            } while (cursor.moveToNext());
        }

        // return timetable list
        return timetableList;
    }

    public List<ttdata> getDayTimeTables(String day) {
        List<ttdata> timetableList = new ArrayList<ttdata>();
        // Select All Query
        String selectQuery = "SELECT * FROM timetable WHERE day='"+day+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ttdata timetable = new ttdata();
                timetable.id=cursor.getInt(0);
                timetable.day=cursor.getString(1);
                timetable.sub=cursor.getString(2);
                timetableList.add(timetable);
            } while (cursor.moveToNext());
        }
        return timetableList;
    }
    public List<String> getSubsNotInDay(String day){
        List<String> str = new ArrayList<String>();
        String selectQuery = "SELECT name FROM subjects where name NOT IN(SELECT sub FROM timetable WHERE day='"+day+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                str.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return str;
    }

    public void update_att(String sub,int present){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM subjects WHERE name='"+sub+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int p = Integer.parseInt(cursor.getString(1));
            int t = Integer.parseInt(cursor.getString(2));
            if (present == 1) p = p + 1;
            t = t + 1;
            db.close();
            db = this.getWritableDatabase();
            String updateQuery = "UPDATE subjects SET att=" + Integer.toString(p) + " , total=" + Integer.toString(t) + " WHERE name='"+sub+"'";
            db.execSQL(updateQuery);
        }

    }

    public void set_att(String sub,int att,int total){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE subjects SET att=" + Integer.toString(att) + " , total=" + Integer.toString(total) + " WHERE name='"+sub+"'";
        db.execSQL(updateQuery);
    }

    public void removeTT(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String removeQuery = "DELETE FROM timetable WHERE id="+Integer.toString(id)+"";
        db.execSQL(removeQuery);
    }

    public int getSubVals(String sub,int p0t1) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery;
        if(p0t1==0){selectQuery= "SELECT att FROM subjects WHERE name='" + sub + "'";}
        else{selectQuery= "SELECT total FROM subjects WHERE name='" + sub + "'";}
        int x = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            x = Integer.parseInt(cursor.getString(0));
        }
        db.close();
        return x;
    }
    public float getOverallAtt(){
        int x=0,y=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT SUM(att) FROM subjects";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            x = Integer.parseInt(cursor.getString(0));
        }
        selectQuery = "SELECT SUM(total) FROM subjects";
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            y = Integer.parseInt(cursor.getString(0));
        }
        return (100*(float)x/y);
    }
}

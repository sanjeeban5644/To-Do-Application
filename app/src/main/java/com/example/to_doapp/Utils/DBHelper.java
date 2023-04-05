package com.example.to_doapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_doapp.Todomodel.todomodel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TODO_DB";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT CHECK (ID>=0), TASK TEXT, STATUS INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT CHECK (" + COL_1 + ">=0), " + COL_2 + " TEXT, " + COL_3 + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(todomodel model){
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,model.getTask());
        values.put(COL_3,0);
        db.insert(TABLE_NAME,null,values);
    }

    public void updateTask(int id,String task){
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,task);
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
    }


    public void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3,status);
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
    }



    public void delete(int id){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }


    public List<todomodel> getAllTask(){
        db=this.getWritableDatabase();
        Cursor cursor = null;
        List<todomodel> modellist = new ArrayList<>();


        db.beginTransaction();
        try{
            cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor!=null){
                int colIndexId = cursor.getColumnIndex(COL_1);
                int colIndexTask = cursor.getColumnIndex(COL_2);
                int colIndexStatus = cursor.getColumnIndex(COL_3);
                if(cursor.moveToFirst()){
                    do{
                        todomodel task = new todomodel();
                        task.setId(cursor.getInt(colIndexId));
                        task.setTask(cursor.getString(colIndexTask));
                        task.setStatus(cursor.getInt(colIndexStatus));
                        modellist.add(task);
                    }while(cursor.moveToNext());
                }
            }
        }finally{
            db.endTransaction();
            db.close();
        }
        return modellist;
    }
}

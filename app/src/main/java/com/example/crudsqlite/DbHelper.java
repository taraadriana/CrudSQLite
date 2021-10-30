package com.example.crudsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    Context mContext;

    public static final String database_name = "db_mahasiswa";
    public static final int VERSION = 1;

    public DbHelper(Context context){
        super(context, database_name, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String sql = "CREATE TABLE tb_mhs (nim integer primary key,"+" " +
                    "nama text, alamat text, usia integer, gender text);";

            db.execSQL(sql);

        }catch (Exception e){
            Log.e("error", "failed");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //menambah data
    public boolean insertData(int nim, String nama, String alamat,
                              int usia, String gender){
        try{
            String sql = "INSERT INTO tb_mhs VALUES ("+nim+",'"+nama+"'," +
                    "'"+alamat+"','"+usia+"','"+gender+"');";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            Toast.makeText(mContext,"Berhasil add data",
                    Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Cursor showData(){
        try{
            String sql = "SELECT * FROM tb_mhs";
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            return cursor;
        }catch (Exception e){
            return null;
        }
    }

    //edit data
    public boolean editData(String nimOld, String nimNew, String nama,
                            String alamat, int usia, String gender){
        try{
            String sql = "UPDATE tb_mhs SET " + "nim=" +nimNew+ ", " +
                    "nama= ' "+nama+"', "+ "alamat='"+alamat+"', "+
                    "usia='"+usia+"', "+ "gender='"+gender+"' " + "WHERE nim = "+nimOld+";";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            Toast.makeText(mContext,"Berhasil edit data", Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteData(String nimOld){
        try{
            String sql = "DELETE FROM tb_mhs WHERE nim = "+nimOld+";";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            Toast.makeText(mContext,"Berhasil hapus data",
                    Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

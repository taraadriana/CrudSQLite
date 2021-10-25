package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowBio extends AppCompatActivity {
    protected Cursor cursor;
    DbHelper dbHelper;
    TextView txNim, txName, txAlamat, txUsia, txGender;
    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bio);

        txNim = findViewById(R.id.shNim);
        txName = findViewById(R.id.shName);
        txAlamat = findViewById(R.id.shAlamat);
        txUsia = findViewById(R.id.shUsia);
        txGender = findViewById(R.id.shGender);
        btnKembali = findViewById(R.id.btnShowKembali);

        DbHelper dbHelper = new DbHelper(ShowBio.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_mhs WHERE nim = '" +
                getIntent().getStringExtra("MainNim") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            txNim.setText(cursor.getString(0).toString());
            txName.setText(cursor.getString(1).toString());
            txAlamat.setText(cursor.getString(2).toString());
            txUsia.setText(cursor.getString(3).toString());
            txGender.setText(cursor.getString(4).toString());
        }
    }

    public void showQuit(View view){
        Intent intent = new Intent(ShowBio.this, MainActivity.class);
        startActivity(intent);
        ShowBio.this.finish();
    }
}
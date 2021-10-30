package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBio extends AppCompatActivity {
    Cursor cursor;

    EditText editNim, editName, editAlamat, editUsia, editGender;
    Button btnEdit, btnQuit;
    String oldNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);

        editNim = findViewById(R.id.editNewNim);
        editName = findViewById(R.id.editNewName);
        editAlamat = findViewById(R.id.editNewAlamat);
        editUsia = findViewById(R.id.editNewUsia);
        editGender = findViewById(R.id.editNewGender);
        btnEdit = findViewById(R.id.btnEdit);
        btnQuit = findViewById(R.id.btnEditKembali);

        DbHelper dbHelper = new DbHelper(EditBio.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_mhs WHERE nim = '" +
                getIntent().getStringExtra("MainNim") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            editNim.setText(cursor.getString(0).toString());
            editName.setText(cursor.getString(1).toString());
            editAlamat.setText(cursor.getString(2).toString());
            editUsia.setText(cursor.getString(3).toString());
            editGender.setText(cursor.getString(4).toString());
            oldNim = cursor.getString(0);
        }
    }

    public void editData(View view){
        try{
            boolean result = MainActivity.dbHelper.editData(
                    oldNim,
                    editNim.getText().toString().trim(),
                    editName.getText().toString().trim(),
                    editAlamat.getText().toString().trim(),
                    Integer.parseInt(editUsia.getText().toString()),
                    editGender.getText().toString().trim()
            );

        } catch (Exception err){
            err.printStackTrace();
            Toast.makeText(getApplicationContext(),"isi semua data",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void editQuit(View view){
        Intent intent = new Intent(EditBio.this, MainActivity.class);
        startActivity(intent);
        EditBio.this.finish();
    }
}
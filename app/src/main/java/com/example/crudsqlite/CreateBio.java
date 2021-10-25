package com.example.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateBio extends AppCompatActivity {
    EditText newNim, newName, newAlamat, newUsia, newGender;
    Button btnCreate, btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bio);

        newNim = findViewById(R.id.editTextNim);
        newName = findViewById(R.id.editTextName);
        newAlamat = findViewById(R.id.editTextAlamat);
        newUsia = findViewById(R.id.editTextUsia);
        newGender = findViewById(R.id.editTextGender);
        btnCreate = findViewById(R.id.btnCreate);
        btnKembali = findViewById(R.id.btnCreateKembali);
    }

    public void createData(View view){
        String ckNim = newNim.getText().toString();
        String ckName = newName.getText().toString();
        String ckAlamat = newAlamat.getText().toString();
        String ckUsia = newUsia.getText().toString();
        String ckGender = newGender.getText().toString();

        boolean check = validasi(ckNim, ckName, ckAlamat, ckUsia, ckGender);
        if (!check){
            Toast.makeText(getApplicationContext(),"isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            boolean result = MainActivity.dbHelper.insertData(
                    Integer.parseInt(newNim.getText().toString().trim()),
                    ckName.trim(),
                    ckAlamat.trim(),
                    Integer.parseInt(newUsia.getText().toString().trim()),
                    ckGender.trim());
            if (result){
                Intent intent = new Intent(CreateBio.this, MainActivity.class);
                startActivity(intent);
                CreateBio.this.finish();
            }
        }
    }

    public void kembali(View view){
        Intent intent = new Intent(CreateBio.this, MainActivity.class);
        startActivity(intent);
        CreateBio.this.finish();
    }

    public boolean validasi(String ckNim, String ckName, String ckAlamat, String ckUsia,
                                String ckGender) {
        if (ckNim.length() == 0) {
            newNim.requestFocus();
            newNim.setError("Nim tidak boleh kosong!");
            return false;
        } else if (ckName.length() == 0) {
            newName.requestFocus();
            newName.setError("Nama tidak boleh kosong!");
            return false;
        } else if (ckAlamat.length() == 0) {
            newAlamat.requestFocus();
            newAlamat.setError("Alamat tidak boleh kosong!");
            return false;
        } else if (ckUsia.length() == 0) {
            newUsia.requestFocus();
            newUsia.setError("Usia tidak boleh kosong!");
            return false;
        } else if (ckGender.length() == 0) {
            newGender.requestFocus();
            newGender.setError("Jenis kelamin tidak boleh kosong!");
            return false;
        }else{
            return true;
        }
    }
}
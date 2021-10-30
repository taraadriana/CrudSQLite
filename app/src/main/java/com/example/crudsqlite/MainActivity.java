package com.example.crudsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG ="MainActivity";
    static DbHelper dbHelper;
    ArrayList<String> arrayListName;
    ArrayList<Integer> arrayListNim;
    ListView listView;
    Button btnAdd;
    ArrayAdapter<String> arrayAdapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(MainActivity.this);
        arrayListName = new ArrayList<>();
        arrayListNim = new ArrayList<>();
        btnAdd = findViewById(R.id.btnNewList);
        listView = findViewById(R.id.listData);

        builder = new AlertDialog.Builder(this);

        Cursor cursor = dbHelper.showData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                arrayListNim.add(Integer.parseInt(cursor.getString(0)));
                arrayListName.add(cursor.getString(1));
            }
        }
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrayListName);
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);
    }

    public void tambahData(View view) {
        Intent intent = new Intent(this, CreateBio.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int index = info.position;
        menu.setHeaderTitle("pilih opsi");
        menu.add(index, v.getId(), 0, "view");
        menu.add(index, v.getId(), 0, "edit");
        menu.add(index, v.getId(), 0, "delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "view") {
            Intent intent = new Intent(MainActivity.this, ShowBio.class);
            intent.putExtra("MainNim", Integer.toString(arrayListNim.get(item.getGroupId())));
            startActivity(intent);
        } else if (item.getTitle() == "edit") {
            Intent intent = new Intent(MainActivity.this, EditBio.class);
            intent.putExtra("MainNim", Integer.toString(arrayListNim.get(item.getGroupId())));
            startActivity(intent);

        } else if (item.getTitle() == "delete") {
            builder.setMessage("do you want to delete this data?")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dbHelper.deleteData(Integer.toString(arrayListNim.get(item.getGroupId())));
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Delete Data");
            alertDialog.show();
        } else {
            return false;
        }
        return super.onContextItemSelected(item);
    }
}
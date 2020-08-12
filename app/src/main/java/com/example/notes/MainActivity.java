package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static ListView listView;
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes" , Context.MODE_PRIVATE);

        HashSet <String> set = (HashSet<String>) sharedPreferences.getStringSet("notes" , null);

        if(set == null) {
        notes.add("example note . .");
        }
        else {

             notes = new ArrayList(set);

        }
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext() , NotesActivity.class);
                intent.putExtra("noteId" , i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("are you sure!")
                        .setMessage("you want to delete the note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<>(notes);
                                sharedPreferences.edit().putString("notes" , String.valueOf(set)).apply();


                            }
                        })
                        .setNegativeButton("no" , null)
                        .show();


                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_notes_menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.add_notes:

                Intent intent = new Intent(getApplicationContext() ,NotesActivity.class);
                startActivity(intent);

                return true;
        }
        return false;

    }
}

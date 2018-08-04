package com.example.dell_i5.storage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
     static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        listView.setBackgroundColor(Color.WHITE);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("nothing",MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            arrayList.add("Example Note");
        }else{
            arrayList=new ArrayList<>(set);
        }
        
         arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                arrayAdapter.remove(String.valueOf(position));
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });


        listView.setOnItemClickListener( new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent i = new Intent(MainActivity.this,AddItemActivity.class);

                i.putExtra("data",arg2);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemtodelete=i;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you Sure?")
                        .setMessage("Do you want to delete this notes?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                             arrayList.remove(itemtodelete);
                             arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("nothing",MODE_PRIVATE);
                                HashSet<String> set=new HashSet<>(MainActivity.arrayList);
                                sharedPreferences.edit().remove("notes").apply();
                            }
                        })

                        .setNegativeButton("No",null).show();



                return true;
            }
        });
}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addItems:
                Intent intent=new Intent(this,AddItemActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

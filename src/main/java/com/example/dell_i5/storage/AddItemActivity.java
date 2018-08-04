package com.example.dell_i5.storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

public class AddItemActivity extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EditText editText=findViewById(R.id.editText);
        Intent intent=getIntent();
         noteId=intent.getIntExtra("data",-1);

        if(noteId!=-1) {
            editText.setText(MainActivity.arrayList.get(noteId));
        }else{
            MainActivity.arrayList.add("");
            noteId=MainActivity.arrayList.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.arrayList.set(noteId,String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("nothing",MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(MainActivity.arrayList);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

package com.example.to_doapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.to_doapp.Adapter.ToDoAdapter;
import com.example.to_doapp.Todomodel.todomodel;
import com.example.to_doapp.Utils.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogueCloseLIstener{


    private RecyclerView rview;
    private FloatingActionButton fab;
    private DBHelper dbHelper;
    private List<todomodel> list;
    private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rview = findViewById(R.id.rview);
        fab = findViewById(R.id.fab_button);
        dbHelper = new DBHelper(MainActivity.this);
        list = new ArrayList<>();
        adapter = new ToDoAdapter(dbHelper,MainActivity.this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public void onDialogueClose(DialogInterface di) {

    }
}
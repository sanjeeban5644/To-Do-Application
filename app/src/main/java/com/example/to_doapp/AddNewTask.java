package com.example.to_doapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_doapp.Todomodel.todomodel;
import com.example.to_doapp.Utils.DBHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    private static final String TAG = "AddNewTask";

    private EditText mytext;
    private Button save;

    private DBHelper mydb;


    public static AddNewTask newInstanc(){
        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_tasks,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mytext = view.findViewById(R.id.newtask);
        save = view.findViewById(R.id.savetask);


        mydb = new DBHelper(getActivity());

        boolean isUpdate = false;


        Bundle bundle = getArguments();

        if(bundle!=null){
            isUpdate=true;
            String task = bundle.getString("task");
            mytext.setText(task);

            if(task.length()>0){
                save.setEnabled(false);
            }
        }

        mytext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(save.toString().equals("")){
                    save.setEnabled(false);
                    save.setBackgroundColor(getResources().getColor(R.color.light_green));
                }else{
                    save.setEnabled(true);
                    save.setBackgroundColor(getResources().getColor(R.color.teal_200));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mytext.getText().toString();

                if(finalIsUpdate){
                    mydb.updateTask(bundle.getInt("id"),text);

                }else{
                    todomodel item = new todomodel();
                    item.setTask(text);
                    item.setStatus(0);
                    mydb.insertTask(item);
                }
                dismiss();
            }
        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogueCloseLIstener){
            ((OnDialogueCloseLIstener)activity).onDialogueClose(dialog);
        }
    }
}

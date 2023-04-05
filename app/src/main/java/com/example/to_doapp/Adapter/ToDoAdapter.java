package com.example.to_doapp.Adapter;

import android.content.Context;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.MainActivity;
import com.example.to_doapp.R;
import com.example.to_doapp.Todomodel.todomodel;
import com.example.to_doapp.Utils.DBHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<todomodel> clist;
    private MainActivity activity;
    private DBHelper mydb;

    public ToDoAdapter(DBHelper mydb,MainActivity activity){
        this.activity=activity;
        this.mydb=mydb;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final todomodel item = clist.get(position);
        holder.checkbox.setText(item.getTask());
        holder.checkbox.setChecked(tobool(item.getStatus()));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mydb.updateStatus(item.getId(),1);
                }else{
                    mydb.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List <todomodel> clist){
        this.clist=clist;
        notifyDataSetChanged();
    }





    private boolean tobool(int num){
        return num!=0;
    }




    @Override
    public int getItemCount() {
        return clist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkbox=itemView.findViewById(R.id.checkbox_t);




        }
    }
}

package com.example.coursework_resit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList _id, _name, _dest, _date, _risk;
    Activity activity;
    int position;
    CustomAdapter(Activity activity,Context context,ArrayList<String> _id,ArrayList<String> _name,ArrayList<String> _dest,ArrayList<String> _date,ArrayList<String> _risk){

        this.activity = activity;
        this.context = context;
        this._id = _id;
        this._name = _name;
        this._dest = _dest;
        this._date = _date;
        this._risk = _risk;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.trip_id_text.setText(String.valueOf(_id.get(position)));
        holder.trip_name_text.setText(String.valueOf(_name.get(position)));
        holder.trip_dest_text.setText(String.valueOf(_dest.get(position)));
        holder.trip_date_text.setText(String.valueOf(_date.get(position)));
        holder.trip_risk_text.setText(String.valueOf(_risk.get(position)));

        //Update
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id",String.valueOf(_id.get(position)));
                intent.putExtra("name",String.valueOf(_name.get(position)));
                intent.putExtra("dest",String.valueOf(_dest.get(position)));
                intent.putExtra("date",String.valueOf(_date.get(position)));
                intent.putExtra("risk",String.valueOf(_risk.get(position)));
                /*context.startActivity(intent);*/
                activity.startActivityForResult(intent,1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_name_text, trip_dest_text, trip_date_text, trip_risk_text, trip_id_text;
        LinearLayout mainLayout;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_text = itemView.findViewById(R.id.trip_id_text);
            trip_name_text = itemView.findViewById(R.id.trip_name_text);
            trip_dest_text = itemView.findViewById(R.id.trip_dest_text);
            trip_date_text = itemView.findViewById(R.id.trip_date_text);
            trip_risk_text = itemView.findViewById(R.id.trip_risk_text);

            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}

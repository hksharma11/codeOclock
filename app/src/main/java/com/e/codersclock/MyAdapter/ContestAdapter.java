package com.e.codersclock.MyAdapter;


import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.e.codersclock.Contest_Details;
import com.e.codersclock.Model.Contest;
import com.e.codersclock.R;


import java.util.ArrayList;


public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestHolder> {


    private Context context;
    ArrayList<Contest> data;

    public ContestAdapter(Context context, ArrayList<Contest> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ContestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contest,parent,false);
        ContestHolder viewholder= new ContestHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContestHolder holder, final int position) {


    holder.contest_event.setText(data.get(position).getEvent());
    holder.contest_name.setText(data.get(position).getName());
    holder.contest_time.setText(data.get(position).getTime());
    holder.contest_date.setText(data.get(position).getDate());

    if(data.get(position).getName().equals("codechef"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.codechef));
    }else if(data.get(position).getName().equals("codeforces"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.codeforces));
    }else if(data.get(position).getName().equals("hackerearth"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.hackerearth));
    }else if(data.get(position).getName().equals("topcoder"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.topcoder));
    }else if(data.get(position).getName().equals("atcoder"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.atcoder));
    }else if(data.get(position).getName().equals("leetcode"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.leetcode));
    }else if(data.get(position).getName().equals("codingcompetitions"))
    {
        holder.contest_logo.setBackground(ContextCompat.getDrawable(context, R.drawable.google));
    }


    holder.contestview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, Contest_Details.class);
            intent.putExtra("name",data.get(position).getName());
            intent.putExtra("event",data.get(position).getEvent());
            intent.putExtra("href",data.get(position).getHref());
            intent.putExtra("end_sortingID",data.get(position).getEnd_sortingID());
            intent.putExtra("start_sortingID",data.get(position).getStart_sortingID());
            context.startActivity(intent);
        }
    });










    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ContestHolder extends RecyclerView.ViewHolder{


        TextView contest_name,contest_event,contest_time,contest_date;
        View contest_logo;

        RelativeLayout contestview;

        public ContestHolder(@NonNull View itemView) {
            super(itemView);

            contest_name=itemView.findViewById(R.id.contest_name);
            contest_event=itemView.findViewById(R.id.contest_event);
            contest_time=itemView.findViewById(R.id.contest_time);
            contest_date=itemView.findViewById(R.id.contest_date);

            contest_logo=itemView.findViewById(R.id.contest_logo);

            contestview=itemView.findViewById(R.id.relative_contest);





        }
    }


}

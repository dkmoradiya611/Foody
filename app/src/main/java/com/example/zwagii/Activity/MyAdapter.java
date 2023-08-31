package com.example.zwagii.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private ArrayList<DataClass> dataList;
    private Context context;
    public MyAdapter(Context context, ArrayList<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);
        String pr = String.valueOf(dataList.get(position).getPrice());
        holder.recyclerCaption.setText(dataList.get(position).getCaption());
        holder.recyclerPrice.setText(pr);
        String sc = String.valueOf(dataList.get(position).getScore());
        holder.recyclerScore.setText(sc);
//        holder.recyclerPrice.setText(dataList.get(position).getPrice());

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchdatalist(ArrayList<DataClass> searchList){
        dataList=searchList;
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption,recyclerPrice,recyclerScore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
            recyclerPrice = itemView.findViewById(R.id.recyclerPrice1);
            recyclerScore = itemView.findViewById(R.id.totalEachItem);



        }
    }
}
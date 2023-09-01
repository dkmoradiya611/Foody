package com.example.zwagii.Activity;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    String name;


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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);

        holder.recyclerCaption.setText(dataList.get(position).getTitle());
        holder.recyclerPrice.setText(dataList.get(position).getPrice());
        holder.recyclerScore.setText(dataList.get(position).getScore());
//        holder.recyclerPrice.setText(dataList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object",dataList.get(holder.getAdapterPosition()));

                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.recyclerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String ur = object.getImageURL().toString();
                String ur = dataList.get(position).getImageURL().toString();
                // Convert string URL to Uri
                Uri actualImageUrl = Uri.parse(ur);


//                String ab = object.getTitle().toString();
                String ab = dataList.get(position).getTitle().toString();
//                String pr = object.getPrice();
                String pr = dataList.get(position).getPrice().toString();
//                String sc = object.getScore();
                String sc = dataList.get(position).getScore().toString();

                name = "DK";
//                name = getIntent().getStringExtra("un");





                // Get reference to the new subfolder where you want to store the actual URL
                DatabaseReference newSubfolderRef = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("imageURL");
                DatabaseReference newSubfolderRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("title");
                DatabaseReference newSubfolderRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("price");
                DatabaseReference newSubfolderRef3 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab).child("score");

                // Store the actual URL in the new subfolder
                newSubfolderRef.setValue(actualImageUrl.toString());
                newSubfolderRef1.setValue(ab);
                newSubfolderRef2.setValue(pr);
                newSubfolderRef3.setValue(sc);
            }
        });



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
        Button recyclerBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
            recyclerPrice = itemView.findViewById(R.id.recyclerPrice1);
            recyclerScore = itemView.findViewById(R.id.totalEachItem);
            recyclerBtn = itemView.findViewById(R.id.btncart_add);





        }
    }
}














//---------------------------------------------------------------------------------------------



//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
//    private ArrayList<DataClass> dataList;
//    private Context context;
//    public MyAdapter(Context context, ArrayList<DataClass> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
//        return new MyViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);
//        String pr = String.valueOf(dataList.get(position).getPrice());
//        holder.recyclerCaption.setText(dataList.get(position).getCaption());
//        holder.recyclerPrice.setText(pr);
//        String sc = String.valueOf(dataList.get(position).getScore());
//        holder.recyclerScore.setText(sc);
////        holder.recyclerPrice.setText(dataList.get(position).getPrice());
//
//    }
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    public void searchdatalist(ArrayList<DataClass> searchList){
//        dataList=searchList;
//        notifyDataSetChanged();
//    }
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView recyclerImage;
//        TextView recyclerCaption,recyclerPrice,recyclerScore;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            recyclerImage = itemView.findViewById(R.id.recyclerImage);
//            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
//            recyclerPrice = itemView.findViewById(R.id.recyclerPrice1);
//            recyclerScore = itemView.findViewById(R.id.totalEachItem);
//
//
//
//        }
//    }
//}
package com.example.zwagii.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    private ArrayList<DataClass> dataList;
    private Context context;
    public int a = 1;


    public CartAdapter() {
    }

    public CartAdapter(Context context, ArrayList<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new CartAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recyclerImage);

        holder.recyclerCaption.setText(dataList.get(position).getTitle());
        holder.recyclerPrice.setText(String.valueOf(dataList.get(position).getPrice()));
        holder.recyclerScore.setText(String.valueOf(dataList.get(position).getPrice()));



        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            public int numberOrder = 1;

            @Override
            public void onClick(View v) {

                numberOrder  += 1;
//                holder.recyclerPrice.setText(String.valueOf(dataList.get(position).getPrice()));
                int ps = Integer.parseInt(dataList.get(position).getPrice().toString());



                holder.numberItemCarttxt.setText("" + numberOrder);
//            Double b = Double.valueOf(object.getPrice());


                holder.totaltxt.setText("$"+ Math.round(numberOrder * ps));
                a +=1;

            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            int numberOrder = Integer.parseInt(holder.numberItemCarttxt.getText().toString());

            @Override
            public void onClick(View v) {
                a  -= 1;
//                holder.recyclerPrice.setText(String.valueOf(dataList.get(position).getPrice()));
                int ps = Integer.parseInt(dataList.get(position).getPrice().toString());



                holder.numberItemCarttxt.setText("" + a);
//            Double b = Double.valueOf(object.getPrice());


                holder.totaltxt.setText("$"+ Math.round(a * ps));

            }
        });

//
//        public void plusNumberFood(ArrayList<FoodDomain> listfood,int position, ChnageNumberItemsListener chnageNumberItemsListener){
//            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart()+1);
//            tinyDB.putListObject("CartList",listfood);
//            chnageNumberItemsListener.changed();



            //String sc = String.valueOf(dataList.get(position).getScore());
        //holder.recyclerScore.setText(sc);
//       holder.recyclerPrice.setText(dataList.get(position).getPrice());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
//                intent.putExtra("object",dataList.get(holder.getAdapterPosition()));
//
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption,recyclerPrice,recyclerScore,plusBtn,minusBtn,numberItemCarttxt,totaltxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.picCart);
            recyclerCaption = itemView.findViewById(R.id.titleText_cart);
            recyclerPrice = itemView.findViewById(R.id.feeEachItem);
            recyclerScore = itemView.findViewById(R.id.totalEachItem);


            plusBtn = itemView.findViewById(R.id.plusCartBtn);
            minusBtn = itemView.findViewById(R.id.minusCartbtn);
            numberItemCarttxt = itemView.findViewById(R.id.numberItemCarttxt);
            totaltxt = itemView.findViewById(R.id.totalEachItem);


            //recyclerScore = itemView.findViewById(R.id.totalEachItem);



        }
    }
}

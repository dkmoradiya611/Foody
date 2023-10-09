package com.example.zwagii.Activity;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    //    private ArrayList<DataClass> dataList;
    private ArrayList<CartClass> dataList;
    private Context context;
    int pos = 0;

    public CartAdapter() {
    }

    public CartAdapter(Context context, ArrayList<CartClass> dataList,RecyclerView recyclerView) {
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
//        holder.recyclerPrice.setText(String.valueOf(dataList.get(position).getPrice()));

        String a = dataList.get(position).getPrice();
        holder.recyclerPrice.setText("₹"+a);

        String b = dataList.get(position).getTotalPrice();
        holder.recyclerScore.setText("₹"+b);

        holder.numberItemCarttxt.setText(dataList.get(position).getNumberInCart());


//        int aa = Integer.parseInt(a);
//        int b = aa / 15;
//
//        holder.numberItemCarttxt.setText(String.valueOf(b));





//        holder.numberItemCarttxt.setText(String.valueOf(dataList.get(position).getNumberInCart()));
        //String a=holder.recyclerScore.setText(String.valueOf(dataList.get(position)));


        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
//            public int numberOrder = 1;

            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("mypref",MODE_PRIVATE);

                String name = sharedPreferences.getString("name",null);

                CartClass cartClass=dataList.get(position);
                int ttl =Integer.parseInt(cartClass.getTotalPrice());

                ttl=ttl+Integer.parseInt(dataList.get(position).getPrice());
                holder.recyclerScore.setText(ttl+"");
                dataList.get(position).setTotalPrice(ttl+"");
                int num= Integer.parseInt(dataList.get(position).getNumberInCart());
                num+=1;
                cartClass.setNumberInCart(num+"");
                FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(dataList.get(position).getTitle()).setValue(dataList.get(position));


//                numberOrder = pos + 1;
//                holder.recyclerPrice.setText(String.valueOf(dataList.get(position).getPrice()));
//                int ps = Integer.parseInt(dataList.get(position).getPrice().toString());
//
//
//                holder.numberItemCarttxt.setText("" + numberOrder);
//                Double b = Double.valueOf(dataList.get(position).getPrice());
//
//
//                holder.totaltxt.setText("$" + Math.round(numberOrder * ps));
//
//                pos = numberOrder;

//                int a = Integer.parseInt(dataList.get(position).getNumberInCart());
//                a += 1;
//
//                holder.numberItemCarttxt.setText(String.valueOf(a));

            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            public int numberOrder = pos;

            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("mypref",MODE_PRIVATE);

                String name = sharedPreferences.getString("name",null);

                CartClass cartClass=dataList.get(position);
                int ttl =Integer.parseInt(cartClass.getTotalPrice());
                if(ttl!=0){
                    ttl=ttl-Integer.parseInt(dataList.get(position).getPrice());
                    holder.recyclerScore.setText(ttl+"");
                    dataList.get(position).setTotalPrice(ttl+"");
                    int num= Integer.parseInt(dataList.get(position).getNumberInCart());
                    num-=1;
                    cartClass.setNumberInCart(num+"");
                    FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(dataList.get(position).getTitle()).setValue(dataList.get(position));
                }
                if(ttl==0){
                    FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(dataList.get(position).getTitle()).removeValue();
                }

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
        TextView recyclerCaption, recyclerPrice, recyclerScore, plusBtn, minusBtn, numberItemCarttxt, totaltxt;

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
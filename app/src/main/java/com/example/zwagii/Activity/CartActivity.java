package com.example.zwagii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwagii.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    String name;



    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    private CartAdapter adapter;
    
//    private RecyclerView.Adapter adapter;
//    private RecyclerView recyclerView;
//    private ManagementCart managementCart;
    private TextView totalFeeTxt,taxTxt,deliveryTxt,totaltxt,emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;
    private AppCompatButton orderBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        name = sharedPreferences.getString(KEY_NAME,null);




        recyclerView = findViewById(R.id.view3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        dataList = new ArrayList<>();
        adapter = new CartAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



//        managementCart = new ManagementCart(this);
        initView();
        initList();
        calculateCart();
        setVariable();
        
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(managementCart.getListCart().isEmpty())
//                {
//                    orderBtn.setEnabled(false);
//                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    orderBtn.setEnabled(true);
//                    Toast.makeText(CartActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }

    private void setVariable() {
        backBtn.setOnClickListener(v -> finish());
    }

    public void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new CartListAdapter(managementCart.getListCart(),this, new ChnageNumberItemsListener() {
//            @Override
//            public void changed() {
//                calculateCart();
//            }
//        });
//
//        recyclerView.setAdapter(adapter);
//
//        if(managementCart.getListCart().isEmpty()){
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        }else{
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }
    }


    private void calculateCart(){
//        if(managementCart.getListCart().isEmpty())
//        {
//            totalFeeTxt.setText("$0");
//            taxTxt.setText("$0");
//            deliveryTxt.setText("$0");
//            totaltxt.setText("$0");
//        }
//        else
//        {
//            double percentTax=0.02;
//            double delivery =10;
//            tax = Math.round(managementCart.getTotalFee() * percentTax * 100.0)/100.0;
//
//            double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100;
//            double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;
//
//            totalFeeTxt.setText("$"+itemTotal);
//            taxTxt.setText("$"+tax);
//            deliveryTxt.setText("$"+delivery);
//            totaltxt.setText("$"+total);
//
//        }


    }

    private void initView() {

        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totaltxt = findViewById(R.id.totalTxt);
        recyclerView = findViewById(R.id.view3);
        scrollView = findViewById(R.id.scrollViewCart);
        backBtn = findViewById(R.id.backBtn);
        orderBtn = findViewById(R.id.orderBtn);
        emptyTxt = findViewById(R.id.emptyTxt);

    }
}
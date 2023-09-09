package com.example.zwagii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.zwagii.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    private RecyclerView.Adapter adapterFoodList;
//    private RecyclerView recyclerViewFood;

    String user, usern, userName, userEmail, userPassword;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROLE = "role";

    String name, role;
    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    private HighAdapter adapter;
    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Images");

    ImageView showProfilePic;
    String usernameUser;

    TextView tvuname, viewAll;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    //private FirebaseDatabase database;
    private DatabaseReference usersRef;
    FirebaseAuth Fauth;
    Button logout;
//    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewAll = findViewById(R.id.tvall);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        name = sharedPreferences.getString(KEY_NAME, null);
//        role = sharedPreferences.getString(KEY_ROLE, null);
//
//        if(name != null )
//        {
//            //tvuname.setText(name);
//        }


        Fauth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                FirebaseAuth.getInstance().signOut();

            }
        });
//        user=Fauth.getCurrentUser();
//        if (user==null){
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
//        }else {
//            //tvuname.setText(user.getEmail());
//            //showProfilePic.getImageAlpha();
//        }

        recyclerView = findViewById(R.id.recycler_high);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dataList = new ArrayList<>();
        adapter = new HighAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference_High.addValueEventListener(new ValueEventListener() {
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


        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner3, ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);


        showProfilePic = findViewById(R.id.imageView);
        tvuname = findViewById(R.id.textView2);
        initRecyclerview();
        bottomNavigation();


        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Replace "desiredUsername" with the username you want to fetch the image for
//        Intent intent = getIntent();
//        usernameUser = intent.getStringExtra("username");
        tvuname.setText(name);
        String desiredUsername = name;


        // Retrieve the user's image URL
        usersRef.orderByChild("username").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                    Glide.with(MainActivity.this).load(imageUrl).into(showProfilePic);
                    usern = userSnapshot.child("name").getValue(String.class);
                    userName = userSnapshot.child("username").getValue(String.class);
                    userEmail = userSnapshot.child("email").getValue(String.class);
                    userPassword = userSnapshot.child("password").getValue(String.class);

                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        showProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, SettingActivity.class);

                startActivity(intent1);
            }
        });
    }


    private void bottomNavigation() {
        //  LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout menuBtn = findViewById(R.id.menuBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);


        ConstraintLayout cat_pizza = findViewById(R.id.cat_pizza);
        ConstraintLayout cat_burger = findViewById(R.id.cat_burger);
        ConstraintLayout cat_hotdog = findViewById(R.id.cat_hotdog);
        ConstraintLayout cat_drinks = findViewById(R.id.cat_drinks);

//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,MainActivity.class));
//            }
//        });


        cat_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                String path = "Pizzas";
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        cat_burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                String path = "Burgers";
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        cat_hotdog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                String path = "Hotdog";
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        cat_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                String path = "Drinks";
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });


        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
//                String path = "Images";
//                intent.putExtra("path", path);
//
//                startActivity(intent);

                if (role.matches("Admin")) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity_admin.class);
                    String path = "Images";
                    intent.putExtra("path", path);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    String path = "Images";
                    intent.putExtra("path", path);

                    startActivity(intent);
                }


            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                String path = "Images";
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    private void initRecyclerview() {
//        ArrayList<FoodDomain> items = new ArrayList<>();
//        items.add(new FoodDomain("Cheese Burger","Satisfy your cravings with our juicy Cheese Burger. \n"+
//                "Made with 100% Angus beef patty and topped with\n"+
//                " melted cheddar cheese, fresh lettuce, tomato, and\n"+
//                " our secret sauce, this classic burger will leave you\n"+
//                " wanting more. Served with crispy fries and a drink,\n"+
//                " it's the perfect meal for any occasion.","fast_1",15,20,120,4));
//        items.add(new FoodDomain("Pizza Peperoni","Get a taste of Italy with our delicious peperoni pizza. Made with freshly rolled dough, zesty tomato sauce, mozzarella cheese, and topped with spicy pepperoni Slices, this pizza is sure to be a crowd Pleaser. Perfectly baked in a wood-fired oven, it's the perfect choice for a quick lunch or a family dinner.","fast_2",10,25,200,5));
//        items.add(new FoodDomain("Vegetable Pizza","looking for a healthier option? Try our vegetable pizza, made with a variety of a fresh veggies such as bell peppers, onions, mushrooms, olives, and tomatoes. Topped with mozzarella cheese and a tangy tomato sauce, this pizza is full of flavor and goodness. Perfect for a vegetarians and anyone who wants to add more greens to their diet.","fast_3",13,30,100,4.5));
//
//        recyclerViewFood = findViewById(R.id.view1);
//        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//
//        adapterFoodList = new FoodListAdapter(items);
//        recyclerViewFood.setAdapter(adapterFoodList);
    }

    public void onBackPressed() {
        finishAffinity();
        MainActivity.this.finish();
        //System.exit(0);
    }
}
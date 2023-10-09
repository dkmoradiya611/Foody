package com.example.zwagii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DetailActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    String name,ab="1";
    private DataClass object;
    private Button addToCartBtn;
    ProgressBar progressBar;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, calTxt, timeTxt,numberItemTxt;
    private ImageView picFood, backBtn;
    //    private Highadapter object;
    private int numberOrder = 1;
    //    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        name = sharedPreferences.getString(KEY_NAME,null);


//        managementCart = new ManagementCart(DetailActivity.this);

        initView();
        getBundle();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        backBtn = findViewById(R.id.backBtnOfDetail);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DetailActivity.this,MainActivity.class));

                finish();
            }
        });

    }

    private void getBundle() {

        object = (DataClass) getIntent().getSerializableExtra("object");

        String firebaseImageUrl = object.getImageURL();

        Glide.with(this)
                .load(firebaseImageUrl)
                .into(picFood);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("₹" + object.getPrice());
        descriptionTxt.setText(object.getCaption());
        numberOrderTxt.setText("" + numberOrder);
        calTxt.setText(object.getEnergy() + "Cal");
        startTxt.setText(object.getScore() + "");
        timeTxt.setText(object.getTime() + "min");
//        numberItemTxt.setText(object.getNumberInCart() + "");
//        String a = String.valueOf(object.getPrice());
//
//
//
//        ab = String.valueOf(object.getPrice());
//
//        int aa = Integer.parseInt(a);
//
//        addToCartBtn.setText("Add to cart - ₹" + Math.round(numberOrder * aa));

        ab = object.getPrice();
        addToCartBtn.setText("Add to cart - ₹" + ab);


        plusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText("" + numberOrder);
//            int b = Integer.parseInt(object.getPrice());
            int b = Integer.parseInt(ab);
            int temp=numberOrder * b;
            ab = String.valueOf(temp);
//                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * b));
            addToCartBtn.setText("Add to cart - ₹" + ab);
        });

        minusBtn.setOnClickListener(v -> {
            if (numberOrder <= 1) {
                numberOrderTxt.setText("1");
            } else {
                numberOrder = numberOrder - 1;
                numberOrderTxt.setText("" + numberOrder);
                int b = Integer.parseInt(object.getPrice());
                int temp=numberOrder * b;
                ab = String.valueOf(temp);
//                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * b));
                addToCartBtn.setText("Add to cart - ₹" + ab);
            }
        });

        addToCartBtn.setOnClickListener(v -> {
            addToCartBtn.setEnabled(true);
//                object.setNumberInCart(numberOrder);


            // managementCart.insertFood(object);

            Intent intent=new Intent(DetailActivity.this,CartActivity.class);
            //intent.putExtra("title",object.getTitle());
//            uploadToFirebase();
//            intent.putExtra("pri",object.getPrice());


            convertAndStoreImageURL();
            String ur = object.getImageURL().toString();
            // Convert string URL to Uri
            Uri actualImageUrl = Uri.parse(ur);


            String ab1 = object.getTitle().toString();
            String pr1 = object.getPrice();
            String pr = ab;
            String sc = object.getScore();
            String nm = String.valueOf(numberOrder);



            // Get reference to the new subfolder where you want to store the actual URL
            DatabaseReference newSubfolderRef = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("imageURL");
            DatabaseReference newSubfolderRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("title");
            DatabaseReference newSubfolderRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("price");
            DatabaseReference newSubfolderRef3 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("score");
            DatabaseReference newSubfolderRef4 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("numberInCart");
            DatabaseReference newSubfolderRef5 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("totalPrice");

            // Store the actual URL in the new subfolder
            newSubfolderRef.setValue(actualImageUrl.toString());
            newSubfolderRef1.setValue(ab1);
            newSubfolderRef2.setValue(pr1);
            newSubfolderRef3.setValue(sc);
            newSubfolderRef4.setValue(nm);
            newSubfolderRef5.setValue(pr);

            startActivity(intent);

            finish();

        });

        numberItemTxt.setText(numberOrder + "");
//        numberItemTxt.setText(object.getNumberInCart() + "");



    }

    private void initView() {

        addToCartBtn = findViewById(R.id.addToCartBtn);
        timeTxt = findViewById(R.id.timeTxt2);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.MinusCartBtn);
        picFood = findViewById(R.id.foodPic);
        startTxt = findViewById(R.id.StarTxt);
        calTxt = findViewById(R.id.calTxt);
        titleTxt = findViewById(R.id.titleTxt1);
        numberItemTxt = findViewById(R.id.numberItemTxt);
    }






    public void convertAndStoreImageURL() {

//        String ur = object.getImageURL().toString();
//        // Convert string URL to Uri
//        Uri actualImageUrl = Uri.parse(ur);
//
//
//        String ab1 = object.getTitle().toString();
//        String pr1 = object.getPrice();
//        String pr = ab;
//        String sc = object.getScore();
//        String nm = String.valueOf(numberOrder);
//
//
//        // Get reference to the new subfolder where you want to store the actual URL
//        DatabaseReference newSubfolderRef = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("imageURL");
//        DatabaseReference newSubfolderRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("title");
//        DatabaseReference newSubfolderRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("price");
//        DatabaseReference newSubfolderRef3 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("score");
//        DatabaseReference newSubfolderRef4 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("numberInCart");
//        DatabaseReference newSubfolderRef5 = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(ab1).child("totalPrice");
//
//        // Store the actual URL in the new subfolder
//        newSubfolderRef.setValue(actualImageUrl.toString());
//        newSubfolderRef1.setValue(ab1);
//        newSubfolderRef2.setValue(pr1);
//        newSubfolderRef3.setValue(sc);
//        newSubfolderRef4.setValue(nm);
//        newSubfolderRef5.setValue(pr);

    }


    private void uploadToFirebase(){




    }
}


// -------------------------------------------------------------------------------------------------------
// --------------------------------------------   Old   -------------------------------------------------------
// -------------------------------------------------------------------------------------------------------


//public class DetailActivity extends AppCompatActivity {
//
//    private DataClass object;
//    private Button addToCartBtn;
//    ProgressBar progressBar;
//    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, calTxt, timeTxt;
//    private ImageView picFood, backBtn;
////    private Highadapter object;
//    private int numberOrder = 1;
////    private ManagementCart managementCart;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
////        managementCart = new ManagementCart(DetailActivity.this);
//
//        initView();
//        getBundle();
//
//        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);
//
//        backBtn = findViewById(R.id.backBtnOfDetail);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
//
//    private void getBundle() {
//
//            object = (DataClass) getIntent().getSerializableExtra("object");
//
//        String firebaseImageUrl = object.getImageURL();
//
//        Glide.with(this)
//                .load(firebaseImageUrl)
//                .into(picFood);
//            titleTxt.setText(object.getTitle());
//            feeTxt.setText("$" + object.getPrice());
//            descriptionTxt.setText(object.getCaption());
//            numberOrderTxt.setText("" + numberOrder);
//            calTxt.setText(object.getEnergy() + "Cal");
//            startTxt.setText(object.getScore() + "");
//            timeTxt.setText(object.getTime() + "min");
//            addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//
//            plusBtn.setOnClickListener(v -> {
//                numberOrder = numberOrder + 1;
//                numberOrderTxt.setText("" + numberOrder);
//                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//            });
//
//            minusBtn.setOnClickListener(v -> {
//                if (numberOrder <= 1) {
//                    numberOrderTxt.setText("1");
//                } else {
//                    numberOrder = numberOrder - 1;
//                    numberOrderTxt.setText("" + numberOrder);
//                    addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//                }
//            });
//
//            addToCartBtn.setOnClickListener(v -> {
//                addToCartBtn.setEnabled(true);
////                object.setNumberInCart(numberOrder);
//
//
//               // managementCart.insertFood(object);
//
//                Intent intent=new Intent(DetailActivity.this,CartActivity.class);
//                //intent.putExtra("title",object.getTitle());
//                uploadToFirebase();
//                startActivity(intent);
//            });
//
//
//
//
////        object = (Highadapter) getIntent().getSerializableExtra("object");
////
////        int drawableResourceId = this.getResources().getIdentifier(object.getPicurl(), "drawable", this.getPackageName());
////        Glide.with(this)
////                .load(drawableResourceId)
////                .into(picFood);
////
////        titleTxt.setText(object.getTitle());
////        feeTxt.setText("$" + object.getPrice());
////        descriptionTxt.setText(object.getDescription());
////        numberOrderTxt.setText("" + numberOrder);
////        calTxt.setText(object.getEnergy() + "Cal");
////        startTxt.setText(object.getScore() + "");
////        timeTxt.setText(object.getTime() + "min");
////        addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
////
////        plusBtn.setOnClickListener(v -> {
////            numberOrder = numberOrder + 1;
////            numberOrderTxt.setText("" + numberOrder);
////            addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
////        });
////
////        minusBtn.setOnClickListener(v -> {
////            if (numberOrder <= 1) {
////                numberOrderTxt.setText("1");
////            } else {
////                numberOrder = numberOrder - 1;
////                numberOrderTxt.setText("" + numberOrder);
////                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
////            }
////        });
////
////        addToCartBtn.setOnClickListener(v -> {
////                addToCartBtn.setEnabled(true);
////                object.setNumberInCart(numberOrder);
////                managementCart.insertFood(object);
////
////            Intent intent=new Intent(DetailActivity.this,CartActivity.class);
////            startActivity(intent);
////        });
//    }
//
//    private void initView() {
//
//        addToCartBtn = findViewById(R.id.addToCartBtn);
//        timeTxt = findViewById(R.id.timeTxt2);
//        feeTxt = findViewById(R.id.priceTxt);
//        descriptionTxt = findViewById(R.id.descriptionTxt);
//        numberOrderTxt = findViewById(R.id.numberItemTxt);
//        plusBtn = findViewById(R.id.plusCardBtn);
//        minusBtn = findViewById(R.id.MinusCartBtn);
//        picFood = findViewById(R.id.foodPic);
//        startTxt = findViewById(R.id.StarTxt);
//        calTxt = findViewById(R.id.calTxt);
//        titleTxt = findViewById(R.id.titleTxt1);
//    }
//
//    private void uploadToFirebase(){
//        String title = object.getTitle();
//        String caption = object.getCaption();
//        //String price = uploadPrice.getText().toString();
//        //String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(object.getPrice());
//        int time = object.getTime();
//        int energy =object.getEnergy();
//        Double score = object.getScore();
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//        final StorageReference imageReference = storageReference.child("add/");
//        Uri u = Uri.parse(object.getImageURL());
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Add");
//        imageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
//                        String key = databaseReference.push().getKey();
//                        databaseReference.child(title).setValue(dataClass);
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(DetailActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DetailActivity.this, MenuActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
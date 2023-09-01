package com.example.zwagii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private DataClass object;
    private Button addToCartBtn;
    ProgressBar progressBar;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, calTxt, timeTxt;
    private ImageView picFood, backBtn;
    //    private Highadapter object;
    private int numberOrder = 1;
    //    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        managementCart = new ManagementCart(DetailActivity.this);

        initView();
        getBundle();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        backBtn = findViewById(R.id.backBtnOfDetail);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        feeTxt.setText("$" + object.getPrice());
        descriptionTxt.setText(object.getCaption());
        numberOrderTxt.setText("" + numberOrder);
        calTxt.setText(object.getEnergy() + "Cal");
        startTxt.setText(object.getScore() + "");
        timeTxt.setText(object.getTime() + "min");
        addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));

        plusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
        });

        minusBtn.setOnClickListener(v -> {
            if (numberOrder <= 1) {
                numberOrderTxt.setText("1");
            } else {
                numberOrder = numberOrder - 1;
                numberOrderTxt.setText("" + numberOrder);
                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
            }
        });

        addToCartBtn.setOnClickListener(v -> {
            addToCartBtn.setEnabled(true);
//                object.setNumberInCart(numberOrder);


            // managementCart.insertFood(object);

            Intent intent=new Intent(DetailActivity.this,CartActivity.class);
            //intent.putExtra("title",object.getTitle());
//            uploadToFirebase();

            convertAndStoreImageURL();
            startActivity(intent);
        });




//        object = (Highadapter) getIntent().getSerializableExtra("object");
//
//        int drawableResourceId = this.getResources().getIdentifier(object.getPicurl(), "drawable", this.getPackageName());
//        Glide.with(this)
//                .load(drawableResourceId)
//                .into(picFood);
//
//        titleTxt.setText(object.getTitle());
//        feeTxt.setText("$" + object.getPrice());
//        descriptionTxt.setText(object.getDescription());
//        numberOrderTxt.setText("" + numberOrder);
//        calTxt.setText(object.getEnergy() + "Cal");
//        startTxt.setText(object.getScore() + "");
//        timeTxt.setText(object.getTime() + "min");
//        addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//
//        plusBtn.setOnClickListener(v -> {
//            numberOrder = numberOrder + 1;
//            numberOrderTxt.setText("" + numberOrder);
//            addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//        });
//
//        minusBtn.setOnClickListener(v -> {
//            if (numberOrder <= 1) {
//                numberOrderTxt.setText("1");
//            } else {
//                numberOrder = numberOrder - 1;
//                numberOrderTxt.setText("" + numberOrder);
//                addToCartBtn.setText("Add to cart - $" + Math.round(numberOrder * object.getPrice()));
//            }
//        });
//
//        addToCartBtn.setOnClickListener(v -> {
//                addToCartBtn.setEnabled(true);
//                object.setNumberInCart(numberOrder);
//                managementCart.insertFood(object);
//
//            Intent intent=new Intent(DetailActivity.this,CartActivity.class);
//            startActivity(intent);
//        });
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
    }



//    import android.net.Uri;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


    public void convertAndStoreImageURL() {

        String ur = object.getImageURL().toString();
        // Convert string URL to Uri
        Uri actualImageUrl = Uri.parse(ur);


        String ab = object.getTitle().toString();

        // Get reference to the new subfolder where you want to store the actual URL
        DatabaseReference newSubfolderRef = FirebaseDatabase.getInstance().getReference().child("users").child("DK").child("AddToCart").child(ab).child("imageCartUrl");

        // Store the actual URL in the new subfolder
        newSubfolderRef.setValue(actualImageUrl.toString());
    }


    private void uploadToFirebase(){




//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("imageURL");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String imageUrlString = object.getImageURL().toString();
//
//                    // Now you have the image URL as a string
//                    // Convert it to a URL object
//                    try {
//                        URL imageUrl = new URL(imageUrlString);
//                        // Proceed to storing the URL in a different subfolder
//                        DatabaseReference newUrlsReference = FirebaseDatabase.getInstance().getReference().child("users").child("DK").child("imageURLOfCart");
//
//                        // Assuming imageUrl is the URL object from the previous step
//                        if (imageUrl != null) {
//                            newUrlsReference.push().setValue(imageUrl.toString())
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            // URL successfully stored in the new subfolder
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // Handle failure
//                                        }
//                                    });
//                        }
//
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle error
//            }
//        });




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

        // Use a library like Picasso or Glide to download the image
//        String imageURL = object.getImageURL();
//        Picasso.get().load(imageURL).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                // The image has been downloaded and loaded into the bitmap
//
//                // Convert the bitmap to a byte array (JPEG format in this case)
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] imageByteArray = baos.toByteArray();
//
//
//                String base64Image = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
//
//                // Now you can update the image URL in the user's data in the Realtime Database
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference userRef = database.getReference("users").child("DK").child("AddToCart");
//
//                userRef.child("imageURL").setValue(base64Image)
//                        .addOnSuccessListener(aVoid -> {
//                            // Image URL updated successfully
//                        })
//                        .addOnFailureListener(exception -> {
//                            // Handle error
//                        });

//                // Now you can upload the imageByteArray to Firebase Storage
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//
//                // Create a unique name for the image file in Storage
//                String imageName = "image_" + System.currentTimeMillis() + ".jpg";
//
//                // Create a reference to the location where you want to store the image
//                StorageReference imageStorageRef = storageRef.child("cart/" + imageName);
//
//                // Upload the image
//                UploadTask uploadTask = imageStorageRef.putBytes(imageByteArray);
//                uploadTask.addOnSuccessListener(taskSnapshot -> {
//                    // Image uploaded successfully
//                    // Now, you can get the download URL of the uploaded image
//                    imageStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                        String uploadedImageUrl = uri.toString();
//                        // Do something with the uploadedImageUrl if needed
//                    });
//                }).addOnFailureListener(exception -> {
//                    // Handle error
//                });
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                // Handle failure to load the image
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                // Image is being loaded
//            }
//        });

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
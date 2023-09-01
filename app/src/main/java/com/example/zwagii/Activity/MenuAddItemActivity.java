package com.example.zwagii.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zwagii.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MenuAddItemActivity extends AppCompatActivity {

    Spinner spinner;
    List<String> items;
    String item;


    private FloatingActionButton uploadButton;
    private ImageView uploadImage;
    EditText uploadTitle,uploadCaption,uploadPrice,uploadTime,uploadEnergy,uploadScore;
    ProgressBar progressBar;
    private Uri imageUri;
     DatabaseReference databaseReference;
      StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_item);


        spinner =findViewById(R.id.spinner);

        items = new ArrayList<>();
        items.add(0,"Food Category");
        items.add("Pizza");
        items.add("Burger");
        items.add("Hotdog");
        items.add("Drinks");
        items.add("General");
        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //1st Method
                //item = items.get(position);

                //2nd Method
                if(!(spinner.getSelectedItem().toString() == "Food Category"))
                {
                    item = spinner.getSelectedItem().toString();
                    Toast.makeText(MenuAddItemActivity.this, item, Toast.LENGTH_SHORT).show();

                }else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        uploadButton = findViewById(R.id.uploadButton);
        uploadTitle = findViewById(R.id.uploadTitle);
        uploadCaption = findViewById(R.id.uploadCaption);

        uploadPrice = findViewById(R.id.uploadPrice);
        uploadTime = findViewById(R.id.uploadTime);
        uploadEnergy = findViewById(R.id.uploadEnergy);
        uploadScore = findViewById(R.id.uploadScore);

        uploadImage = findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(MenuAddItemActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }



        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                } else  {
                    Toast.makeText(MenuAddItemActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Outside onCreate
    private void uploadToFirebase(Uri imageUri){

        if (spinner.getSelectedItem().toString() == "General") {

            String title = uploadTitle.getText().toString();
            String caption = uploadCaption.getText().toString();
            //String price = uploadPrice.getText().toString();
            String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(uploadPrice.getText().toString());
            int time = Integer.parseInt(uploadTime.getText().toString());
            int energy = Integer.parseInt(uploadEnergy.getText().toString());
            String score = uploadScore.getText().toString();
//        Double score = Double.valueOf(uploadScore.getText().toString());

            StorageReference imageReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Images");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if (spinner.getSelectedItem().toString() == "Pizza") {

            String title = uploadTitle.getText().toString();
            String caption = uploadCaption.getText().toString();
            //String price = uploadPrice.getText().toString();
            String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(uploadPrice.getText().toString());
            int time = Integer.parseInt(uploadTime.getText().toString());
            int energy = Integer.parseInt(uploadEnergy.getText().toString());
            String score = uploadScore.getText().toString();
//        Double score = Double.valueOf(uploadScore.getText().toString());

            StorageReference imageReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Images");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });




            StorageReference imageReference2 = storageReference.child("pizza/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Pizzas");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else if (spinner.getSelectedItem().toString() == "Burger") {

            String title = uploadTitle.getText().toString();
            String caption = uploadCaption.getText().toString();
            //String price = uploadPrice.getText().toString();
            String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(uploadPrice.getText().toString());
            int time = Integer.parseInt(uploadTime.getText().toString());
            int energy = Integer.parseInt(uploadEnergy.getText().toString());
            String score = uploadScore.getText().toString();
//        Double score = Double.valueOf(uploadScore.getText().toString());

            StorageReference imageReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Images");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });




            StorageReference imageReference2 = storageReference.child("burger/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Burgers");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else if (spinner.getSelectedItem().toString() == "Hotdog") {

            String title = uploadTitle.getText().toString();
            String caption = uploadCaption.getText().toString();
            //String price = uploadPrice.getText().toString();
            String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(uploadPrice.getText().toString());
            int time = Integer.parseInt(uploadTime.getText().toString());
            int energy = Integer.parseInt(uploadEnergy.getText().toString());
            String score = uploadScore.getText().toString();
//        Double score = Double.valueOf(uploadScore.getText().toString());

            StorageReference imageReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Images");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });




            StorageReference imageReference2 = storageReference.child("hotdog/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Hotdog");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else if (spinner.getSelectedItem().toString() == "Drinks") {

            String title = uploadTitle.getText().toString();
            String caption = uploadCaption.getText().toString();
            //String price = uploadPrice.getText().toString();
            String price = uploadPrice.getText().toString();
//        Double price = Double.valueOf(uploadPrice.getText().toString());
            int time = Integer.parseInt(uploadTime.getText().toString());
            int energy = Integer.parseInt(uploadEnergy.getText().toString());
            String score = uploadScore.getText().toString();
//        Double score = Double.valueOf(uploadScore.getText().toString());

            StorageReference imageReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Images");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });




            StorageReference imageReference2 = storageReference.child("drinks/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));


            imageReference2.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(title.toString(), uri.toString(),caption.toString(), price, time, energy, score);
                            databaseReference = FirebaseDatabase.getInstance().getReference("Drinks");
                            String key = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MenuAddItemActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MenuAddItemActivity.this, MenuActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MenuAddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }







    }
    private String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
}


package com.example.zwagii.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";
    EditText editName, editEmail, editUsername, editPassword;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";

    ImageView showProfilePic;
    Button saveButton;
    Uri imageUri;
    String nameUser, emailUser, usernameUser, passwordUser,image;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

//    String img = databaseReference.child("Rudra").child("imageURLUser").get().toString();

    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        showProfilePic = findViewById(R.id.showProfilePic);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME,null);

        if(name != null )
        {
            //tvuname.setText(name);
        }


        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Replace "desiredUsername" with the username you want to fetch the image for
        Intent intent = getIntent();
        usernameUser = intent.getStringExtra("imau");
        String desiredUsername = name;

        showData();

        // Retrieve the user's image URL
        usersRef.orderByChild("username").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                    Glide.with(EditProfile.this).load(imageUrl).into(showProfilePic);
                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });



//        showProfilePic.setImageURI(FirebaseDatabase.getInstance().getReference().child("imageURLUser").);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged() || isPasswordChanged() || isUnameChanged()){
                    Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();

//                    startActivity(new Intent(EditProfile.this, MainActivity.class));
//                    finish();
                } else {
                    Toast.makeText(EditProfile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())){
            databaseReference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())){
            databaseReference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())){
            databaseReference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isUnameChanged() {
        if (!usernameUser.equals(editUsername.getText().toString())){
            databaseReference.child(usernameUser).child("username").setValue(editUsername.getText().toString());
            usernameUser = editUsername.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    public void showData(){
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("uname");
        emailUser = intent.getStringExtra("uEmail");
        usernameUser = intent.getStringExtra("uUname");
        passwordUser = intent.getStringExtra("uPwd");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
    }


}

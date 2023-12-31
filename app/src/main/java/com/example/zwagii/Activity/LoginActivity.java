package com.example.zwagii.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zwagii.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
//    private static final String KEY_ROLE = "role";

    String name, role;
    String selectedOption;

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    ProgressBar progressBar;
    FirebaseAuth Fauth;
    LinearLayout bgimage;

    //    public void onStart(){
//        super.onStart();
//        FirebaseUser user=Fauth.getCurrentUser();
//        if (user!=null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bg2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bg3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img5), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img7), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img8), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img9), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img10), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img11), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        bgimage = findViewById(R.id.back_login);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        name = sharedPreferences.getString(KEY_NAME, null);
//         role = sharedPreferences.getString(KEY_ROLE,null);
//        if(name != null && role != null){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        if (name != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


//        RadioGroup radioGroup = findViewById(R.id.radioGroup);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // Check which radio button is selected
//                RadioButton radioButton = findViewById(checkedId);
//
//                if (radioButton != null) {
//                     selectedOption = radioButton.getText().toString();
//                    Toast.makeText(LoginActivity.this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        progressBar = findViewById(R.id.progressBarUser2);
        Fauth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (checkUser()) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email, password;
                    email = String.valueOf(loginUsername.getText());
                    password = String.valueOf(loginPassword.getText());
                    Fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                FirebaseUser user = Fauth.getCurrentUser();
                                assert user != null;
                                if (user.isEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please Verify Email", Toast.LENGTH_SHORT).show();
                                }
//                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME, loginUsername.getText().toString());
//                            editor.putString(KEY_ROLE, selectedOption.trim());
                                //editor.putString(KEY_PWD,edtpwd.getText().toString());
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //validateUsername();
                    //validatePassword();
                    checkUser();
                }
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

  // public Boolean validateUsername() {
  //     String val = loginUsername.getText().toString();
  //     if (val.isEmpty()) {
  //         loginUsername.setError("Username cannot be empty");
  //         return false;
  //     } else {
  //         loginUsername.setError(null);
  //         return true;
  //     }
  // }

  // public Boolean validatePassword() {
  //     String val = loginPassword.getText().toString();
  //     if (val.isEmpty()) {
  //         loginPassword.setError("Password cannot be empty");
  //         return false;
  //     } else {
  //         loginPassword.setError(null);
  //         return true;
  //     }
  // }

    boolean valid;
    public boolean checkUser() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            valid=false;
        } else {
            loginUsername.setError(null);
        }


        String val2 = loginPassword.getText().toString();
        if (val2.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            valid=false;
        } else {
            loginPassword.setError(null);
        }



        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);
                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                        String image = snapshot.child("imageURLUser").getValue(String.class);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("name", nameFromDB);
////                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("username", usernameFromDB);
////                        intent.putExtra("password", passwordFromDB);
////                        intent.putExtra("imageURLUser", image);
//
//                        startActivity(intent);
                        valid=true;
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                        valid=false;
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                    valid=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return valid;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        LoginActivity.this.finish();
    }
}
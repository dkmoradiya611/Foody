package com.example.zwagii.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zwagii.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {
    SwitchCompat switchCompat,notify;
    boolean nightmode;
    ImageView back;


    Button editbutton;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    String user,usern,userName,userEmail,userPassword;

    private DatabaseReference usersRef;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    SharedPreferences.Editor editor;
    TextView usen,txtemail;
    RelativeLayout rv,sentmsg,aboutus,logout;
    ImageView imageView;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id="default";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.gray));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        imageView = findViewById(R.id.uid);
        usen = findViewById(R.id.uname);
        txtemail = findViewById(R.id.txtemail);
        editbutton = findViewById(R.id.editprofile);

        usersRef = FirebaseDatabase.getInstance().getReference("users");


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME,null);
        String email = sharedPreferences.getString(KEY_EMAIL,null);

        if(name != null )
        {
            //tvuname.setText(name);
        }


//        // Replace "desiredUsername" with the username you want to fetch the image for
//        Intent intent = getIntent();
//        user = intent.getStringExtra("un");
//        // tvuname.setText(usernameUser);
//        // usernameUser = name.toString();
//        String desiredUsername = user;
        String desiredUsername = name;



        // Retrieve the user's image URL
        usersRef.orderByChild("username").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                    usern = userSnapshot.child("name").getValue(String.class);
                    userName = userSnapshot.child("username").getValue(String.class);
                    userEmail = userSnapshot.child("email").getValue(String.class);
                    userPassword = userSnapshot.child("password").getValue(String.class);
//                    String nameFromDB = userSnapshot.child(usernameUser).child("name").getValue(String.class);
//                    String emailFromDB = userSnapshot.child(userUsername).child("email").getValue(String.class);
//                    String usernameFromDB = userSnapshot.child(userUsername).child("username").getValue(String.class);
//

                    usen.setText(name.toString());
                    txtemail.setText(userEmail.toString());


                    Glide.with(SettingActivity.this).load(imageUrl).into(imageView);
                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });






        switchCompat = findViewById(R.id.nnm);
        // Deva's code
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Ramani's code
        //sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightmode = sharedPreferences.getBoolean("night", false); //light mode

        logout = findViewById(R.id.log_out);
        back = findViewById(R.id.imgback);
        rv = findViewById(R.id.privacy);
        sentmsg = findViewById(R.id.sentmessage);
        aboutus = findViewById(R.id.aboutus);
        notify = findViewById(R.id.notification);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,AboutUs.class));
            }
        });
        sentmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,SentMessage.class));
            }
        });
        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,PrivacyPolicy.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,MainActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                finish();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                FirebaseAuth.getInstance().signOut();

            }
        });


        int Flag= getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean dark=Flag== Configuration.UI_MODE_NIGHT_YES;

        //dark mode
        if (dark) {
            switchCompat.setChecked(true);

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dark) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);

                }
                editor.apply();
            }
        });
        notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(notify.isChecked()){
                    Toast.makeText(SettingActivity.this, "Turn On Notification", Toast.LENGTH_SHORT).show();




                        Intent snoozeIntent = new Intent(SettingActivity.this, MainActivity.class);
                        snoozeIntent.setAction("ACTION_SNOOZE");
                        snoozeIntent.putExtra("EXTRA_NOTIFICATION_ID", 0);
                        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(SettingActivity.this, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        //MediaPlayer mp = MediaPlayer.create(SettingActivity.this, R.raw.snapchat_tone);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SettingActivity.this, default_notification_channel_id);
                        mBuilder.setContentTitle("Foody");
                        mBuilder.setContentText("Hungry??\nOrder a Fresh Food");
                        mBuilder.setTicker("Hungry??\nOrder a Fresh Food");
                        mBuilder.setSmallIcon(R.drawable.baseline_info_24);
                        mBuilder.setAutoCancel(true);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            assert mNotificationManager != null;
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }
                        assert mNotificationManager != null;
                        //mp.start();
                        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());




                }
                else
                {
                    Toast.makeText(SettingActivity.this, "Turn Off Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SettingActivity.this, EditProfile.class);


                intent1.putExtra("imau",user);

                intent1.putExtra("uname",usern.toString());
                intent1.putExtra("uEmail",userEmail.toString());
                intent1.putExtra("uUname",name.toString());
                intent1.putExtra("uPwd",userPassword.toString());

//                intent1.putExtra("unn",usernameUser.toString());
//                intent1.putExtra("unn",usernameUser.toString());
//                intent1.putExtra("unn",usernameUser.toString());
//                intent1.putExtra("unn",usernameUser.toString());
                //intent1.putExtra("un",usernameUser.toString());
                startActivity(intent1);
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingActivity.this,MainActivity.class));
    }
}
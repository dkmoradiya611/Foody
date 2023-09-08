package com.example.zwagii.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwagii.R;

public class AboutUs extends AppCompatActivity {

    ImageView back;
    TextView dar, par, deep, meet, deva, har;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        back = findViewById(R.id.imgback);

        dar = findViewById(R.id.textView5);
        par = findViewById(R.id.textView25);
        deep = findViewById(R.id.textView35);
        meet = findViewById(R.id.textView45);
        deva = findViewById(R.id.textView55);
        har = findViewById(R.id.textView65);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUs.this, SettingActivity.class));
                finish();
            }
        });

        dar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(dar.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        par.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(par.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        deep.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(deep.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        meet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(meet.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        deva.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(deva.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        har.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(har.getText().toString());
                Toast.makeText(AboutUs.this, "COPIED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
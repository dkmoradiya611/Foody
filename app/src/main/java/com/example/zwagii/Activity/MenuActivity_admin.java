package com.example.zwagii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.zwagii.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity_admin extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROLE = "role";

    String name,role;



    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    private MyAdapter adapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();






        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        name = sharedPreferences.getString(KEY_NAME,null);
        role = sharedPreferences.getString(KEY_ROLE,null);


//        assert role != null;
//        if(role.toString() == "Admin")
//        {
////            fab.hide();
//            fab.setVisibility(View.VISIBLE);
//        }else {
////            fab.show();
//            fab.setVisibility(View.VISIBLE);
////            fab.setVisibility(View.INVISIBLE);
//        }

//        Intent intent  = new Intent(MenuActivity.this, MyAdapter.class);
//        intent.putExtra("un",name);
//        startActivity(intent);


        Intent intent = getIntent();
        String pa = intent.getStringExtra("path");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(pa);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchlist(newText);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity_admin.this, MenuAddItemActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void searchlist(String text){
        ArrayList<DataClass> searchlist=new ArrayList<>();
        for(DataClass dataClass:dataList){
            if (dataClass.getCaption().toLowerCase().contains(text.toLowerCase())){
                searchlist.add(dataClass);
            }
        }
        adapter.searchdatalist(searchlist);
    }
}
package com.example.covid_hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Profile> list;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference = FirebaseDatabase.getInstance().getReference().child("Profiles");
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        list = new ArrayList<Profile>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Profile p = dataSnapshot1.getValue(Profile.class);

                        list.add(p);


                }
                adapter = new MyAdapter(MainActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setTitle("Create Entry");
        dialog.setCancelable(true);
        dialog.show();
        Button saveButton = (Button)dialog.findViewById(R.id.Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)dialog.findViewById(R.id.edit_name)).getText().toString();
                String email = ((EditText)dialog.findViewById(R.id.edit_email)).getText().toString();
                String status = ((EditText)dialog.findViewById(R.id.edit_status)).getText().toString();
                Profile p = new Profile(name,status,email);
                String id = reference.push().getKey();
                reference.child(id).setValue(p);
                dialog.dismiss();
            }
        });
        return super.onOptionsItemSelected(item);
    }
}
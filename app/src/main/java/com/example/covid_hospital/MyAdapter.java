package com.example.covid_hospital;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    Context context;
    ArrayList<Profile> profiles;
    private DatabaseReference databaseReference;

    public MyAdapter(Context c ,ArrayList<Profile> p){
        context =c;
        profiles =p;
        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");
    }


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyAdapter.MyViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.email.setText(profiles.get(position).getEmail());
        holder.profilePic.setImageResource(R.drawable.ic_baseline_person);
        holder.status.setText(profiles.get(position).getStatus());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query removequery = databaseReference.orderByChild("name").equalTo(profiles.get((position)).getName());

                removequery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });

            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setTitle("update");
                dialog.setCancelable(true);
                dialog.show();
                Button saveButton = (Button)dialog.findViewById(R.id.Save_Button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = ((EditText)dialog.findViewById(R.id.edit_name)).getText().toString();
                        String email = ((EditText)dialog.findViewById(R.id.edit_email)).getText().toString();
                        String status = ((EditText)dialog.findViewById(R.id.edit_status)).getText().toString();
                        final Profile p = new Profile(name,status,email);
                        Query updatequery = databaseReference.orderByChild("name").equalTo(profiles.get((position)).getName());
                        updatequery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    dataSnapshot.getRef().setValue(p);
                                    dialog.dismiss();



                                }

                            }

                            @Override
                            public void onCancelled(@NonNull  DatabaseError error) {

                            }
                        });
                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,email,status;
        ImageView profilePic;
        Button update,delete;


        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            profilePic  = (ImageView) itemView.findViewById(R.id.profilePic);
            status = (TextView) itemView.findViewById(R.id.status);
            update = (Button) itemView.findViewById(R.id.Update_profile);
            delete = (Button) itemView.findViewById(R.id.delete_profile);

        }


    }


}

package com.example.ninjacodefest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ninjacodefest.pojo.Food;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Home extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;


    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    FirestoreRecyclerOptions firestoreRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button serchbutton = findViewById(R.id.serchbutton);
        Button bu = findViewById(R.id.logout);
        TextView search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rec);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);


        Query loadjob = db.collection("Foods").whereEqualTo("status", "pending");
        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Food>().setQuery(loadjob, Food.class).build();
        //adpater set

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Food, foodholder>(firestoreRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull foodholder holder, int position, @NonNull Food model) {
                holder.name.setText(model.getName());
                holder.price.setText(model.getPrice() + " ");
                System.out.println(model.getImageurl());
                Glide.with(holder.imageView.getContext()).load(model.getImageurl()).into(holder.imageView);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(Home.this, Productview.class);
                        intent.putExtra("name", getItem(position).getName());
                        intent.putExtra("price", getItem(position).getPrice());
                        intent.putExtra("image", getItem(position).getImageurl());
                        startActivity(intent);

                    }


                });
            }


            @NonNull
            @Override
            public foodholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodview, parent, false);
                return new foodholder(view);
            }


        };


        recyclerView.setAdapter(firestoreRecyclerAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        serchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Query query = db.collection("Foods")
                        .whereEqualTo("status", "pending")
                        .orderBy("name").startAt(search.getText().toString()).endAt(search.getText().toString() + "\uf8ff");
                FirestoreRecyclerOptions<Food> build = new FirestoreRecyclerOptions.Builder<Food>().setQuery(query, Food.class).build();
                setView(build);
            }
        });


    }

    private void setView(FirestoreRecyclerOptions<Food> build) {
        firestoreRecyclerAdapter.stopListening();
        firestoreRecyclerAdapter.updateOptions(build);
        firestoreRecyclerAdapter.startListening();
        firestoreRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();
    }


    private class foodholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price;


        public foodholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.nameText);
            price = itemView.findViewById(R.id.priceText);
        }
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this, "Signout", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        // [END auth_fui_signout]
    }
}
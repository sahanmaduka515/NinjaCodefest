//package com.example.ninjacodefest;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.bumptech.glide.Glide;
//import com.example.ninjacodefest.pojo.Food;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//
//public class Newsfeedview extends AppCompatActivity {
//
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    GridLayoutManager gridLayoutManager;
//    RecyclerView recyclerView;
//    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
//    FirestoreRecyclerOptions firestoreRecyclerOptions;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_newsfeedview);
//
//        recyclerView = findViewById(R.id.rec);
//        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
//
//
//
//        Query loadjob = db.collection("Foods").whereEqualTo("status","pending");
//        firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Food>().setQuery(loadjob, Food.class).build();
//        //adpater set
//
//        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Food, Home.foodholder>(firestoreRecyclerOptions) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull Home.foodholder holder, int position, @NonNull Food model) {
//                holder.name.setText(model.getName());
//                holder.price.setText(model.getPrice()+" ");
//
//                Glide.with(holder.imageView.getContext()).load(model.getImagepath()).into(holder.imageView);
//
//                holder.name.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        Intent intent = new Intent(Home.this,Productview.class);
//                        intent.putExtra("name",getItem(position).getName());
//                        intent.putExtra("price",getItem(position).getPrice());
//                        intent.putExtra("image",getItem(position).getImagepath());
//                        startActivity(intent);
//
//                    }
//
//
//                });
//            }
//
//
//
//            @NonNull
//            @Override
//            public Home.foodholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodview, parent, false);
//                return new Home.foodholder(view);
//            }
//
//
//        };
//
//
//
//        recyclerView.setAdapter(firestoreRecyclerAdapter);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//
//
//    }
//}
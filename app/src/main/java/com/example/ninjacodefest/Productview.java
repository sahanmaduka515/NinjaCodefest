package com.example.ninjacodefest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Productview extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);

        Bundle bundle = getIntent().getExtras();

        String name1 = bundle.getString("name");
        String price1 = bundle.getString("price");
        String image = bundle.getString("image");


        ImageView viewById = findViewById(R.id.imageView3);
        TextView name = findViewById(R.id.productname);
        TextView price = findViewById(R.id.productprice);
        Button Placeorder = findViewById(R.id.Placeorder);

        Glide.with(getApplicationContext()).load(image).into(viewById);

        name.setText(name1);
        price.setText(price1);




        Placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Foods").whereEqualTo("name",name1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if(documents.size()>0){
                            DocumentSnapshot documentSnapshot = documents.get(0);
                            String id = documentSnapshot.getId();
                            db.collection("Foods").document(id).update("status","ITEM_TAKE").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Productview.this, "ITEM_TAKE", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });





                Intent intent = new Intent(Productview.this,Checkout.class);
                intent.putExtra("name",name1);
                intent.putExtra("price",price1);
                startActivity(intent);
            }
        });


    }
}
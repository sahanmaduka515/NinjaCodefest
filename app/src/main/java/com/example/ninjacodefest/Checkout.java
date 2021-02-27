package com.example.ninjacodefest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Checkout extends AppCompatActivity {
TextView t3,t4,ad,ad1,ad2,ad3,ca1,ca2,ca3,ca4;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String price = bundle.getString("price");
        t3 = findViewById(R.id.textView3);
         t4 =findViewById(R.id.textView4);
        ad= findViewById(R.id.ad);
        ad1=findViewById(R.id.ad1);
        ad2=findViewById(R.id.ad2);
        ad3 =findViewById(R.id.ad3);

        ca1=findViewById(R.id.ca1);
        ca2=findViewById(R.id.ca2);
        ca3=findViewById(R.id.ca3);
        ca4 =findViewById(R.id.ca4);

        t3.setText(name);
        t4.setText(price);

        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Checkout.this, "address and card payment successfully", Toast.LENGTH_SHORT).show();
                db.collection("Foods").whereEqualTo("name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if(documents.size()>0){
                            DocumentSnapshot documentSnapshot = documents.get(0);
                            String id = documentSnapshot.getId();
                            db.collection("Foods").document(id).update("status","SOLD").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Checkout.this, "SOLD", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });



            }
        });


    }
}
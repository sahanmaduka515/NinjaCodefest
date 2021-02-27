package com.example.ninjacodefest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ninjacodefest.pojo.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 2;
    private static final int PROFILE_IMAGE = 3;
    StorageReference reference;
    Uri imageuri;
    EditText name;
    EditText email;
    EditText Nic;
    EditText tel;
    Button Register;
    Button button2;
    String foodimagename;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        Nic = findViewById(R.id.Nic);
        tel = findViewById(R.id.tel);
        Register = findViewById(R.id.Register);
        button2 = findViewById(R.id.button2);

        reference = FirebaseStorage.getInstance().getReference();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, CHOOSE_IMAGE);
            }
        });


        Bundle bundle = getIntent().getExtras();
        String name1 = bundle.getString("name");
        String email1 = bundle.getString("email");
        String authid = bundle.getString("authid");
        name.setText(name1);
        email.setText(email1);


        CollectionReference subjectsRef = db.collection("gender");

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final List<String> subjects = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("name");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = name.getText().toString();
                String s1 = email.getText().toString();
                String s2 = Nic.getText().toString();
                String s3 = tel.getText().toString();
                String s4 = spinner.getSelectedItem().toString();
                Customer user = new Customer();
                user.setName(s);
                user.setEmail(s1);
                user.setNic(s2);
                user.setGender(s4);
                user.setTelephone(s3);

                String image = s +".png";
                user.setImage_path(image);


                reference.child("Userimage/"+image).putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });



                CollectionReference users = db.collection("Customer");
                users.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this,"User Add",Toast.LENGTH_SHORT).show();
                        //finishActivity(102);
                        Intent intent = new Intent(Register.this,MainActivity.class);



                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,"User NOtAdd",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }



    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            try {

                ImageView im = findViewById(R.id.imageView);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                System.out.println(getContentResolver());
                im.setImageBitmap(bitmap);
                String s = name.getText().toString();
                foodimagename = "Productimage" + "_" + s + "." + GetFileExtension(imageuri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
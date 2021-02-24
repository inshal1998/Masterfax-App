package com.example.masterfax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class New_Menu_Page extends AppCompatActivity {
    FirebaseAuth mAuth;
    public static int PICK_IMAGE=123;
    StorageReference userprofileref;
    StorageReference getUserprofileref;
    String currentuserid;
    DatabaseReference rootref;
    ImageView imageView1,cart;
    TextView useremail;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__menu__page);
        FirebaseStorage firebaseStorage;
        imageView1=findViewById(R.id.userlogo);
        mAuth=FirebaseAuth.getInstance();
        final ImageView imageView=findViewById(R.id.Logout);
        firebaseStorage=FirebaseStorage.getInstance();
        currentuserid=mAuth.getCurrentUser().getUid();
        rootref= FirebaseDatabase.getInstance().getReference();
        useremail=findViewById(R.id.useremail);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        useremail.setText(firebaseUser.getEmail());
        cart=findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(New_Menu_Page.this,Main_Cart.class);
                startActivity(i);
            }
        });
        userprofileref=firebaseStorage.getInstance().getReference().child("New Model Images");
        userprofileref.child(mAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                Toast.makeText(New_Menu_Page.this, "Aaa rahi hai image", Toast.LENGTH_SHORT).show();
                Picasso.get().load(uri).fit().centerCrop().into(imageView1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(New_Menu_Page.this, "Nahi aa Paygi Image", Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mAuth.signOut();
                finish();
                Toast.makeText(New_Menu_Page.this,"Sucessfully Signout",Toast.LENGTH_LONG).show();
                startActivity(new Intent(New_Menu_Page.this,signin.class));
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,PICK_IMAGE) ;
            }
        });

        ImageView pro=findViewById(R.id.car_pro);

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(New_Menu_Page.this,products.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri uriimg=data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).
                    setAspectRatio(1,1).
                    start(this);

        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                Uri imguri=result.getUri();
                StorageReference filepath=userprofileref.child(currentuserid);
                filepath.putFile(imguri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(New_Menu_Page.this, "Upload Hogai Bhai", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String s=task.getException().toString();
                            Toast.makeText(New_Menu_Page.this,"Nahi Ho Rahi is liye  --->  "+ s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}

package com.example.masterfax;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;

import javax.xml.transform.Result;

public class FirstPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    ImageView nav_userprofileimage;
    TextView nav_name,nav_email;
    public static int PICK_IMAGE=123;
    String currentuserid;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    Uri ImageUri;
    DatabaseReference Rootref;
    StorageReference storageReference;
    StorageReference UserProfile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        currentuserid=mAuth.getCurrentUser().getUid();
        Rootref=FirebaseDatabase.getInstance().getReference(mAuth.getUid());
//      databaseReference=firebaseDatabase.getReference(mAuth.getUid());
        firebaseStorage=FirebaseStorage.getInstance();

        StorageReference myref=storageReference.child(mAuth.getUid());
        UserProfile=FirebaseStorage.getInstance().getReference().child("Model Images");
        storageReference=firebaseStorage.getReference();
       Toast.makeText(this, "On Create Mai hu ", Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        storageReference.child("Model Images").child(mAuth.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                Toast.makeText(FirstPage.this, "Aaa rahi hai image", Toast.LENGTH_SHORT).show();
                Picasso.get().load(uri).fit().centerCrop().into(nav_userprofileimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirstPage.this, "Nahi aa Paygi Image", Toast.LENGTH_SHORT).show();
            }
        });

        updateheader();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        nav_name.setText(firebaseUser.getDisplayName());
        nav_email.setText(firebaseUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logout) {
            signout1();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signout1()
    {
        mAuth.signOut();
        finish();
        Toast.makeText(FirstPage.this,"Sucessfully Signout",Toast.LENGTH_LONG).show();
        startActivity(new Intent(FirstPage.this,signin.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home)
        {


        } else if (id == R.id.menu_aboutus) {

        } else if (id == R.id.menu_products) {

        } else if (id == R.id.menu_services) {

        } else if (id == R.id.menu_contact_us) {

        } else if (id == R.id.menu_bookapointment) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateheader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        nav_userprofileimage = view.findViewById(R.id.image);
        nav_name = view.findViewById(R.id.navname);
        nav_email = view.findViewById(R.id.email);
       // nav_name.setText(firebaseUser.getDisplayName());
//        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        nav_name.setText(firebaseUser.getDisplayName());
        //nav_name.setText(mAuth.getCurrentUser().getDisplayName().toString());
        //        nav_name.setText(mAuth.getCurrentUser().getDisplayName());
//        nav_email.setText(mAuth.getCurrentUser().getEmail());

        nav_userprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent galleryintent=new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK&&data!=null)
        {
            Uri Imageuri=data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                final Uri resulturi=result.getUri();
                final StorageReference filePath=UserProfile.child(currentuserid+".jpg");
                filePath.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(FirstPage.this, "Upload Hogai", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(FirstPage.this, "Phir Sei Kar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}

package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class individual_product extends AppCompatActivity {
    TextView title,price,description;
    ImageView image;
    DatabaseReference ref;
    ElegantNumberButton elegantNumberButton;
    FloatingActionButton floatingActionButton;
    Button add_to_cart;
    String tit,ImageUrl,descriptionn,pricee;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_product);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.product_cart);
        elegantNumberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        description=(TextView)findViewById(R.id.description);
        title=findViewById(R.id.title);
        price=findViewById(R.id.price);
        image=findViewById(R.id.imageView);
        add_to_cart=(Button)findViewById(R.id.add_to_cart);

        ref= FirebaseDatabase.getInstance().getReference().child("products");
        key=getIntent().getStringExtra("key");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(individual_product.this,Main_Cart.class);
                startActivity(i);
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

        ref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                     tit=dataSnapshot.child("title").getValue().toString();
                     ImageUrl=dataSnapshot.child("imageUrl").getValue().toString();
                     descriptionn=dataSnapshot.child("description").getValue().toString();
                     pricee=dataSnapshot.child("price").getValue().toString();
                    Picasso.get().load(ImageUrl).into(image);
                    title.setText(tit);
                    description.setText(descriptionn);
                    price.setText(pricee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addingToCartList()
    {
        Intent cartdata=new Intent(this,Main_Cart.class);
        String savecurrentdate,savecurrenttime;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currentime=new SimpleDateFormat("HH:mm:ss a ");
        savecurrenttime=currentime.format(calfordate.getTime());

        final DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("cart list");
        final HashMap<String,Object> cartmap=new HashMap<>();
        cartmap.put("key",key);
        cartmap.put("title",title.getText().toString());
        cartmap.put("price",price.getText().toString());
        cartmap.put("description",description.getText().toString());
        cartmap.put("savecurrentdate",savecurrentdate);
        cartmap.put("savecurrenttime",savecurrenttime);
        cartmap.put("elegantNumberButton",elegantNumberButton.getNumber());
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        cartlistref.child("User View").child(firebaseUser.getUid())
                .child("Products").child(key)
                .updateChildren(cartmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        cartlistref.child("Admin View").child(firebaseUser.getUid())
                                .child("Products").child(key)
                                .updateChildren(cartmap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(individual_product.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                    }
                                });
                    }
                });

    }
}

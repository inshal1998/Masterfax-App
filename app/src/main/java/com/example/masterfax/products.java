package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class products extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Model, products_Adapter> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("products");
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(databaseReference, Model.class).build();
        showdata();
    }

    public void showdata() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, products_Adapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final products_Adapter holder, final int position, @NonNull final Model model) {
                Picasso.get().load(model.getImageUrl()).into(holder.img, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(products.this, "AA gai image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(products.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.title.setText(model.getTitle());
                holder.price.setText("Price : "+model.getPrice().toString());
                holder.category.setText("Company : "+model.getCategory());
                holder.description.setText(model.getDescription());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(products.this,individual_product.class);
                        i.putExtra("key",getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public products_Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_data, parent, false);
                return new products_Adapter(itemView);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
@Override
public void  onStart()
{
    super.onStart();
    if(firebaseRecyclerAdapter !=null)
    {
        firebaseRecyclerAdapter.startListening();
    }
}
    public void  onStop()
    {
        if(firebaseRecyclerAdapter !=null)
        {
            firebaseRecyclerAdapter.startListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(firebaseRecyclerAdapter !=null)
        {
            firebaseRecyclerAdapter.startListening();
        }
    }
}

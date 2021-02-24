package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main_Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button nextbtn;
    TextView total;
    int totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__cart);
        recyclerView =findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nextbtn=findViewById(R.id.next);
        total=findViewById(R.id.total);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total.setText(String.valueOf(totalprice));
                Intent i=new Intent(Main_Cart.this,ConfirmFinalOrder.class);
                i.putExtra("Total Price",String.valueOf(totalprice));
                startActivity(i);
                finish();
            }
        });

    }

    public void onStart()
    {
        super.onStart();
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference cartlistref= FirebaseDatabase.getInstance().getReference().child("cart list");
        FirebaseRecyclerOptions<cart_data> options=new FirebaseRecyclerOptions.Builder<cart_data>()
                .setQuery(cartlistref.child("User View").child(firebaseUser.getUid()).child("Products"),cart_data.class)
                .build();
        FirebaseRecyclerAdapter<cart_data,cartVieholder> adapter=
                new FirebaseRecyclerAdapter<cart_data, cartVieholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull cartVieholder holder, int position, @NonNull final cart_data model) {
                            holder.product_name.setText("Title : "+model.getTitle());
                            holder.product_quantity.setText("Quantity : "+model.getElegantNumberButton());
                            holder.product_price.setText("Price : "+model.getPrice());

                            int onTypeProductPrice=((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.elegantNumberButton);

                            totalprice=totalprice+onTypeProductPrice;

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CharSequence[] options=new CharSequence[]
                                            {
                                                    "Edit",
                                                    "Delete"
                                            };
                                    AlertDialog.Builder builder=new AlertDialog.Builder(Main_Cart.this);
                                    builder.setTitle("Cart Options");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i)
                                        {
                                            if(i==0)
                                            {
                                                Intent intent=new Intent(Main_Cart.this,individual_product.class);
                                                intent.putExtra("key",model.getKey());
                                                startActivity(intent);
                                            }
                                            if(i==1)
                                            {
                                                cartlistref.child("User View").child(firebaseUser.getUid()).child("Products")
                                                        .child(model.getKey()).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if(task.isSuccessful())
                                                                {
                                                                    Toast.makeText(Main_Cart.this, "Item Removed From Cart", Toast.LENGTH_SHORT).show();
                                                                    Intent intent=new Intent(Main_Cart.this,products.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                                builder.show();
                                }
                            });
                    }
                    @NonNull
                    @Override
                    public cartVieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout_file,parent,false);
                        cartVieholder holder=new cartVieholder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
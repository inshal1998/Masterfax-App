package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrder extends AppCompatActivity {
    EditText edname,edphone_no,edaddress;
    Button confirmOrder;
    String totalAmount="";
    String userfulldata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Your Toatal is : "+totalAmount, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_confirm_final_order);
        edname=findViewById(R.id.name);
        edphone_no=findViewById(R.id.phone_no);
        edaddress=findViewById(R.id.address);
        confirmOrder=findViewById(R.id.confirm);
        confirmOrder.setText("Continue And Pay ₹ " +totalAmount);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edname.getText().toString()))
                {
                    Toast.makeText(ConfirmFinalOrder.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(edphone_no.getText().toString()))
                {
                    Toast.makeText(ConfirmFinalOrder.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(edaddress.getText().toString()))
                {
                    Toast.makeText(ConfirmFinalOrder.this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    confirmOrder();
                    /*final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("cart list").child(firebaseUser.getUid()).child("Products").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            String description=dataSnapshot.child("description").toString();
                            String descrielegantNumberButton=dataSnapshot.child("elegantNumberButton").toString();
                            String price=dataSnapshot.child("price").toString();
                            String title=dataSnapshot.child("title:").toString();

                            String all="Description : "+description+"\n"+
                                    "Total Item : "+descrielegantNumberButton+"\n"+
                                    "Price : "+price+"\n"+
                                    "Title :"+title+"\n";
                            String custemail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            JavaMail javaMailAPI=new JavaMail(ConfirmFinalOrder.this,custemail,"Your Product List",all);
                            javaMailAPI.execute();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                }
            }
        });
    }

    private void confirmOrder()
    {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String savecurrentdate,savecurrenttime;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());
        SimpleDateFormat currentime=new SimpleDateFormat("HH:mm:ss a ");
        savecurrenttime=currentime.format(calfordate.getTime());
        DatabaseReference orderef= FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(firebaseUser.getUid());
        HashMap<String,Object> ordermap=new HashMap<>();
        ordermap.put("totalAmount",totalAmount);
        ordermap.put("name",edname.getText().toString());
        ordermap.put("address",edaddress.getText().toString());
        ordermap.put("phone_no",edphone_no.getText().toString());
        ordermap.put("date",savecurrentdate);
        ordermap.put("time",savecurrenttime);
        ordermap.put("state","not yet Shipped");

        orderef.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("cart list")
                            .child("User View")
                            .child(firebaseUser.getUid())
                            .removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ConfirmFinalOrder.this,  userfulldata, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ConfirmFinalOrder.this,products.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}

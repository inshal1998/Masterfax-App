package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity
{
    EditText email;
    EditText epawrd;
    Button submit;
    EditText ename;
    TextView Ar;
    FirebaseAuth firebaseAuth;
    String rname,rpasword,remail1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_registration);
         email=(EditText)findViewById(R.id.EEmail);
         epawrd=(EditText)findViewById(R.id.Epassword);
         submit=(Button)findViewById(R.id.Submit);
         ename=(EditText)findViewById(R.id.Ename);
         Ar=(TextView)findViewById(R.id.Account_Exsist);
         firebaseAuth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view)
            {
                if(validate())
                {
                    String user_email=email.getText().toString().trim();
                    String user_password=epawrd.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                sendEmailVerification();
                                senduserdata();
                                firebaseAuth.signOut();
                                Toast.makeText(Registration.this, "Registartion Successfull", Toast.LENGTH_LONG).show();
                                finish();
                                Intent i=new Intent(Registration.this,signin.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(Registration.this, "!!! Registartion Unsuccessfull !!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

        });
       Ar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(Registration.this,signin.class);
               startActivity(i);
           }
       });
    }

    public Boolean validate()
    {
        Boolean result=false;
        rname=ename.getText().toString();
        rpasword=epawrd.getText().toString();
        remail1=email.getText().toString();

        if( remail1.isEmpty() || rname.isEmpty() || rpasword.isEmpty())
        {
            Toast.makeText(this, "Please Enter Your Detail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }
        return result;
    }
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                     if(task.isSuccessful())
                     {
                         //senduserdata();
                         Toast.makeText(Registration.this,"Successfully Registered , Verification Mail Has Been Sent",Toast.LENGTH_LONG).show();
                         firebaseAuth.signOut();
                         finish();
                         startActivity(new Intent(Registration.this,signin.class));
                     }
                     else
                     {
                         Toast.makeText(Registration.this,"Email Has'nt Been Sent",Toast.LENGTH_LONG).show();
                     }
                }
            });
        }
    }
    public void senduserdata()
    {
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference(firebaseAuth.getUid());
        user_signin_Db user_signin_db=new user_signin_Db(rname,remail1);
        myref.setValue(user_signin_db);
    }
}
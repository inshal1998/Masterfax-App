package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password_reset extends AppCompatActivity {

    EditText Email;
    Button resetpass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
         Email=(EditText)findViewById(R.id.fp);
         resetpass=(Button)findViewById(R.id.reset);
         firebaseAuth=FirebaseAuth.getInstance();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String useremail=Email.getText().toString().trim();
                if(useremail.equals(""))
                {
                    Toast.makeText(password_reset.this,"Please Enter Email",Toast.LENGTH_LONG).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(password_reset.this,"Password Reset Link has Been Send To Mail",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(password_reset.this,signin.class));
                            }
                            else
                            {
                                Toast.makeText(password_reset.this,"Error in Sending Mail",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
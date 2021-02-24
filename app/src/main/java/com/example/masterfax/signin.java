package com.example.masterfax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class signin extends AppCompatActivity {
    Button sub;
    EditText uname;
    EditText upassword;
    TextView forgotPassword;
    FirebaseAuth firebaseAuth;
    String Uname,Upass;
    ImageButton newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sub=(Button)findViewById(R.id.signin_Submit);
        uname=(EditText)findViewById(R.id.signin_name);
        upassword=(EditText)findViewById(R.id.signin_password);
        forgotPassword=(TextView)findViewById(R.id.fp);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();//This Line Chks with DB if User Has Already Loggedin
        newAccount=findViewById(R.id.NewAccount);


        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(signin.this,New_Menu_Page.class));
        }

        sub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Uname=uname.getText().toString().trim();
                Upass=upassword.getText().toString().trim();
                validate(uname.getText().toString().trim(),upassword.getText().toString().trim());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
                startActivity(new Intent(signin.this,password_reset.class));
            }
        });
        newAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(signin.this,Registration.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void validate(String usename,String usepassword)
    {
        firebaseAuth.signInWithEmailAndPassword(usename,usepassword).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                   // startActivity(new Intent(signin.this,FirstPage.class));
                    // Toast.makeText(signin.this,"Login Successfull",Toast.LENGTH_LONG).show();
                    checkEmailVerfication();
                    //finish();
                }
                else
                {
                    Toast.makeText(signin.this,"Username Or Password Doesnt Match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void checkEmailVerfication()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean flag=firebaseUser.isEmailVerified();
        if(flag)
        {
            finish();
            startActivity(new Intent(signin.this,New_Menu_Page.class));
        }
        else
        {
            Toast.makeText(signin.this,"Please Verify Your Email",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
    }
}

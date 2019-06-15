package com.example.jmanu.financialadviser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.text.Normalizer;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button LogInButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView SignUpTextView;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        progressDialog= new ProgressDialog(this);
        LogInButton = (Button)findViewById(R.id.LogInButton);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText)findViewById(R.id.editTextPassword);
        SignUpTextView= (TextView) findViewById(R.id.SignUpTextView);
        LogInButton.setOnClickListener(this);
        SignUpTextView.setOnClickListener(this);
        if(user!=null)
        {
            //Start your profile activity

            email=user.getEmail();
            finish();
            Intent intent = new Intent(getBaseContext(), FormActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);


        }

    }
    private void UserLogin(){
        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Loggin In");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(MainActivity.this, "Welcome "+email, Toast.LENGTH_SHORT).show();
                            //Profile Acitivity
                            startActivity(new Intent(getApplicationContext(),FormActivity.class));

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
     if(view==LogInButton){

         UserLogin();
     }
     if(view==SignUpTextView){
         finish();
         startActivity(new Intent(this, SignUpActivity.class));
     }


    }



}

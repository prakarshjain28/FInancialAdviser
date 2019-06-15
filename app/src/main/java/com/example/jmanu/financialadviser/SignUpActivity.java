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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextemail,editTextpassword,editTextconfirm_paasword;
    private TextView login;
    Button signup;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private String email,password,confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null){
            finish();
            email=user.getEmail();
            finish();
        }
        progressDialog= new ProgressDialog(this);
        signup=(Button)findViewById(R.id.signup);
        editTextemail= (EditText) findViewById(R.id.editTextEmail);
        editTextpassword= (EditText)findViewById(R.id.editTextPassword);
        editTextconfirm_paasword=(EditText)findViewById(R.id.editTextPassword1);
        login= (TextView) findViewById(R.id.SignUpTextView1);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }
 private void signingup(){
        email=editTextemail.getText().toString().trim();
        password=editTextpassword.getText().toString().trim();
        confirm=editTextconfirm_paasword.getText().toString().trim();
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
     if(TextUtils.isEmpty(confirm))
     {
         Toast.makeText(this, "Please Enter a Password"+password, Toast.LENGTH_SHORT).show();
         return;
     }
     if(!confirm.equals(password)){
         Toast.makeText(this, "Please Enter the same  Password "+password+"frfr "+confirm, Toast.LENGTH_SHORT).show();
         return;
     }
     progressDialog.show();
     progressDialog.setMessage("Signing Up");
     mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();
            if(task.isSuccessful()){

                Intent intent = new Intent(getBaseContext(), FormActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();

            }
            else {
                Toast.makeText(SignUpActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
         }
     });
    }
    @Override
    public void onClick(View v) {
    if(v==login){
        finish();
        Intent intent=new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }
    if(v==signup){
        signingup();
    }
    }
}

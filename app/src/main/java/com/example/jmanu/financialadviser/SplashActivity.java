package com.example.jmanu.financialadviser;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    FirebaseFirestore db3;
    private FirebaseAuth mAuth;
    String email;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler= new Handler();
        mAuth = FirebaseAuth.getInstance();
         user= mAuth.getCurrentUser();


                if (user != null) {
                    email = user.getEmail();
                    db3 = FirebaseFirestore.getInstance();
                    DocumentReference df = db3.collection("verification").document(email);
                    df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    System.out.println("It got here");
                                    Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();

                                } else {

                                }
                            } else {
                                Toast.makeText(SplashActivity.this, "Cant connect to DB", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else{
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                }



    }
}

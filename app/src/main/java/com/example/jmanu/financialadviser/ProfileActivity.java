package com.example.jmanu.financialadviser;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView g1,g2,g3;
    private String email;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email=getIntent().getStringExtra("email");
        setContentView(R.layout.activity_profile);
        g1=(CardView)findViewById(R.id.goalCardView);
        g1.setOnClickListener(this);
        g2=(CardView)findViewById(R.id.goalCardView1);
        g2.setOnClickListener(this);
        g3=(CardView)findViewById(R.id.goalCardView2);
        g3.setOnClickListener(this);
        db=FirebaseFirestore.getInstance();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    @Override
    public void onClick(View v) {
        if(v==g1){
            Intent intent=new Intent(getBaseContext(),GoalActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        if(v==g2) {
            Intent intent=new Intent(getBaseContext(),ShowGoalsActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        if(v==g3) {
            Intent intent = new Intent(getBaseContext(), EditFormActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
}

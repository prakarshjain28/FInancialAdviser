package com.example.jmanu.financialadviser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GoalActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText e1,e2,e3;
    private Button b1;
    String s1,s2,s3,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        email=getIntent().getStringExtra("email");
        e1=(EditText)findViewById(R.id.goalText);
        e2=(EditText)findViewById(R.id.amountText);
        e3=(EditText)findViewById(R.id.timeText);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==b1){
            s1=e1.getText().toString().trim();
            s2=e2.getText().toString().trim();
            s3=e3.getText().toString().trim();
            Intent intent=new Intent(getBaseContext(),ResultActivity.class);
            Bundle extras = new Bundle();
            extras.putString("email",email);
            extras.putString("goal",s1);
            extras.putString("amount",s2);
            extras.putString("time",s3);
            intent.putExtras(extras);

            startActivity(intent);
        }
    }
}

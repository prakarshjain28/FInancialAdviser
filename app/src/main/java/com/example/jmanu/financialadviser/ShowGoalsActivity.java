package com.example.jmanu.financialadviser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowGoalsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db,db1,db2;
    String email ;
    LinearLayout gl,tl;
    ScrollView scr1,scr2;
    String[] a;
    int e=0,e1=0;
    private ProgressDialog progressDialog;
    private View rowView;
    Map<String,Object> goal =new HashMap<>();
    Map<String,Object> assets =new HashMap<>();
    Map<String,Object> surplus =new HashMap<>();
    private RadioButton r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_goals);
        db=FirebaseFirestore.getInstance();
        db1=FirebaseFirestore.getInstance();
        db2=FirebaseFirestore.getInstance();
        email=getIntent().getStringExtra("email");
        gl=findViewById(R.id.gl);
        scr1=findViewById(R.id.scr1);
        scr2=findViewById(R.id.scr2);
        scr2.setVisibility(View.INVISIBLE);
        scr1.setVisibility(View.VISIBLE);
        r1=findViewById(R.id.button21);
        r2=findViewById(R.id.button22);
        r2.setOnClickListener(this);
        r1.setOnClickListener(this);
        tl=findViewById(R.id.tbl);
        progressDialog= new ProgressDialog(this);
        DocumentReference df = db.collection("goals").document(email);

        Task<DocumentSnapshot> task = df.get();
        while(task.isComplete() == false){
            System.out.println("busy wait goals");
        }
        DocumentSnapshot document= task.getResult();
        goal = document.getData();

        Set set = goal.entrySet();
        Iterator itr = set.iterator();
        while(itr.hasNext()){
            Map.Entry entry = (Map.Entry) itr.next();
            if(String.valueOf(entry.getKey()).equals("number")){
                System.out.println("It comes here "+entry.getKey());


            }
            else{
                System.out.println("These ARE THE GOALS "+entry.getValue()+entry.getKey().getClass().getName());
                String values=String.valueOf(entry.getValue());
                onAddField(values,entry.getKey().toString());
                onAddField1(values,entry.getKey().toString());
            }
        }

    }


    public void onAddField(String values, final String key) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       rowView = inflater.inflate(R.layout.field5, null);
        a=values.split(",");
        final TextView textOut2 = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut2.setText("You have invested Rs "+a[0]+" in "+a[1]+" for "+a[2]+" years for "+a[3]);


        System.out.println("The goals are"+goal);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();
        Button buttonRemove = (Button)rowView.findViewById(R.id.button2);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                textlayoutremove(textOut2,key);


            }
        });

        gl.addView(rowView);
    }

    public void onAddField1(String values, final String key) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.field6, null);
        a=values.split(",");
        final TextView textOut2 = (TextView)rowView.findViewById(R.id.textView9);
        final TextView textOut3 = (TextView)rowView.findViewById(R.id.textView10);
        final TextView textOut4 = (TextView)rowView.findViewById(R.id.textView11);
        final TextView textOut5 = (TextView)rowView.findViewById(R.id.textView12);
        textOut2.setText(a[0]);
        textOut3.setText(a[1]);
        textOut4.setText(a[2]);
        textOut2.setText(a[3]);

        System.out.println("The goals are"+goal);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("YOYOYOY"+a[0]+a[1]+a[2]+a[3]);
                tablelayoutremove(a[0],textOut3,textOut4,textOut5,key);

            }
        });

        tl.addView(rowView);
    }

    public void tablelayoutremove(String textOut2,TextView textOut3,TextView textOut4,TextView textOut5,final String key){
    System.out.println("lol table layout");
    String text1 ="";
    String text2 = textOut2;

        String text3=textOut3.getText().toString();
        String text4=textOut4.getText().toString();
        String text5=textOut5.getText().toString();
        text1=text2+","+text3+","+text4+","+text5;
        System.out.println("Text2 is "+text2+text3+text4+text5);
        final int amount=Integer.parseInt(text2);
        final String  reason=text3;
        System.out.println("lol "+text1);
    AlertDialog.Builder builder = new AlertDialog.Builder(ShowGoalsActivity.this);
        builder.setTitle("Confirm dialog demo !");
        builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            goal.remove(key);
            int num=Integer.parseInt(String.valueOf(goal.get("number")))-1;
            goal.put("number",String.valueOf(num));
            System.out.println("The remaining goals are"+goal);
            progressDialog.setMessage("Calculating");
            progressDialog.show();
            if(reason.equals("Fixed Deposit")){
                DocumentReference df = db.collection("assets").document(email);

                Task<DocumentSnapshot> task = df.get();
                while(task.isComplete() == false){
                    System.out.println("busy wait goals");
                }
                DocumentSnapshot document= task.getResult();
                assets = document.getData();

                int x=Integer.parseInt(String.valueOf(assets.get("Cash and Savings")));
                x=x+amount;
                assets.put("Cash and Savings",String.valueOf(x));
                db1.collection("goals").document(email)
                        .set(goal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("Goals have been reduced");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Goals are not reduced");
                            }
                        });
                db2.collection("assets").document(email)
                        .set(assets).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("Assets have been reduced");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Assets are not reduced");
                            }
                        });
                progressDialog.dismiss();
                ((LinearLayout)rowView.getParent()).removeView(rowView);
                Intent intent=new Intent(getBaseContext(),ShowGoalsActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();

            }
            else{
                DocumentReference df = db.collection("surplus").document(email);

                Task<DocumentSnapshot> task = df.get();
                while(task.isComplete() == false){
                    System.out.println("busy wait goals");
                }
                DocumentSnapshot document= task.getResult();
                surplus = document.getData();

                int x=Integer.parseInt(String.valueOf(surplus.get("surplus")));
                x=x+amount;
                System.out.println("lol jk"+x);
                surplus.put("surplus",String.valueOf(x));

                db1.collection("goals").document(email)
                        .set(goal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("Goals have been reduced");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Goals are not reduced");
                            }
                        });
                db2.collection("surplus").document(email)
                        .set(surplus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("Surplus have been reduced");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Surplus are not reduced");
                            }
                        });
                progressDialog.dismiss();
                ((LinearLayout)rowView.getParent()).removeView(rowView);
                Intent intent=new Intent(getBaseContext(),ShowGoalsActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();



            }
        }
    });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    });

        builder.show();

}

    public  void textlayoutremove(TextView textOut2, final String key){
        System.out.println("lol ");
        String text1 ="";
        String text = textOut2.getText().toString();
        Pattern pattern = Pattern.compile("in");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            e=matcher.end();
            e1=matcher.start();
            System.out.println("I found the text "+matcher.group()+" starting at index "+
                    matcher.start()+" and ending at index "+matcher.end());

        }
        text1=text1+text.substring(21,e1-1);
        int c=text.indexOf("for");
        int d=text.indexOf("years");
        final int amount=Integer.parseInt(text.substring(21,e1-1));
        text1=text1+","+text.substring(e+1,c-1);
        final String reason=text.substring(e+1,c-1);
        text1=text1+","+text.substring(c+4,d-1);
        int f=text.lastIndexOf("for");
        text1=text1+","+text.substring(f+4,text.length());

        System.out.println("lol "+text1);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowGoalsActivity.this);
        builder.setTitle("Confirm dialog demo !");
        builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goal.remove(key);
                int num=Integer.parseInt(String.valueOf(goal.get("number")))-1;
                goal.put("number",String.valueOf(num));
                System.out.println("The remaining goals are"+goal);
                progressDialog.setMessage("Calculating");
                progressDialog.show();
                if(reason.equals("Fixed Deposit")){
                    DocumentReference df = db.collection("assets").document(email);

                    Task<DocumentSnapshot> task = df.get();
                    while(task.isComplete() == false){
                        System.out.println("busy wait goals");
                    }
                    DocumentSnapshot document= task.getResult();
                    assets = document.getData();

                    int x=Integer.parseInt(String.valueOf(assets.get("Cash and Savings")));
                    x=x+amount;
                    assets.put("Cash and Savings",String.valueOf(x));
                    db1.collection("goals").document(email)
                            .set(goal).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println("Goals have been reduced");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Goals are not reduced");
                                }
                            });
                    db2.collection("assets").document(email)
                            .set(assets).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println("Assets have been reduced");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Assets are not reduced");
                                }
                            });
                    progressDialog.dismiss();
                    ((LinearLayout)rowView.getParent()).removeView(rowView);
                    Intent intent=new Intent(getBaseContext(),ShowGoalsActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();

                }
                else{
                    DocumentReference df = db.collection("surplus").document(email);

                    Task<DocumentSnapshot> task = df.get();
                    while(task.isComplete() == false){
                        System.out.println("busy wait goals");
                    }
                    DocumentSnapshot document= task.getResult();
                    surplus = document.getData();

                    int x=Integer.parseInt(String.valueOf(surplus.get("surplus")));
                    x=x+amount;
                    System.out.println("lol jk"+x);
                    surplus.put("surplus",String.valueOf(x));

                    db1.collection("goals").document(email)
                            .set(goal).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println("Goals have been reduced");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Goals are not reduced");
                                }
                            });
                    db2.collection("surplus").document(email)
                            .set(surplus).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println("Surplus have been reduced");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Surplus are not reduced");
                                }
                            });
                    progressDialog.dismiss();
                    ((LinearLayout)rowView.getParent()).removeView(rowView);
                    Intent intent=new Intent(getBaseContext(),ShowGoalsActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();

                }
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
        if(v==r1){
           scr1.setVisibility(View.VISIBLE);
           scr2.setVisibility(View.INVISIBLE);
        }
        else{
            scr2.setVisibility(View.VISIBLE);
            scr1.setVisibility(View.INVISIBLE);
        }
    }
}

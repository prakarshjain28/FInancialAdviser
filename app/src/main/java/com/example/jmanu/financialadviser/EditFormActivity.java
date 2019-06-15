package com.example.jmanu.financialadviser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EditFormActivity extends AppCompatActivity implements View.OnClickListener {

    private StateProgressBar stateProgressBar;
    ScrollView scrollView,scr2;
    private LinearLayout Lasset,al,ll,cl,il,cfl;
    private Button back,next;
    private int inf=0,ofl=0;
    private String email,name,phone,age,city,address,surplus;
    private FirebaseFirestore db,db1,db2,db3,db4,db5,db6,db7;
    private EditText editTextName, editTextPhone,editTextCity,editTextAge,editTextAddress;
    private String[] descriptionData = {"Personal Details", "Financial Details"};
    private EditText edittext_var,edittext_var1,edittext_var2,edittext_var3,edittext_var4,edittext_var5;
    private Spinner spinner,spinner1,spinner2,spinner3,spinner4;
    Boolean isFirstRun;
    ProgressDialog progressDialog;
    Map<String,Object> assets= new HashMap<>();
    Map<String,Object> surplus1= new HashMap<>();
    Map<String,Object> liabilities= new HashMap<>();
    Map<String,Object> cash= new HashMap<>();
    Map<String,Object> cash_of= new HashMap<>();
    Map<String,Object> insurance= new HashMap<>();
    Map<String ,Object> mp= new HashMap<>();
    Map<String,Object> surp=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        email=getIntent().getStringExtra("email");
        progressDialog= new ProgressDialog(this);
        liabilities.put("Home/Land/Plot Loan","0");
        liabilities.put("Car Loan","0");
        liabilities.put("Personal Loan","0");
        liabilities.put("Business Liability","0");
        liabilities.put("Credit Card Bill/EMI","0");

        assets.put("House","0");
        assets.put("Land/Plot","0");
        assets.put("Car","0");
        assets.put("Cash and Savings","0");
        assets.put("Gold","0");
        assets.put("Stock","0");
        assets.put("Mutual Fund","0");
        assets.put("Fixed Deposit","0");
        assets.put("Bonds","0");
        assets.put("PPF","0");
        assets.put("EPF","0");

        cash.put("Salary","0");
        cash.put("Businesss","0");
        cash.put("Rental Income","0");
        cash.put("Any Other","0");

        cash_of.put("Househild Expenses","0");
        cash_of.put("Children Education","0");
        cash_of.put("EMI","0");
        cash_of.put("Premium","0");
        mp.put("number","0");
        db=FirebaseFirestore.getInstance();
        db1=FirebaseFirestore.getInstance();
        db2=FirebaseFirestore.getInstance();
        db4=FirebaseFirestore.getInstance();
        db3=FirebaseFirestore.getInstance();
        db5=FirebaseFirestore.getInstance();
        db6=FirebaseFirestore.getInstance();
        db7=FirebaseFirestore.getInstance();
        stateProgressBar = (StateProgressBar) findViewById(R.id.progress);
        stateProgressBar.setStateDescriptionData(descriptionData);
        scrollView=(ScrollView)findViewById(R.id.scr1);
        Lasset = (LinearLayout) findViewById(R.id.Lasset);
        scr2=(ScrollView)findViewById(R.id.scr2);
        edittext_var=(EditText)findViewById(R.id.number_edit_text);
        spinner=(Spinner)findViewById(R.id.type_spinner);
        edittext_var1=(EditText)findViewById(R.id.number_edit_text1);
        spinner1=(Spinner)findViewById(R.id.type_spinner1);
        edittext_var2=(EditText)findViewById(R.id.number_edit_text2);
        spinner2=(Spinner)findViewById(R.id.type_spinner2);
        edittext_var3=(EditText)findViewById(R.id.number_edit_text3);
        spinner3=(Spinner)findViewById(R.id.type_spinner3);
        edittext_var3=(EditText)findViewById(R.id.number_edit_text3);
        spinner3=(Spinner)findViewById(R.id.type_spinner3);
        edittext_var4=(EditText)findViewById(R.id.number_edit_text4);
        spinner4=(Spinner)findViewById(R.id.type_spinner4);
        edittext_var5=(EditText)findViewById(R.id.number_edit_text5);
        al = (LinearLayout) findViewById(R.id.al);
        ll = (LinearLayout) findViewById(R.id.ll);
        cl = (LinearLayout) findViewById(R.id.cl);
        cfl=(LinearLayout) findViewById(R.id.cfl);
        il = (LinearLayout) findViewById(R.id.il);
        scrollView.setVisibility(View.VISIBLE);
        scr2.setVisibility(View.INVISIBLE);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextAddress=(EditText)findViewById(R.id.editTextAddress);
        editTextAge=(EditText)findViewById(R.id.editTextAge);
        editTextCity=(EditText)findViewById(R.id.editTextCity);
        editTextPhone=(EditText)findViewById(R.id.editTextPhone);
        back=(Button)findViewById(R.id.back);
        next=(Button)findViewById(R.id.next);
        back.setOnClickListener(this);
        next.setOnClickListener(this);


        //toolbar=findViewById(R.id.toolbar);
    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        final TextView textOut = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut.setText(edittext_var.getText().toString());
        final TextView textOut1 = (TextView)rowView.findViewById(R.id.number_edit_text2);
        textOut1.setText(spinner.getSelectedItem().toString().trim());
        // Add the new row before the add field button.
        assets.put(spinner.getSelectedItem().toString().trim(),edittext_var.getText().toString());
        System.out.println("The assets are"+assets);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();

        edittext_var.setText(null);
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("lol ");
                String text = textOut.getText().toString();
                String text1 = textOut1.getText().toString();
                System.out.println("lol " + text);
                //Toast.makeText(FormActivity.this, "" + text + " " + text1, Toast.LENGTH_SHORT).show();
                assets.remove(text1, text);
                Set set = assets.entrySet();
                Iterator itr = set.iterator();
                while (itr.hasNext()) {
                    Map.Entry entry = (Map.Entry) itr.next();
                    System.out.println(entry.getKey()+" ok1 "+entry.getValue());
                    Toast.makeText(EditFormActivity.this,entry.getKey()+" ok "+entry.getValue() , Toast.LENGTH_SHORT).show();

                }
                ((LinearLayout) rowView.getParent()).removeView(rowView);
            }
        });

        al.addView(rowView);
    }

    public void onAddField2(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field2, null);
        final TextView textOut2 = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut2.setText(edittext_var2.getText().toString());
        final TextView textOut3 = (TextView)rowView.findViewById(R.id.number_edit_text2);
        textOut3.setText(spinner2.getSelectedItem().toString().trim());
        // Add the new row before the add field button.
        cash.put(spinner2.getSelectedItem().toString().trim(),edittext_var2.getText().toString());
        System.out.println("The assets are"+cash);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();

        edittext_var2.setText(null);
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("lol ");
                String text = textOut2.getText().toString();
                String text1 = textOut3.getText().toString();
                System.out.println("lol "+text);
                Toast.makeText(EditFormActivity.this, ""+text+" "+text1, Toast.LENGTH_SHORT).show();
                cash.remove(text1,text);
                ((LinearLayout)rowView.getParent()).removeView(rowView);
            }
        });

        cl.addView(rowView);
    }
    public void onAddField1(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field1, null);
        final TextView textOut4 = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut4.setText(edittext_var1.getText().toString());
        final TextView textOut5 = (TextView)rowView.findViewById(R.id.number_edit_text2);
        textOut5.setText(spinner1.getSelectedItem().toString().trim());
        // Add the new row before the add field button.
        liabilities.put(spinner1.getSelectedItem().toString().trim(),edittext_var1.getText().toString());
        System.out.println("The assets are"+liabilities);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();

        edittext_var1.setText(null);
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("lol ");
                String text = textOut4.getText().toString();
                String text1 = textOut5.getText().toString();
                System.out.println("lol "+text);
                Toast.makeText(EditFormActivity.this, ""+text+" "+text1, Toast.LENGTH_SHORT).show();
                liabilities.remove(text1,text);
                ((LinearLayout)rowView.getParent()).removeView(rowView);
            }
        });

        ll.addView(rowView);
    }
    public void onAddField3(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field3, null);
        final TextView textOut6 = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut6.setText(edittext_var3.getText().toString());
        final TextView textOut7 = (TextView)rowView.findViewById(R.id.number_edit_text2);
        textOut7.setText(spinner3.getSelectedItem().toString().trim());
        // Add the new row before the add field button.
        cash_of.put(spinner3.getSelectedItem().toString().trim(),edittext_var3.getText().toString());
        System.out.println("The assets are"+insurance);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();

        edittext_var3.setText(null);
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("lol ");
                String text = textOut6.getText().toString();
                String text1 = textOut7.getText().toString();
                System.out.println("lol "+text);
                Toast.makeText(EditFormActivity.this, ""+text+" "+text1, Toast.LENGTH_SHORT).show();
                cash_of.remove(text1,text);
                ((LinearLayout)rowView.getParent()).removeView(rowView);
            }
        });

        cfl.addView(rowView);
    }
    public void onAddField4(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field4, null);
        final TextView textOut8 = (TextView)rowView.findViewById(R.id.number_edit_text1);
        textOut8.setText(edittext_var4.getText().toString());
        final TextView textOut9 = (TextView)rowView.findViewById(R.id.number_edit_text2);
        textOut9.setText(spinner4.getSelectedItem().toString().trim());
        final TextView textOut10 = (TextView)rowView.findViewById(R.id.number_edit_text3);
        textOut10.setText(edittext_var5.getText().toString());
        // Add the new row before the add field button.
        insurance.put(spinner4.getSelectedItem().toString().trim(),edittext_var4.getText().toString()+","+edittext_var5.getText().toString());
        System.out.println("The insurance are"+insurance);
        //Toast.makeText(this, "The assts are", Toast.LENGTH_SHORT).show();

        edittext_var4.setText(null);
        Button buttonRemove = (Button)rowView.findViewById(R.id.delete_button);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                System.out.println("lol ");
                String text = textOut8.getText().toString();
                String text1 = textOut9.getText().toString();
                System.out.println("lol "+text);
                Toast.makeText(EditFormActivity.this, ""+text+" "+text1, Toast.LENGTH_SHORT).show();
                insurance.remove(text1,text);
                ((LinearLayout)rowView.getParent()).removeView(rowView);
            }
        });

        il.addView(rowView);
    }
    public void onDelete(View v) {
        al.removeView((View) v.getParent());
    }
    public void onDelete1(View v) {
        ll.removeView((View) v.getParent());
    }

    @Override
    public void onClick(View v) {
        if(v==next){
            switch(stateProgressBar.getCurrentStateNumber()){
                case 1:
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    scrollView.setVisibility(View.INVISIBLE);
                    scr2.setVisibility(View.VISIBLE);
                    next.setText("Confirm");
                    break;
                case 2:
                    name=editTextName.getText().toString().trim();
                    phone=editTextPhone.getText().toString().trim();
                    age=editTextAge.getText().toString().trim();
                    address=editTextAddress.getText().toString().trim();
                    city=editTextCity.getText().toString().trim();
                    Map<String,Object> user=new HashMap<>();
                    user.put("email",email);
                    user.put("name",name);
                    user.put("phone",phone);
                    user.put("age",age);
                    user.put("adress",address);
                    user.put("city",city);

                    progressDialog.setMessage("Saving Changes");
                    progressDialog.show();

                    Set set = cash.entrySet();
                    Iterator itr = set.iterator();
                    while (itr.hasNext()) {
                        Map.Entry entry = (Map.Entry) itr.next();
                        System.out.println(entry.getKey()+" ok1 "+entry.getValue());
                        String rah=String.valueOf(entry.getValue());
                        inf=Integer.parseInt(rah)+inf;
                        // Toast.makeText(FormActivity.this,entry.getKey()+" ok "+entry.getValue() , Toast.LENGTH_SHORT).show();

                    }
                    Set set1 = cash_of.entrySet();
                    Iterator itr1 = set1.iterator();
                    while (itr1.hasNext()) {
                        Map.Entry entry = (Map.Entry) itr1.next();
                        System.out.println(entry.getKey()+" ok1 "+entry.getValue());
                        String rah=String.valueOf(entry.getValue());
                        ofl=Integer.parseInt(rah)+ofl;
                        //Toast.makeText(FormActivity.this,entry.getKey()+" ok "+entry.getValue() , Toast.LENGTH_SHORT).show();

                    }
                    surplus=String.valueOf(inf-ofl);


                    DocumentReference df = db.collection("surplus").document(email);

                    Task<DocumentSnapshot> task = df.get();
                    while(task.isComplete() == false){
                        System.out.println("busy wait goals");
                    }
                    DocumentSnapshot document= task.getResult();
                    surplus1 = document.getData();

                    int ideal_surplus=Integer.parseInt(String.valueOf(surplus1.get("actual_surplus")));
                    if(ideal_surplus<Integer.parseInt(surplus)){
                        int current_surplus=Integer.parseInt(String.valueOf(surplus1.get("surplus")));
                        int ok=inf-ofl-ideal_surplus+current_surplus;
                        surp.put("surplus",String.valueOf(ok));
                        surp.put("actual_surplus",String.valueOf(ok));
                    }
                    else{
                        Toast.makeText(this, "All your goals have been deleted as surplus has decreased,please make fresh goals", Toast.LENGTH_LONG).show();
                        surp.put("surplus",surplus);
                        surp.put("actual_surplus",surplus);
                        db3.collection("goals").document(email)
                                .set(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditFormActivity.this, "Goal initialisation not done", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }






                    db.collection("users").document(email)
                            .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db1.collection("assets").document(email).set(assets)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            db2.collection("liabilities").document(email).set(liabilities)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            db4.collection("cashflow_inflow").document(email).set(cash)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            db5.collection("cash_outflow").document(email).set(cash_of)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {

                                                                                            db6.collection("insurance").document(email).set(insurance)
                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void aVoid) {

                                                                                                            db7.collection("surplus").document(email).set(surp)
                                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                                           progressDialog.dismiss();
                                                                                                                            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                                                                                                                            intent.putExtra("email",email);
                                                                                                                            startActivity(intent);
                                                                                                                            finish();

                                                                                                                        }
                                                                                                                    })
                                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                                        @Override
                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                            Toast.makeText(EditFormActivity.this, "Surplus Not added"+e, Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                    });
                                                                                                        }
                                                                                                    })
                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                        @Override
                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                            Toast.makeText(EditFormActivity.this, "Insurance Not added"+e, Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    });

                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(EditFormActivity.this, "Cash OutFlow Not added"+e, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });




                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(EditFormActivity.this, "Cash Flow Not added"+e, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });



                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditFormActivity.this, "Liabilities Not added"+e, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditFormActivity.this, "Assets Not added"+e, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditFormActivity.this, "User Not added"+e, Toast.LENGTH_SHORT).show();
                                    String TAG="";

                                }
                            });
//                    Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
//                    intent.putExtra("email",email);
//                    startActivity(intent);

            }
        }
        if(v==back){
            switch(stateProgressBar.getCurrentStateNumber()){
                case 2:
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                    scrollView.setVisibility(View.VISIBLE);
                    scr2.setVisibility(View.INVISIBLE);
                    next.setText("Next");
                    break;

            }

        }
    }



}

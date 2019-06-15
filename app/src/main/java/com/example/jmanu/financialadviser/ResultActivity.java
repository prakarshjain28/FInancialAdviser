package com.example.jmanu.financialadviser;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
 private String email,goal,amount,time,z,paaji,name;
 private int ntime,x=0,y=0,num=0;
 private ProgressDialog progressDialog,pd;
 double namt,interest,fd2,fd,a,b,c,a1,b1,c1;
 private TextView basic,sip1,sip2,sip3,fd1;
 private Button addfullsip1,addfullsip2,addfullsip3,addfullfd1,addfractionsip1,addfractionsip2,addfractionsip3,addfractionfd1;

 private FirebaseFirestore db,db1,db2;
 Map<String,Object > si3 =new HashMap<>();
 Map<String,Object > sip5 =new HashMap<>();
 Map<String,Object > number =new HashMap<>();
 Map<String,Object > updated =new HashMap<>(); 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        pd=new ProgressDialog(this);
        pd.setMessage("Calculating");
        pd.show();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            email = extras.getString("email");
            goal = extras.getString("goal");
            amount = extras.getString("amount");
            time = extras.getString("time");
        }
        db1=FirebaseFirestore.getInstance();
        db2=FirebaseFirestore.getInstance();
         db = FirebaseFirestore.getInstance();
        basic = (TextView) findViewById(R.id.basicText);
        namt = Double.parseDouble(amount);
        ntime = Integer.parseInt(time);
        sip1=(TextView)findViewById(R.id.sip1);
        sip2=(TextView)findViewById(R.id.sip2);
        sip3=(TextView)findViewById(R.id.sip3);
        fd1=(TextView)findViewById(R.id.fd);

        progressDialog= new ProgressDialog(this);

        addfractionsip1=(Button)findViewById(R.id.addfractionsip1);
        addfractionsip2=(Button)findViewById(R.id.addfractionsip2);
        addfractionsip3=(Button)findViewById(R.id.addfractionsip3);
        addfractionfd1=(Button)findViewById(R.id.addfractionfd);
        addfullsip1=(Button)findViewById(R.id.addfullsip1);
        addfullsip2=(Button)findViewById(R.id.addfullsip2);
        addfullsip3=(Button)findViewById(R.id.addfullsip3);
        addfullfd1=(Button)findViewById(R.id.addfullfd);
        addfullsip1.setOnClickListener(this);
        addfullsip2.setOnClickListener(this);
        addfullsip3.setOnClickListener(this);
        addfullfd1.setOnClickListener(this);

        addfractionsip1.setOnClickListener(this);
        addfractionsip2.setOnClickListener(this);
        addfractionsip3.setOnClickListener(this);
        addfractionfd1.setOnClickListener(this);


        basic.setText("Given that you want to save Rs " + amount + " for " + goal + " over a span of " + time + " years, we have listed various methods to save for that ocassion.Given below are few of those methods :- ");

        System.out.println(email+" email here yo");
        DocumentReference docRef = db2.collection("goals").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG="lol";
                System.out.println("IT CAME Here") ;
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                       // number=document.getData();
                        name = document.getString("number");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    System.out.println("get failed with ");
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        DocumentReference df = db.collection("goals").document(email);

        Task<DocumentSnapshot> task = df.get();
        while(task.isComplete() == false){
            System.out.println("busy wait goals");
        }
        DocumentSnapshot document= task.getResult();
        number = document.getData();
        name=document.getString("number");
        System.out.println("SIP3"+number.get("number"));
        pd.dismiss();

        System.out.println("THis is the nunmber "+number);
        System.out.println("THis is the name "+name);
//         Set set = number.entrySet();
//                        Iterator itr = set.iterator();
//                        while (itr.hasNext()) {
//                            Map.Entry entry = (Map.Entry) itr.next();
//                            System.out.println(entry.getKey()+" ok1 "+entry.getValue());
//
//
//                        }



         //paaji=String.valueOf(number.get("number")) ;
       num=Integer.parseInt(name) ;
       num=num+1;
       number.put("number",String.valueOf(num) );
        a = monthly_invest(ntime, namt, 0.12);
        b = monthly_invest(ntime, namt, 0.105);
        c = monthly_invest(ntime, namt, 0.07);
        a1 = monthly_invest_fraction(ntime, namt, 0.12);
        b1 = monthly_invest_fraction(ntime, namt, 0.105);
        c1 = monthly_invest_fraction(ntime, namt, 0.07);

        sip1.setText("You can invest Rs " + (int) a + " monthly in Equity funds with an annual rate to assumed around 12% to achieve that much amount.But if you want to take a loan then only 20% of the amount is enough,which can be achieved by putting Rs " + (int) a1 + " monthly in Equity funds");
        sip2.setText("You can invest Rs " + (int) b + " monthly in Balanced funds with an annual rate to assumed around 10.5% to achieve that much amount.But if you want to take a loan then only 20% of the amount is enough,which can be achieved by putting Rs " + (int) b1 + " monthly in Balanced funds");
        sip3.setText("You can invest Rs " + (int) c + " monthly in Debt funds with an annual rate to assumed around 7% to achieve that much amount.But if you want to take a loan then only 20% of the amount is enough,which can be achieved by putting Rs " + (int) c1 + " monthly in Debt funds");

        fd = one_time_invest(ntime, namt, 0.07);
        fd2=one_time_invest_fraction(ntime,namt,0.07);
        fd1.setText("You can put Rs " + (int) fd + " in Fixed Deposit through which you can recieve your goal amount.But if you want to take a loan then only 20% of the amount is enough,which can be achieved by putting Rs " + (int) fd2 + " in Fixed Deposits");



    }



    static  private double one_time_invest(int ntime, double namt,double rate){
        double fv1,ok=0;
        fv1=fv(.03,ntime,0,namt);
        ok=pv(rate,ntime,0,fv1,0);
        return ok;

    }
    static  private double one_time_invest_fraction(int ntime, double namt,double rate){
        double fv1,ok=0;
        fv1=fv(.03,ntime,0,namt);
        fv1=fv1*0.20;
        ok=pv(rate,ntime,0,fv1,0);
        return ok;

    }
    static private double monthly_invest(int ntime,double namt,double rate){
        double fv1,ok=0;
        fv1=fv(.03,ntime,0,namt);
        ok=pmt(rate/12,ntime*12,0,fv1);
        return ok;
    }
    static private double monthly_invest_fraction(int ntime,double namt,double rate){
        double fv1,ok=0;
        fv1=fv(.03,ntime,0,namt);
        fv1=fv1*0.20;
        ok=pmt(rate/12,ntime*12,0,fv1,0);
        return ok;
    }
    static public double fv(double r, int nper, double pmt, double pv, int type) {
        double fv = -(pv * Math.pow(1 + r, nper) + pmt * (1+r*type) * (Math.pow(1 + r, nper) - 1) / r);
        return fv;
    }

    /**
     * Overloaded fv() call omitting type, which defaults to 0.
     *
     * @see #fv(double, int, double, double, int)
     */
    static public double fv(double r, int nper, double c, double pv) {
        return fv(r, nper, c, pv, 0);
    }
    public static double pv(double r, int n, double y, double f, double t1) {
        boolean t=false;
        double retval = 0;
        if(t1==1){
            t=true;
        }
        else{
            t=false;
        }
        if (r == 0) {
            retval = -1*((n*y)+f);
        }
        else {
            double r1 = r + 1;
            retval =(( ( 1 - Math.pow(r1, n) ) / r ) * (t ? r1 : 1)  * y - f)
                    /
                    Math.pow(r1, n);
        }
        return retval;
    }
    static public double pmt(double r, int nper, double pv, double fv, int type) {
        double pmt = -r * (pv * Math.pow(1 + r, nper) + fv) / ((1 + r*type) * (Math.pow(1 + r, nper) - 1));
        return pmt;
    }
    /**
     * Overloaded pmt() call omitting type, which defaults to 0.
     *
     * @see #pmt(double, int, double, double, int)
     */
    static public double pmt(double r, int nper, double pv, double fv) {
        return pmt(r, nper, pv, fv, 0);
    }

    /**
     * Overloaded pmt() call omitting fv and type, which both default to 0.
     *
     * @see #pmt(double, int, double, double, int)
     */
    static public double pmt(double r, int nper, double pv) {
        return pmt(r, nper, pv, 0);
    }

    @Override
    public void onClick(View v) {
        if(v==addfractionsip1 || v==addfractionsip2 ||v==addfractionsip3 ||v==addfullsip1|| v==addfullsip2 || v==addfullsip3){
             x=0;
             y=0;
           if(v==addfractionsip1){
               x=(int)a1;
               z="Equity Funds";
           }
            if(v==addfractionsip2){
                x=(int)b1;
                z="Balanced Funds";
            }
            if(v==addfractionsip3){
                x=(int)c1;
                z="Debt Funds";
            }
            if(v==addfullsip1){
                x=(int)a;
                z="Equity Funds";
            }
            if(v==addfullsip2){
                x=(int)b;
                z="Balanced Funds";
            }
            if(v==addfullsip3){
               x=(int)c;
               z="Debt Funds";
            }
            progressDialog.setMessage("Calculating");
            progressDialog.show();
            DocumentReference df1 = db.collection("surplus").document(email);

            Task<DocumentSnapshot> task = df1.get();
            while(task.isComplete() == false){
                System.out.println("busy wait");
            }
            DocumentSnapshot document= task.getResult();
            si3 = document.getData();
            System.out.println("SIP3"+si3.get("surplus"));

            name=String.valueOf(si3.get("surplus"));
            y=Integer.parseInt(name) ;
            progressDialog.dismiss();

            if(y>x){

                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                builder.setTitle("Final Confirmation !");
                builder.setMessage("Every change you make will change your Cash Inflow ,Outflow and various other Assets,Liabilities.Are you sure you want to proceed with this plans ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        y=y-x;
                        si3.put("surplus",String.valueOf(y));
                        number.put("amt_goal_"+String.valueOf(num),String.valueOf(x)+","+ z+","+ntime+","+goal);


                        db.collection("surplus").document(email)
                                .set(si3).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    db1.collection("goals").document(email)
                                            .set(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                               AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                               builder.setTitle("Changes have been made!");
                                               builder.setMessage("Your monthly surplus has been reduce to Rs "+String.valueOf(y)+" and this goal has been saved to the database");
                                               builder.setCancelable(true);
                                               builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                      Intent intent=new Intent(getBaseContext(),ProfileActivity.class);
                                                      intent.putExtra("email",email);
                                                       startActivity(intent);
                                                      finish();
                                                   }

                                                     });
                                                     builder.show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ResultActivity.this, "There seems to be a problem in surplus ,try again", Toast.LENGTH_SHORT).show();
                                        }
                                    })    ;
                                
                                }
                                else{
                                    Toast.makeText(ResultActivity.this, "There seems to be a problem in goals ,try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ResultActivity.this, "There seems to be a problem in goals ,try again", Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();



            }
            else{
                Toast.makeText(ResultActivity.this, "There seems to be a problem,as your surplus is less than the monthly investmnt needed.You can reduce your insurance to accommodate the difference", Toast.LENGTH_SHORT).show();

            }



        }
        else{

            if(v==addfullfd1){
                x=(int)fd2;
                z="Fixed Deposit";
            }


            if(v==addfractionfd1){
                x=(int)fd;
                z="Fixed Deposit";
            }


            DocumentReference df = db.collection("assets").document(email);

            Task<DocumentSnapshot> task = df.get();
            while(task.isComplete() == false){
                System.out.println("busy wait goals");
            }
            DocumentSnapshot document= task.getResult();
            sip5 = document.getData();
            paaji=document.getString("Cash and Savings");
            System.out.println("SIP3"+number.get("Cash and Savings"));

            y=Integer.parseInt(paaji);
            if(y>x){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Final Confirmation!");
                builder.setMessage("Every change you make will change your Cash and various other Assets,Liabilities.Are you sure you want to proceed with this plans?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sip5.put("Cash and Savings",String.valueOf(y-x));
                        number.put("amt_goal_"+String.valueOf(num),String.valueOf(x)+","+ z+","+ntime+","+goal);

                        db.collection("assets").document(email)
                                .set(sip5).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    db1.collection("goals").document(email)
                                            .set(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                                                builder.setTitle("Changes have been made!");
                                                builder.setMessage("Your assets has been reduced to Rs "+String.valueOf(y)+" and this goal has been saved to the database");
                                                builder.setCancelable(true);
                                                builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent=new Intent(getBaseContext(),ProfileActivity.class);
                                                        intent.putExtra("email",email);
                                                        finish();
                                                    }
                                                });
                                                builder.show();

                                            }
                                        }
                                    });


                                }

                            }
                        });

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }

        }

    }
}

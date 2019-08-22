package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    //private Button submitBtn;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private  Button bBtn;

    private DocumentReference dRef;
    private static final String NAME_KEY = "Name";
    private static final String ENROL_KEY = "Enrolment";
    private static final String ROLL_KEY = "Roll";
    private static final String BRANCH_KEY = "Branch";
    private static final String SEM_KEY = "Semester";
    private static final String MOB_KEY = "Mobile";


    private EditText mobTxt;
    private EditText semTxt;
    private EditText nmeTxt;
    private EditText branchTxt;
    private EditText rollTxt;
    private EditText enTxt;
    private Button subBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

     //   getActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();

        bBtn=findViewById(R.id.bBtn);

        final String userId=mAuth.getCurrentUser().getUid();

        subBtn=findViewById(R.id.subBtn);
        enTxt=findViewById(R.id.enTxt);
        rollTxt=findViewById(R.id.rollTxt);
        branchTxt=findViewById(R.id.branchTxt);
        nmeTxt=findViewById(R.id.nmeTxt);
        semTxt=findViewById(R.id.semTxt);
        mobTxt=findViewById(R.id.mobTxt);

        firestore=FirebaseFirestore.getInstance();

       dRef=firestore.collection("details").document(userId);



       dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {

               if(task.isSuccessful())
               {
                   if(task.getResult().exists())
                   {
                       String Name=task.getResult().getString(NAME_KEY);
                       String Enrol=task.getResult().getString(ENROL_KEY);
                       String Roll=task.getResult().getString(ROLL_KEY);
                       String Branch=task.getResult().getString(BRANCH_KEY);
                       String Sem=task.getResult().getString(SEM_KEY);
                       String Mob=task.getResult().getString(MOB_KEY);


                       nmeTxt.setText(Name);
                       enTxt.setText(Enrol);
                       rollTxt.setText(Roll);
                       branchTxt.setText(Branch);
                       semTxt.setText(Sem);
                       mobTxt.setText(Mob);

                       subBtn.setText("Save Changes");
                   }

               }
               else
               {
                   String error=task.getException().getMessage();
                   Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG);
               }


           }
       });

      subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=nmeTxt.getText().toString();
                String enrol=enTxt.getText().toString();
                String roll=rollTxt.getText().toString();
                String branch=branchTxt.getText().toString();
                String sem=semTxt.getText().toString();
                String mob=mobTxt.getText().toString();

                Map<String,Object> dMap= new HashMap<>();

                dMap.put(NAME_KEY,name);
                dMap.put(ENROL_KEY,enrol);
                dMap.put(ROLL_KEY,roll);
                dMap.put(BRANCH_KEY,branch);
                dMap.put(SEM_KEY,sem);
                dMap.put(MOB_KEY,mob);


                dRef.set(dMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(),"Details Saved",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String Error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error :"+Error,Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });


      bBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              startActivity(new Intent(ProfileActivity.this,MainActivity.class));
          }
      });

    }
}

package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class ContentActivity extends AppCompatActivity {

    private TextView tv,tv1,tv2,tv3;
    private Button retBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DocumentReference reference;
    private static final String TYPE_KEY = "Complaint Type";
    private static final String COMPLAINT_KEY = "Complaint";
    private static final String TIMESTAMP_KEY = "date";
    private static final String ID_KEY = "ComplaintId";

    public static Button dltBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);

        firestore=FirebaseFirestore.getInstance();
        dltBtn=findViewById(R.id.dltBtn);


        tv=findViewById(R.id.tv);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);

        retBtn=findViewById(R.id.retBtn);

        mAuth=FirebaseAuth.getInstance();
        final String ids=getIntent().getStringExtra("ids");
        String userId=mAuth.getCurrentUser().getUid();

        reference=firestore.collection("Complaints").document(userId).collection("User Complaints").document(ids);


        if(getIntent().hasExtra("state")){
            if (getIntent().getStringExtra("state").equals("success")){
                dltBtn.setVisibility(View.GONE);
            }else{
                dltBtn.setVisibility(View.VISIBLE);
            }
        }else{
            dltBtn.setVisibility(View.VISIBLE);
        }

    //    String getc=getIntent().;

   //    Toast.makeText(getApplicationContext(),getc,Toast.LENGTH_LONG).show();
        /*
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {

                        if(document.getId()==ids)
                        {
                            String complaint =document.getString(TYPE_KEY);
                            String complaintType =document.getString(COMPLAINT_KEY);
                            String complaintId =document.getString(ID_KEY);
                            String complaintDate =document.getString(TIMESTAMP_KEY);

                            tv.setText(complaint);
                            tv1.setText(complaintType);
                            tv2.setText(complaintId);
                            tv3.setText(complaintDate);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error :",Toast.LENGTH_LONG);
                        }
                    }

                }
                else
                {
                    String error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG);
                }
            }
        });        */

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document=task.getResult();
                String complaint =document.getString(TYPE_KEY);
                String complaintType =document.getString(COMPLAINT_KEY);
                String complaintId =document.getString(ID_KEY);
                String complaintDate =document.getString(TIMESTAMP_KEY);

                tv.setText(complaint);
                tv1.setText(complaintType);
                tv2.setText(complaintId);
                tv3.setText(complaintDate);
            }
        });


        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContentActivity.this,ComplaintNoActivity.class));
                finish();
            }
        });


    }
}

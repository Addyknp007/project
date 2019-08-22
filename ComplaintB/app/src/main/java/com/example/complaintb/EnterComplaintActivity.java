package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class EnterComplaintActivity extends AppCompatActivity {

    private TextView showTxt;
    private Button subBtn,compBtn,homexBtn;
    private EditText compTxt;
    private ProgressBar compProgress;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private static final String TYPE_KEY = "Complaint Type";
    private static final String COMPLAINT_KEY = "Complaint";
    private static final String TIMESTAMP_KEY = "date";
    private static final String ID_KEY = "ComplaintId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_complaint);

        subBtn=findViewById(R.id.subBtn);
        compBtn=findViewById(R.id.compBtn);
        homexBtn=findViewById(R.id.homexBtn);
        compTxt=findViewById(R.id.compTxt);

        final int random = new Random().nextInt(20000) + 1;

        final String dateString =new SimpleDateFormat("ddMMyy", Locale.getDefault()).format(new Date());

        final String date =new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());

        final String randomId = UUID.randomUUID().toString();
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        String userId=mAuth.getCurrentUser().getUid();
        reference=firestore.collection("Complaints").document(userId).collection("User Complaints");


        compProgress=findViewById(R.id.compProgress);

        showTxt=findViewById(R.id.showTxt);

        showTxt.setText(getIntent().getStringExtra("msg"));

        final String compId="CMP"+dateString+random;


        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                compProgress.setVisibility(View.VISIBLE);

                String text= compTxt.getText().toString();

                if(!TextUtils.isEmpty(text))
                {
                    Map<String,Object> dMap=new HashMap<>();

                    dMap.put(COMPLAINT_KEY,text);
                    dMap.put(TYPE_KEY,getIntent().getStringExtra("msg"));
                    dMap.put(TIMESTAMP_KEY, date);
                    dMap.put(ID_KEY,compId);


                    reference.add(dMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(EnterComplaintActivity.this,MainActivity.class);
                                intent.putExtra("ID",randomId);
                                Toast.makeText(getApplicationContext(),"Complaint no. is "+compId,Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();


                            }
                            else
                            {
                                String Error=task.getException().getMessage();
                                Toast.makeText(getApplicationContext(),"Error :"+Error,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
                    compProgress.setVisibility(View.INVISIBLE);
                }
            }
        });

        compBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnterComplaintActivity.this,ComplaintsActivity.class));
                finish();
            }
        });

        homexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EnterComplaintActivity.this,MainActivity.class));
                finish();
            }
        });




    }
}

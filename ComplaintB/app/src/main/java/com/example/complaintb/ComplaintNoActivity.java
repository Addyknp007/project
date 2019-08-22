package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplaintNoActivity extends AppCompatActivity {

    private ListView compList;
    private ArrayAdapter arrayAdapter;
    //private List list= new ArrayList();
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private CollectionReference dRef;
    private static final String ID_KEY = "ComplaintId";

    private Button buttonX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complaint_no);


        buttonX=findViewById(R.id.buttonX);

        compList=findViewById(R.id.compList);
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        String userId=mAuth.getCurrentUser().getUid();

      //  String ex=getIntent().getStringExtra("state1");


      /*  dRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                       String complaintId=task.getResult().getString(ID_KEY);
                        list.add(complaintId);
                        arrayAdapter =new ArrayAdapter(ComplaintNoActivity.this,R.layout.list_item,list);
                        compList.setAdapter(arrayAdapter);

                        Map<String, Object> map = task.getResult().getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                list.add(entry.getValue().toString());
                            }
                        }

                        //So what you need to do with your list

                    }
                }
                else
                {
                    String Error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),"Error :"+Error,Toast.LENGTH_LONG).show();
                }

            }
        });        */


        firestore.collection("Complaints").document(userId).collection("User Complaints").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();


                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        list.add(document.getString(ID_KEY));

                    }
                    arrayAdapter =new ArrayAdapter(ComplaintNoActivity.this,R.layout.list_item,list);
                    compList.setAdapter(arrayAdapter);
                 //   final String data=task.getResult().getDocuments().get()

                    compList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            String ids= (String) ((TextView) view).getText();
                            String data=task.getResult().getDocuments().get(i).getId();

                          //  Toast.makeText(getApplicationContext(),ids+" 1 "+data, Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(ComplaintNoActivity.this,ContentActivity.class);
                          intent.putExtra("ids",data);
                          intent.putExtra("state","success");
                          startActivity(intent);


                        }
                    });
                  //  Log.d(TAG, list.toString());
                } else {
                    String Error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),"Error :"+Error,Toast.LENGTH_LONG).show();
                }
            }
        });


        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentX=new Intent(ComplaintNoActivity.this,MainActivity.class);
                startActivity(intentX);
                finish();
            }
        });
    }
}

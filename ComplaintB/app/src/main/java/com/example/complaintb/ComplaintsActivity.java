package com.example.complaintb;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;

    private ImageView backTbtn ;
    private List list= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);


        ConstraintLayout linearLayout = findViewById(R.id.linear_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);

        animationDrawable.start();


         listView=findViewById(R.id.listView);

           backTbtn=findViewById(R.id.backTbtn);

       backTbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

       startActivity(new Intent(ComplaintsActivity.this,MainActivity.class));
       finish();
      }
     });


         list.add("Complaint regarding students");
         list.add("Complaint regarding faculty");
         list.add("Complaint regarding a department");
         list.add("Complaint regarding management");
         list.add("Complaint regarding ragging ");
         list.add("Complaint regarding hostel facilities");
         list.add("Complaint regarding college campus");
         list.add("Complaint regarding not working equipments");

         arrayAdapter= new ArrayAdapter(ComplaintsActivity.this,R.layout.list_item,list);
         listView.setAdapter(arrayAdapter);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              if(i==0)
              {
                 Intent i1=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                 i1.putExtra("msg","Complaint regarding students");
                 startActivity(i1);
                 finish();
              }
              if(i==1)
              {
                  Intent i2=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i2.putExtra("msg","Complaint regarding faculty");
                  startActivity(i2);
                  finish();
              }if(i==2)
              {
                  Intent i3=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i3.putExtra("msg","Complaint regarding a department");
                  startActivity(i3);
                  finish();
              }if(i==3)
              {
                  Intent i4=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i4.putExtra("msg","Complaint regarding management");
                  startActivity(i4);
                  finish();
              }if(i==4)
              {
                  Intent i5=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i5.putExtra("msg","Complaint regarding ragging");
                  startActivity(i5);
                  finish();
              }if(i==5)
              {
                  Intent i6=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i6.putExtra("msg","Complaint regarding hostel facilities");
                  startActivity(i6);
                  finish();
              }if(i==6)
              {
                  Intent i7=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i7.putExtra("msg","Complaint regarding college campus");
                  startActivity(i7);
                  finish();
              }if(i==7)
              {
                  Intent i8=new Intent(ComplaintsActivity.this,EnterComplaintActivity.class);
                  i8.putExtra("msg","Complaint regarding not working equipments");
                  startActivity(i8);
                  finish();
              }

          }
         });
    }
}

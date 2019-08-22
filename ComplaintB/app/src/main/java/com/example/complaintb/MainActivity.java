package com.example.complaintb;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


         private FirebaseAuth mAuth;
         private Toolbar toolbarX;
         private Button saveBtn;

         private Button addBtn;
         private Button viewBtn;
         private Button removeBtn;
         private Button aboutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String randomId=getIntent().getStringExtra("IDX");

        saveBtn =findViewById(R.id.subBtn);

        addBtn =findViewById(R.id.addBtn);
        viewBtn =findViewById(R.id.viewBtn);
        removeBtn =findViewById(R.id.removeBtn);
        aboutBtn =findViewById(R.id.aboutBtn);

        toolbarX=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarX);
        getSupportActionBar().setTitle("HOME");
        mAuth=FirebaseAuth.getInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,ComplaintsActivity.class));
                finish();
            }
        });

       viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,ComplaintNoActivity.class));
               finish();
            }
        });

       removeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent=new Intent(MainActivity.this,ComplaintNoActivity.class);
               startActivity(intent);
               intent.putExtra("state1","success");
               finish();
           }
       });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.item1:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
              //  saveBtn.setText("Save Changes");
                finish();
                return true;

            case R.id.item2:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,methodsActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser==null)
        {
            startActivity(new Intent(MainActivity.this,methodsActivity.class));
            finish();
        }
    }
}


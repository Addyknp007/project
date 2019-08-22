package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class methodsActivity extends AppCompatActivity {

    private Button studentBtn,anonyBtn,facultyBtn;
    private ProgressBar stProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_methods);

        studentBtn=findViewById(R.id.studentBtn);
        facultyBtn=findViewById(R.id.facultyBtn);
        anonyBtn=findViewById(R.id.anonyBtn);
        stProgress=findViewById(R.id.stProgress);

        mAuth=FirebaseAuth.getInstance();

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stProgress.setVisibility(View.VISIBLE);
                startActivity(new Intent(methodsActivity.this,LoginActivity.class));
                finish();
            }
        });

        facultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stProgress.setVisibility(View.VISIBLE);
                startActivity(new Intent(methodsActivity.this,LoginActivity.class));
                finish();
            }
        });

        anonyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stProgress.setVisibility(View.VISIBLE);

                mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(methodsActivity.this,MainActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(),"Signed in Anonymously",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
                            stProgress.setVisibility(View.INVISIBLE);

                        }

                    }
                });
            }
        });


    }
}

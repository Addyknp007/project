package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button resetBtn,backBtn;
    private ProgressBar resetProg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        resetEmail=findViewById(R.id.resetEmail);
        resetBtn=findViewById(R.id.resetBtn);
        backBtn=findViewById(R.id.backLogBtn);
        resetProg=findViewById(R.id.resetProg);

        mAuth=FirebaseAuth.getInstance();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetActivity.this,LoginActivity.class));
                finish();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=resetEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Email cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                resetProg.setVisibility(View.VISIBLE);

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"We have sent a reset link to your mail",Toast.LENGTH_LONG).show();
                            resetProg.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
                            resetProg.setVisibility(View.INVISIBLE);
                        }
                        //resetProg.setVisibility(View.VISIBLE);
                    }
                });
            }
        });



    }
}

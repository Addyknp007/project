package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText signEmail,signPass,signPass1;
    private ProgressBar signProgress;
    private Button signBtn,backLogBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signEmail=findViewById(R.id.signEmail);
        signPass=findViewById(R.id.signPass);
        signPass1=findViewById(R.id.signPass1);
        signProgress=findViewById(R.id.signProgress);
        signBtn=findViewById(R.id.signBtn);
        backLogBtn=findViewById(R.id.backLogBtn);

        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
        }


        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=signEmail.getText().toString();
                final String pass=signPass.getText().toString();
                final String pass1=signPass1.getText().toString();


                signProgress.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(getApplicationContext(), "Enter confirm password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!pass.equals(pass1))
                {
                    Toast.makeText(getApplicationContext(),"password not matching",Toast.LENGTH_LONG).show();
                    signProgress.setVisibility(View.INVISIBLE);
                }

                else {
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            } else {

                                String error = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "Error :" + error, Toast.LENGTH_LONG).show();

                            }

                            signProgress.setVisibility(View.VISIBLE);


                        }
                    });
                }



            }
        });

        backLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });



    }
}

package com.example.complaintb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

  private EditText emailText,passText;
  private FirebaseAuth mAuth;
  private Button loginBtn,resetBtn,signupBtn;
  private ProgressBar progressBar;
  //   private Toolbar toolbar;
  private ImageView backImg ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_login);




    mAuth= FirebaseAuth.getInstance();

    if (mAuth.getCurrentUser() != null) {
      startActivity(new Intent(LoginActivity.this, MainActivity.class));
      finish();
    }




    emailText=findViewById(R.id.emailText);
    passText=findViewById(R.id.passText);

    loginBtn=findViewById(R.id.loginBtn);
    resetBtn=findViewById(R.id.resetBtn);
    signupBtn=findViewById(R.id.signupBtn);
    progressBar=findViewById(R.id.progressBar);
    backImg=findViewById(R.id.backImg);

    mAuth=FirebaseAuth.getInstance();

    signupBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
        finish();            }
    });


    resetBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this,ResetActivity.class));
        finish();
      }
    });


    backImg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        startActivity(new Intent(LoginActivity.this,methodsActivity.class));
        finish();
      }
    });

    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String email= emailText.getText().toString();
        final String password = passText.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(email)) {
          Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
          progressBar.setVisibility(View.INVISIBLE);

          return;
        }

        if (TextUtils.isEmpty(password)) {
          Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
          progressBar.setVisibility(View.INVISIBLE);

          return;
        }





        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful())
            {
              startActivity(new Intent(LoginActivity.this,MainActivity.class));
              finish();
            }
            else
            {
              if (password.length() < 6) {
                passText.setError("password must be of atleast 6 characters");
                progressBar.setVisibility(View.INVISIBLE);

              } else {
                String ErrorM=task.getException().getMessage();
                Toast.makeText(LoginActivity.this,"ERROR :"+ErrorM,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

              }
            }


          }
        });

      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

    if(currentUser!=null)
    {
      startActivity(new Intent(LoginActivity.this,MainActivity.class));
      finish();
    }
  }
}

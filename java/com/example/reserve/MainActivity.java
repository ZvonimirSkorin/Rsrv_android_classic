package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ImageView About;
    Button button;
    EditText unosIme;
    EditText unosPassword;
    TextView signUp;
    int ACTIVITY1=1;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView btnForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        About=findViewById(R.id.About);
        button=findViewById(R.id.button);
        unosIme=findViewById(R.id.unosIme);
        unosPassword=findViewById(R.id.unosPassword);
        signUp=findViewById(R.id.signUp);
        firebaseAuth=firebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);
        btnForgotPassword=findViewById(R.id.txtForgotPassword);


        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user != null)
        {finish();
            startActivity(new Intent(MainActivity.this, com.example.reserve.SecondActivity.class));}


        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, com.example.reserve.MainActivity3.class);
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,com.example.reserve.registration.class);
                startActivityForResult(intent, ACTIVITY1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!unosIme.getText().toString().isEmpty() && !unosPassword.getText().toString().isEmpty())
                { validate(unosIme.getText().toString().trim(),unosPassword.getText().toString().trim());}
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,com.example.reserve.ForgotPassword.class));
            }
        });


    }
    private void validate(String userName,String userPassword)
    {   progressDialog.setMessage("Ubrzo ce sve biti na dlanu");
    progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                { progressDialog.dismiss();
                   // Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                   checkEmailVerification();}
                else {progressDialog.dismiss();Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();}

            }
        });}

        private void checkEmailVerification(){

        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();

        if(emailflag)
        {   finish();
            startActivity(new Intent(MainActivity.this,com.example.reserve.SecondActivity.class));}


        else {Toast.makeText(this,"Vertify your email", Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();}
        }

}
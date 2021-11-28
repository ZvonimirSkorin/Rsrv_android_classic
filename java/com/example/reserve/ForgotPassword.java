package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    private EditText etPasswordEmail;
    private Button btnResetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etPasswordEmail=findViewById(R.id.etPasswordEmail);
        btnResetPassword=findViewById(R.id.btnResertPassword);
        firebaseAuth=FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=etPasswordEmail.getText().toString().trim();
                if(userEmail.isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Molimo vas da unesete Email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,"Mail za novu lozinku je poslan", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,com.example.reserve.MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(ForgotPassword.this,"Nešto je pošlo po zlu. Molimo vas da ponovite postupak.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                }


        });
    }
}
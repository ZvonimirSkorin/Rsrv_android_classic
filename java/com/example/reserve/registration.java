package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class registration extends AppCompatActivity {

    EditText newUsername;
   EditText newLastname;
    EditText newEmail;
  EditText newPhonenumber;
  Button btnRegister;
    TextView login;
    FirebaseAuth firebaseAuth;
    ImageView ivProfile;
    String name,email,number;
     StorageReference storageReference;
    private static int PICK_IMAGE=123;
    Uri imagePath;
    private FirebaseStorage firebaseStorage;


    private Boolean validate()
    {
        Boolean istina=true;
        if(newUsername.getText().toString().isEmpty() ||newLastname.getText().toString().isEmpty()
          || newEmail.getText().toString().isEmpty() || newPhonenumber.getText().toString().isEmpty()
        )
        {  istina=false;
            Toast.makeText(registration.this,"Unesi sve podatke",Toast.LENGTH_SHORT).show();
        }
        return istina;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE && data.getData()!=null )
        {   imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                ivProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseStorage=firebaseStorage.getInstance();
        newUsername=findViewById(R.id.newUsername);
        ivProfile=findViewById(R.id.ivProfile);
        newLastname=findViewById(R.id.newLastname);
        newEmail=findViewById(R.id.newEmail);
        newPhonenumber=findViewById(R.id.newPhonenumber);
        login=findViewById(R.id.login);
        btnRegister=findViewById(R.id.btnRegister);
        firebaseAuth=firebaseAuth.getInstance();

        storageReference=firebaseStorage.getReference();

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE);
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    Intent intent=new Intent();
                    intent.putExtra("newUsername", newUsername.getText().toString().trim());
                    intent.putExtra("newLastname",newLastname.getText().toString().trim());
                    intent.putExtra("newEmail",newEmail.getText().toString().trim());
                    intent.putExtra("newPhonenumber",newPhonenumber.getText().toString().trim());
                    String user_email=newEmail.getText().toString().trim();
                    String password=newLastname.getText().toString().trim();
                    name=newUsername.getText().toString().trim();
                    email=newEmail.getText().toString().trim();
                    number=newPhonenumber.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                            sendEmail();


                            }
                            else
                            {Toast.makeText(registration.this,"Registration Failed",Toast.LENGTH_SHORT).show();}


                    }
                    });

                    registration.this.finish();

                }

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registration.this,com.example.reserve.MainActivity.class);
                startActivity(intent);

            }
        });



    }

    private void sendEmail()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(registration.this,"Uspješno ste registrirani. Mail za vertifikaciju je poslan.",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(registration.this,com.example.reserve.MainActivity.class));

                    }
                    else
                    {Toast.makeText(registration.this,"Vertifikacijski mail nije poslan. Pokušajte ponovno.", Toast.LENGTH_SHORT).show();}

                }
            });
        }
        }

          private void sendPic(){
              StorageReference imageReference=storageReference.child(firebaseAuth.getUid()).child("Slike").child("Profilna slika");
              UploadTask uploadTask=imageReference.putFile(imagePath);
              uploadTask.addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(registration.this,"Pic upload failed", Toast.LENGTH_SHORT).show();
                  }
              }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                  }
              });


          }
         private void sendUserData(){
             FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
             DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getUid());

             UserProfile userProfile=new UserProfile(number,email,name);


             myRef.setValue(userProfile);



         }
    }




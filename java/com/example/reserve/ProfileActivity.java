package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reserve.dodatci.Schedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    public Uri imageUri;

    private ImageView ivKorisnikovaslika;
    private TextView tvKorisnikovoIme;
    private TextView tvKorisnikovBroj;
    private TextView tvKorisnikovEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView ivPoziv;
    private ImageView profilPic;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Button btnPromjeni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        tvKorisnikovBroj=findViewById(R.id.tvKorisnikovBroj);
        tvKorisnikovoIme=findViewById(R.id.tvImeKorisnika);
        tvKorisnikovEmail=findViewById(R.id.tvKorisnikovEmail);
        ivKorisnikovaslika=findViewById(R.id.ivKorisnikovaSlika);
        ivPoziv=findViewById(R.id.ivPoziv);
        btnPromjeni=findViewById(R.id.btnPromjeni);
        profilPic=findViewById(R.id.ivKorisnikovaSlika);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        storage =FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        DatabaseReference dataRef2;
        dataRef2=FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ocjenjivanje");


        dataRef2.orderByChild("ocjene").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
  float kakavJekorisnik= (float) 0.0;
  int i=0;
                for(DataSnapshot a:snapshot.getChildren()){
kakavJekorisnik+=Float.parseFloat(a.getValue().toString());
i++;
  }
            TextView ocjena=findViewById(R.id.ocjenaKorisnika);
            kakavJekorisnik=kakavJekorisnik/i;
            float ostatak=(int)(kakavJekorisnik*100%100);

            kakavJekorisnik=(int)kakavJekorisnik+ostatak/100;
            ocjena.setText(String.valueOf(kakavJekorisnik));
            TextView broj=findViewById(R.id.brojOcjenjivaca);
            broj.setText("("+i+")");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        profilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        if(storageReference.child("images/"+"profilna/"+FirebaseAuth.getInstance().getCurrentUser().getUid())!=null )
        storageReference.child("images/"+"profilna/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilPic);
            }
        });

        ivPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+385994734278"));
                startActivity(intent);
            }
        });

        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                tvKorisnikovoIme.setText(userProfile.getUserName().toString().trim());
                tvKorisnikovBroj.setText(userProfile.getUserNumber());
                tvKorisnikovEmail.setText(userProfile.getUserEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null &&data.getData()!=null) {
            imageUri=data.getData();
            profilPic.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void choosePicture(){
       Intent intent=new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(intent,1);


    }


    private void uploadPicture(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Slika se stavlja...");
        pd.show();
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+"profilna/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Snackbar.make(findViewById(android.R.id.content),"Image uploaded",Snackbar.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                pd.setMessage("Salje se");

            }
        });
    }
}


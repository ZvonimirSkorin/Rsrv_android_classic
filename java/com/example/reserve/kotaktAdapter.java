package com.example.reserve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.makeRestartActivityTask;

public class kotaktAdapter extends RecyclerView.Adapter <kotaktAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> myDoes;
    ImageView circleImageView;
    DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    FirebaseAuth firebaseAuth;



    public kotaktAdapter(Context c, ArrayList<String> p){
        context=c;
        myDoes=p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.kotaktjedan, viewGroup, false));

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        reference=FirebaseDatabase.getInstance().getReference().child(myDoes.get(position)).child("userName");

reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        myViewHolder.titledoes.setText(snapshot.getValue().toString());

        Toast.makeText(context, myDoes.get(position), Toast.LENGTH_SHORT).show();
        storage =FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        if(storageReference.child("images/"+"profilna/"+myDoes.get(position) )!=null )
            storageReference.child("images/"+"profilna/"+myDoes.get(position)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(myViewHolder.circleImageView);

                }
            });

        myViewHolder.klikni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context.getApplicationContext(), com.example.reserve.ChatActivity.class);
                intent.putExtra("Korisnik",myDoes.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



    }


    @Override
    public int getItemCount() {
        return myDoes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView titledoes;
        CircleImageView circleImageView;
LinearLayout klikni;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes=itemView.findViewById(R.id.imeKontakta);
            circleImageView=itemView.findViewById(R.id.slikaKontakta);
klikni=itemView.findViewById(R.id.klikni);




        }}
}

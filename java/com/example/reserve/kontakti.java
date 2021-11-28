package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class kontakti extends AppCompatActivity {
    TextView titledoes, descdoes, datedoes;
    DatabaseReference reference;
    RecyclerView ourdoes;
    kotaktAdapter doesAdapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakti);


        ourdoes=findViewById(R.id.recycy);
        reference= FirebaseDatabase.getInstance().getReference();
       String s= reference.getKey();
        Toast.makeText(kontakti.this, s, Toast.LENGTH_SHORT).show();
        list=new ArrayList<String>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        ourdoes.setLayoutManager(layoutManager);
        ourdoes.setHasFixedSize(true);
        ClearAll();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                int m=0;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String ime= dataSnapshot1.getKey();
                    if(!ime.equals("Chate")){
                if(dataSnapshot1.child("userName").exists()){
                    String p=dataSnapshot1.child("userName").getValue().toString();
                    m++;


                    list.add(ime);}}

                }

                doesAdapter=new kotaktAdapter(kontakti.this,list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"No data", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void ClearAll(){
        if(list!=null){
            list.clear();

            if(doesAdapter!=null)
            {
                doesAdapter.notifyDataSetChanged();
            }

        }
        else
            list=new ArrayList<>();
    }
}
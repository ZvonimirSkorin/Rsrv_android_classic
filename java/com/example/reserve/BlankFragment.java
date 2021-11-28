package com.example.reserve;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank, container, false);

        StorageReference storageReference;
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        CircleImageView circleImageView= view.findViewById(R.id.slikanekakva);
        TextView textView=view.findViewById(R.id.imenekoga);
        String s;
        if(storageReference.child("images/"+"profilna/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())!=null )
            storageReference.child("images/"+"profilna/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(circleImageView);
                }
            });
        FirebaseDatabase firebaseDatabase;
        FirebaseAuth firebaseAuth;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        textView.setText("Bok");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                textView.setText(userProfile.getUserName().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        RelativeLayout klikProfil=view.findViewById(R.id.klikMojProfil);
        klikProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getContext(),com.example.reserve.ProfileActivity.class));
            }
        });
        klikProfil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setBackgroundColor(Color.WHITE);

                return false;
            }
        });
        RelativeLayout klikRaspored=view.findViewById(R.id.klikMojRaspored);
        klikRaspored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getContext(),com.example.reserve.dodatci.Schedule.class));
            }
        });
        RelativeLayout klikRulet=view.findViewById(R.id.klikRulet);
        klikRulet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                startActivity(new Intent(getContext(),com.example.reserve.rulet.class));
            }
        });

        return view;



    }
}
package com.example.reserve.dodatci;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reserve.R;
import com.example.reserve.kotaktAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Schedule extends AppCompatActivity {
    RecyclerView recyclerView;
    private StorageReference storageReference;
    FloatingActionButton floatingbtn;
    TextView textView;
    private FirebaseStorage storage;
    FirebaseRecyclerOptions<raspored> options;
    FirebaseRecyclerAdapter<raspored,MyViewHolderZaRaspored> adapter;
    DatabaseReference Dataref;
    DatabaseReference dataRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_schedule);
        recyclerView=findViewById(R.id.rasporednarecikladza);
String podatak= FirebaseAuth.getInstance().getCurrentUser().getUid();
        storage =FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Dataref= FirebaseDatabase.getInstance().getReference().child(podatak).child("raspored");
       LoadData();

    }
void LoadData(){
        Query query;

        query=Dataref.orderByChild("vrijeme").equalTo("14:00-16:00");
    options = new FirebaseRecyclerOptions.Builder<raspored>().setQuery(query, raspored.class).build();
    adapter=new FirebaseRecyclerAdapter<raspored, MyViewHolderZaRaspored>(options) {
        @Override
        protected void onBindViewHolder(@NonNull MyViewHolderZaRaspored holder, int position, @NonNull raspored model) {
            holder.adresa.setText(model.getAdresa());
            holder.firma.setText(model.getImefirme());
            holder.vrijeme.setText(model.getVrijeme());
            holder.datum.setText(model.getDatum());
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cx = holder.imageView.getWidth() / 2;
                    int cy = holder.imageView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(holder.imageView, cx, cy, 0f, cx*2);
                    anim.setDuration(600);
                    // make the view visible and start the animation
holder.imageView.setVisibility(View.VISIBLE);
                    anim.start();

                }
            });
        }

        @NonNull
        @Override
        public MyViewHolderZaRaspored onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rasporedniitem, parent, false);
            return new MyViewHolderZaRaspored(v);
        }

    };
adapter.startListening();
recyclerView.setAdapter(adapter);

}

}
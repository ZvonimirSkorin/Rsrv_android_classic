package com.example.reserve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    LinearLayout LLRestorani;
    DatabaseReference reference;
    TabLayout tabItem;
    TextView persona;
    LinearLayout LLRekreacija;
    LinearLayout llordinacije;
FirebaseDatabase firebaseDatabase;
DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);


        firebaseAuth = FirebaseAuth.getInstance();
        LLRestorani=findViewById(R.id.LLRestorani);
        tabItem=findViewById(R.id.chatich);
        persona=findViewById(R.id.personaliziranaPrvaStranica);
        LLRekreacija=findViewById(R.id.LLrekreacija);
        llordinacije=findViewById(R.id.ordinacije);
        llordinacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondActivity.this,com.example.reserve.kontakti.class);
                startActivity(intent);

            }
        });

        RelativeLayout viewkz=findViewById(R.id.punoikona);
        View myView=findViewById(R.id.fragment);

     myView.setVisibility(View.INVISIBLE);


        ImageView vjuev=findViewById(R.id.sortiranjeNaprvojstranici);
        vjuev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myView.getVisibility()==View.INVISIBLE)
                {  vjuev.animate().rotation(90);
                    TranslateAnimation trns= new TranslateAnimation(-750,0,0,0);
                    trns.setDuration(300);
                    myView.startAnimation(trns);
                    myView.setVisibility(View.VISIBLE);}
                else {
                    { vjuev.animate().rotation(0);
                        TranslateAnimation trns2= new TranslateAnimation(0,-750,0,0);
                        myView.startAnimation(trns2);
trns2.setDuration(300);
trns2.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        myView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
                      }
                }
            }
        });

        String korisnik = firebaseAuth.getCurrentUser().getUid();
        reference=FirebaseDatabase.getInstance().getReference(korisnik);
        String ime= reference.child("userName").toString().trim();

        LLRekreacija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cx = myView.getWidth() / 2;
                int cy = myView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius);
anim.setDuration(2000);
                // make the view visible and start the animation


            }
        });

        LLRestorani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp2();

            }
        });
dataref=FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("userName");


dataref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        persona.setText("Bok, "+snapshot.getValue().toString());
        TranslateAnimation translateAnimation=new TranslateAnimation(1000,0,0,0);
       translateAnimation.setDuration(500);
        persona.startAnimation(translateAnimation);
        persona.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }

    
    
    private void logout(){
        firebaseAuth.signOut();
        finish();
        Intent intent=new Intent(SecondActivity.this,com.example.reserve.MainActivity.class);
        startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
        case R.id.logoffMenu:
        { logout();
        break;}

            case R.id.profileMenu:
            {startActivity(new Intent(SecondActivity.this,com.example.reserve.ProfileActivity.class));
            break;}
            case R.id.settingsMenu:
            {

                 openApp();
                    
                break;}
              }

        return super.onOptionsItemSelected(item);
    }

    public void openApp()
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.mojraspored");
        startActivity(intent);

    }
    public void openApp2()
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.myapp");
        startActivity(intent);

    }
}

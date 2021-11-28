package com.example.reserve;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class rulet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rulet);
        Button button=findViewById(R.id.btnPovecavanje);
        ImageView imageView=findViewById(R.id.pravirulet);
        Random rand = new Random();
ImageView rlet=findViewById(R.id.ruletNazad);
rlet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(rulet.this,com.example.reserve.SecondActivity.class));
    }
});

        EditText unesiKod=findViewById(R.id.unesiKodrulet);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unesiKod.getText().toString().equals("1111")) {
                    int n = rand.nextInt(10) + 5;
                    int m = rand.nextInt(7) + 1;

                    n *= m;
                    float finalN = n * (360 / 8) + 2880;
                    if ((n % 8) == 0)
                        Toast.makeText(rulet.this, "Bravo 200 poena", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 1)
                        Toast.makeText(rulet.this, "Bravo 100 poena", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 2)
                        Toast.makeText(rulet.this, "Bravo 50 poena", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 3)
                        Toast.makeText(rulet.this, "10", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 4)
                        Toast.makeText(rulet.this, "Bravo 1 poena", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 5)
                        Toast.makeText(rulet.this, "Bomba stari!!", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 6)
                        Toast.makeText(rulet.this, "Bravo 1000 poena", Toast.LENGTH_SHORT).show();
                    if (n % 8 == 7)
                        Toast.makeText(rulet.this, "Bravo 500 poena", Toast.LENGTH_SHORT).show();


                    ImageView myView = findViewById(R.id.pravirulet);
                    int cx = myView.getWidth() / 2;
                    int cy = myView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)

                    final Handler handler = new Handler();
                    button.setClickable(false);
                    imageView.setVisibility(View.VISIBLE);
                    Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius);
                    anim.setDuration(2000);
                    ImageView zbic = findViewById(R.id.zbic);
                    TranslateAnimation tansic = new TranslateAnimation(0, 0, -1000, 0);
                    tansic.setDuration(2000);
                    zbic.startAnimation(tansic);
                    zbic.setVisibility(View.VISIBLE);
                    anim.start();

                    final Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zbic.animate().rotation(160).setDuration(100);
                            imageView.animate().rotation(finalN).setDuration(4000);

                        }
                    }, 2000);

                    final Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView1 = findViewById(R.id.zbic);
                            imageView1.animate().rotation(180);
                            button.animate().translationY(2000).setDuration(2000);
                        }
                    }, 5800);


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (shouldAllowBack()) {
            super.onBackPressed();
        } else {

        }
    }

    private boolean shouldAllowBack() {
        return false;
    }
}
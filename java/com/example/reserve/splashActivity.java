package com.example.reserve;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class splashActivity extends AppCompatActivity {

    ImageView ivTop,ivHeart,ivBeat,ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay=200;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivTop=findViewById(R.id.iv_top);
        ivHeart=findViewById(R.id.iv_heart);
        ivBottom=findViewById(R.id.iv_bottom);
        ivBeat=findViewById(R.id.iv_beat);
        textView=findViewById(R.id.text_viewSrce);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.top_wave);
        ivTop.setAnimation(animation1);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(
                ivHeart,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2F)
                );

        objectAnimator.setDuration(500);

        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator.start();


        animatText("ReserVe");
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8-782c-457c-babd-f2e922ba172b")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBeat);

        Animation animation2=AnimationUtils.loadAnimation(this,R.anim.bottom_wave);
        ivBottom.setAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(com.example.reserve.splashActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        },3000);




    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index<=charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animatText(CharSequence cs){
        charSequence=cs;
        index=0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);


    }

}
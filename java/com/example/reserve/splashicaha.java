package com.example.reserve;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splashicaha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashicaha);
        ImageView view=findViewById(R.id.povecavanje1);
        ImageView view2=findViewById(R.id.povecavanje2);
        ImageView view3=findViewById(R.id.povecavanje3);
        TextView textView=findViewById(R.id.eserve);

        final ScaleAnimation growAnim = new ScaleAnimation(1.0f, 1.5f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        final ScaleAnimation shrinkAnim = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);

        growAnim.setDuration(600);
        shrinkAnim.setDuration(600);

        view.setAnimation(growAnim);
        view2.setAnimation(growAnim);
        growAnim.start();

        growAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout relativeLayout=findViewById(R.id.relativeLayout);
                float x=relativeLayout.getWidth()/2;
                TranslateAnimation animate = new TranslateAnimation(0,x, 0, 0);
                animate.setDuration(500);
                TranslateAnimation animate2 = new TranslateAnimation(0,-x, 0, 0);
                animate2.setDuration(500);
                view.startAnimation(animate);
                view2.startAnimation(animate2);

                final ScaleAnimation growAnim2 = new ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                final ScaleAnimation shrinkAnim2 = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);

                growAnim2.setDuration(400);
                shrinkAnim2.setDuration(400);

                view3.setAnimation(growAnim2);

                growAnim2.start();
                growAnim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
view3.setVisibility(View.VISIBLE);
                        final ScaleAnimation growAnim3 = new ScaleAnimation(1f, 0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                        final ScaleAnimation shrinkAnim3 = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);

                        growAnim3.setDuration(400);
                        shrinkAnim3.setDuration(400);

                        view3.setAnimation(growAnim3);
                        growAnim3.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                final ScaleAnimation growAnim4 = new ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                                final ScaleAnimation shrinkAnim4 = new ScaleAnimation(1f, 1.0f, 1F, 0f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                                Button button=findViewById(R.id.btnPovecavanje);
                                growAnim4.setDuration(400);
                                shrinkAnim4.setDuration(400);
                                view.setAnimation(shrinkAnim4);
                                view2.setAnimation(shrinkAnim4);
                                view3.setVisibility(View.INVISIBLE);

                                button.startAnimation(growAnim4);

                                growAnim4.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
button.setVisibility(View.VISIBLE);
view2.setVisibility(View.INVISIBLE);
view.setVisibility(View.INVISIBLE);
                                        final ScaleAnimation growAnim5 = new ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0.5F);
                                        final ScaleAnimation shrinkAnim5 = new ScaleAnimation(1f, 1.0f, 1F, 0f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                                        Button button=findViewById(R.id.btnPovecavanje);
                                        growAnim5.setDuration(1000);
                                        shrinkAnim5.setDuration(400);
                                        TranslateAnimation animate7 = new TranslateAnimation(-400,0, 0, 0);
                                        animate7.setDuration(500);
                                        TranslateAnimation animate8 = new TranslateAnimation(0,-200, 0, 0);
                                        animate8.setDuration(500);
startActivity(new Intent(splashicaha.this,com.example.reserve.MainActivity.class));



                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
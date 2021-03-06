package com.example.angitha.mygame.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.example.angitha.mygame.fragment.CustomSlideFragment;
import com.example.angitha.mygame.fragment.HowToPlayCustomSlideFragment;
import com.example.angitha.mygame.fragment.IntroCustomSlideFragment;
import com.example.angitha.mygame.levels.GameLevels;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    String value = "notFromMenu";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        enableLastSlideAlphaExitTransition(false);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getString("isFromMenu");
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("first_time", false) || value.equalsIgnoreCase("yesFromMenu")) {

            GameLevels.getInstance().gameTour = true;

            addSlide(new IntroCustomSlideFragment());
            addSlide(new HowToPlayCustomSlideFragment());
            addSlide(new CustomSlideFragment());

        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        GameLevels.getInstance().gameTour=false;
        if(value.equalsIgnoreCase("yesFromMenu")){
            finish();
        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time", true);
            editor.apply();

            Intent i = new Intent(IntroActivity.this, GameMenuActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if(value.equalsIgnoreCase("yesFromMenu")){
            GameLevels.getInstance().gameTour=false;
        }
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}

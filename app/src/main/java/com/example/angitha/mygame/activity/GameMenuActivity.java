package com.example.angitha.mygame.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.angitha.mygame.R;
import com.example.angitha.mygame.controller.GameMenuController;
import com.example.angitha.mygame.levels.GameLevels;
import com.example.angitha.mygame.rules.GameRules;
import com.example.angitha.mygame.utils.Constants;
import com.example.angitha.mygame.utils.ForceUpdateChecker;
import com.example.angitha.mygame.utils.PrefUtils;
import com.example.angitha.mygame.view.MenuView;

/**
 * Created by angitha on 1/7/17.
 */

public class GameMenuActivity extends AppCompatActivity implements GameMenuController.MenuControllerListener,ForceUpdateChecker.OnUpdateNeededListener {
    GameLevels gameLevels = GameLevels.getInstance();
    ImageView play_button;
    ImageView mute_button;
    Intent gamePlayIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_game_menu);

        MenuView menuView = findViewById(R.id.menuView);

        play_button =  findViewById(R.id.play);
        mute_button = findViewById(R.id.muteSound);
        mute_button.setImageResource(PrefUtils.getMuteStatus(this, Constants.MUTE_SOUND, true)?R.drawable.music:R.drawable.no_music);

        ScaleAnimation scaleIn = new ScaleAnimation(0.6f, 0.9f, 0.6f, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleIn.setDuration(700);
        scaleIn.setRepeatCount(Animation.INFINITE);
        scaleIn.setRepeatMode(Animation.REVERSE);
        play_button.startAnimation(scaleIn);

        GameMenuController gameMenuController = new GameMenuController(this);
        menuView.setListeners(gameMenuController);
    }

    @Override
    public void onPlay(@NonNull GameRules gameRules) {
        gameLevels.fromMenu = true;
        gamePlayIntent = new Intent(this, GamePlayActivity.class);
        gamePlayIntent.putExtras(gameRules.exportTo(new Bundle()));
        startActivity(gamePlayIntent);
    }

    @Override
    public void showAllLevels() {
        Intent gameLevelsIntent = new Intent(this, LevelsRecyclerActivity.class);
        startActivity(gameLevelsIntent);
    }

    @Override
    public void gameTour() {
        Intent gameTourIntent = new Intent(this, IntroActivity.class);
        gameTourIntent.putExtra("isFromMenu", "yesFromMenu");
        startActivity(gameTourIntent);
    }

    @Override
    public void toggleGameSound() {
       PrefUtils.saveMuteStatus(this, Constants.MUTE_SOUND, !PrefUtils.getMuteStatus(this, Constants.MUTE_SOUND, true));
        mute_button.setImageResource(PrefUtils.getMuteStatus(this, Constants.MUTE_SOUND, true)?R.drawable.music:R.drawable.no_music);
    }

    @Override
    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    @Override
    public void onUpdateNeeded(String updateUrl) {
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.force_update_title)
//                .setMessage(R.string.force_update_message)
//                .setPositiveButton(R.string.update,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                redirectStore(updateUrl);
//                            }
//                        }).setNegativeButton(R.string.later,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                initialize();
//                                dialog.dismiss();
//                            }
//                        }).create();
//        dialog.show();
    }

    @Override
    public void isUpdateNeeded(boolean isUpdateNeeded) {
//        if(!isUpdateNeeded){
//            initialize();
//        }
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

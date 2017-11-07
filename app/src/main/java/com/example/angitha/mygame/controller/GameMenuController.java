package com.example.angitha.mygame.controller;

import android.view.View;

import com.example.angitha.mygame.R;
import com.example.angitha.mygame.rules.GameRules;

/**
 * Created by angitha on 1/7/17.
 */

public class GameMenuController implements View.OnClickListener{
    private final MenuControllerListener mListener;
    private final GameRules mGameRules = new GameRules();

    public GameMenuController(MenuControllerListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.play:mListener.onPlay(mGameRules);
                break;
            case R.id.levels:mListener.showAllLevels();
                break;
            case R.id.info:mListener.aboutInfo();
                break;
            case R.id.gameTour:mListener.gameTour();
                 break;
            case R.id.like:mListener.rateApp();
                break;
            case R.id.share:mListener.shareApp();
                break;
            case R.id.sound:
                break;
            case R.id.achievements:
                break;
           default:break;
        }
    }

    public interface MenuControllerListener {
        /**
         * The method is called by menu controller to inform the
         * menu Activity about to start game
         */
        void onPlay(GameRules gameRules);
        void showAllLevels();
        void gameTour();
        void aboutInfo();
        void rateApp();
        void shareApp();
    }
}

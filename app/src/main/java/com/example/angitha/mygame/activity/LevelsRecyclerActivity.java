package com.example.angitha.mygame.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.angitha.mygame.R;
import com.example.angitha.mygame.adapter.MyLevelsAdapter;
import com.example.angitha.mygame.levels.GameLevels;

public class LevelsRecyclerActivity extends AppCompatActivity{
    RecyclerView recyclerViewLevels;
    int totalNumberOfLevels;
    LinearLayout levelPicker;
    GameLevels mGameLevels = GameLevels.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_levels_recycler);

        recyclerViewLevels = findViewById(R.id.recyclerViewLevels);
        levelPicker = findViewById(R.id.levelPicker);
        totalNumberOfLevels= mGameLevels.getTotalNumberOfLevels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int i;
        Bitmap[] logos = new Bitmap[totalNumberOfLevels];
        String[] levelList = new String[totalNumberOfLevels];

        for(i= 0;i< totalNumberOfLevels; i++) {
            if (i > mGameLevels.getHighestLevelCrossed(getBaseContext())) {
                logos[i] = BitmapFactory.decodeResource(getResources(), R.drawable.locked);
            } else if(i == mGameLevels.getHighestLevelCrossed(getBaseContext())) {
                logos[i] = BitmapFactory.decodeResource(getResources(), R.drawable.open_lock);
            }else{
                logos[i] = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            }
            levelList[i] = "Level " + (i + 1);
        }
        MyLevelsAdapter adapter = new MyLevelsAdapter(getApplicationContext(),levelList, logos);
        recyclerViewLevels.setLayoutManager(new GridLayoutManager(LevelsRecyclerActivity.this, 3));
        recyclerViewLevels.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LevelsRecyclerActivity.this, GameMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void pressBack(View view) {
        Intent intent = new Intent(LevelsRecyclerActivity.this, GameMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

package com.example.snakegame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snakegame.Classes.GameEngine;
import com.example.snakegame.Classes.SnakeView;
import com.example.snakegame.Enums.Direction;
import com.example.snakegame.Enums.GameState;
import com.example.snakegame.R;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    GameEngine gameEngine; // field that contains all the functions of GameEngine class
    Context context = this; // context of GameActivity
    TextView tv; // TextView that shows how many apples you have to eat during the level
    SnakeView snakeView; // // field that contains all the functions of SnakeView class
    int infiApples = 0; // the number of apples for infinity level
    RelativeLayout layout;
    int numLvl = 0;
    Button next, menu, again, keep; // buttons in dialogs that transfer you to menu or to next level, start the level again or continue it
    public static SoundPool sp;
    public static int eatId,stopId,gameOverId,lvl_completeId;
    public static boolean pause;
    private final Handler handler = new Handler();
    private final long updateDelay = 200; // speed of snake
    int applestoeat = 0;
    int presentapplestoeat = 0;
    boolean infilvl = false;
    Dialog d;
    boolean backPressed = false;
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        layout = findViewById(R.id.first_level_layout);
        gameEngine = new GameEngine();
        switch (ChooseLevelActivity.levelnumber){
            case 1: gameEngine.initGame1();
            GameEngine.eatenApples = 0;
            applestoeat = 5;
            numLvl = 1;
            break;

            case 2: gameEngine.initGame2();
            GameEngine.eatenApples = 0;
            applestoeat = 10;
            numLvl = 2;
            layout.setBackgroundColor(Color.BLACK);
            break;

            case 3: gameEngine.initGame3();
            GameEngine.eatenApples = 0;
            layout.setBackgroundColor(Color.BLACK);
            applestoeat = 15;
            numLvl = 3;
            break;

            case 4: gameEngine.initInfiGame();
            infilvl = true;
            GameEngine.eatenApples = 0;
            break;
        }
        tv = findViewById(R.id.textpointer1);
        snakeView = findViewById(R.id.snakeView);
        StartUpdateHandler();
        snakeView.setOnTouchListener(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){ // create sound variable according to sdk level
            AudioAttributes aa = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME).build();
            sp = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(aa).build();
        }
        else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        stopId = sp.load(context,R.raw.pause_sound,1);
        eatId = sp.load(context,R.raw.nom_nom,1);
        gameOverId = sp.load(context,R.raw.game_over,1);
        lvl_completeId = sp.load(context,R.raw.level_completed,1);
    }

    @Override// function that restarts your level
    protected void onResume() {
        super.onResume();
        pause = false;
    }
    @Override // function that stops your level
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    public void StartUpdateHandler() { // handler function that creates movement of snake, and each move checks its state
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!pause) {
                    gameEngine.Update();
                    if (gameEngine.getCurrentGameState() == GameState.Running) { // if the snake doesn`t confront with something, it will continue move with help of recursion
                        handler.postDelayed(this,updateDelay);
                    }
                    if (gameEngine.getCurrentGameState() == GameState.Lost) { // if the snake confronts with something, the level will be stopped
                        onPause();
                        sp.play(gameOverId, 1, 1, 0, 0, 1);
                        saveResult(infiApples);
                        createLoseDialog();
                    }

                    presentapplestoeat = applestoeat - GameEngine.eatenApples;
                    if(!infilvl)
                    {
                     tv.setText(ChooseLevelActivity.name+", you have to eat " + presentapplestoeat + " apples!");
                     CheckResult();
                    }
                    else
                    { tv.setText(ChooseLevelActivity.name +", you have eaten " + GameEngine.eatenApples + " apples!");
                    infiApples = GameEngine.eatenApples;
                    }
                    snakeView.setSnakeViewMap(gameEngine.getMap());
                    snakeView.invalidate();
                }
            }
        }, updateDelay);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) { // onTouch function that connects choice of direction with your finger movement
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newY = event.getY();
                float newX = event.getX();

                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    if (newX > prevX) {
                        gameEngine.UpdateDirection(Direction.East);
                    } else {
                        gameEngine.UpdateDirection(Direction.West);
                    }
                } else {
                    if (newY > prevY) {
                        gameEngine.UpdateDirection(Direction.South);
                    } else {
                        gameEngine.UpdateDirection(Direction.North);
                    }
                }
                break;
        }
        return true;
    }

    public void CheckResult() {
        if (presentapplestoeat == 0) {
            switch (numLvl) {
                case 1:
                    SharedPreferences.Editor editor1 = MainActivity.sp1.edit();
                    editor1.putString("first_level", "completed");
                    editor1.commit();
                    onPause();
                    createVictoryDialog();
                    break;
                case 2:
                    SharedPreferences.Editor editor2 = MainActivity.sp1.edit();
                    editor2.putString("second_level", "completed");
                    editor2.commit();
                    onPause();
                    createVictoryDialog();
                    break;
                case 3:
                    SharedPreferences.Editor editor3 = MainActivity.sp1.edit();
                    editor3.putString("third_level", "completed");
                    editor3.commit();
                    onPause();
                    createFinishDialog();
                    break;
            }
            sp.play(lvl_completeId, 1, 1, 0, 0, 1);
        }
    }

    public void createFinishDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.finish_dialog);
        d.setCancelable(true);
        menu = d.findViewById(R.id.finishBack);
        menu.setOnClickListener(this);
        d.show();
    }
    public void createVictoryDialog() {
        d = new Dialog(this);
        d.setContentView(R.layout.victory_dialog);
        d.setCancelable(true);
        next = d.findViewById(R.id.next);
        menu = d.findViewById(R.id.menu);
        next.setOnClickListener(this);
        menu.setOnClickListener(this);
        d.show();
    }
    public void createLoseDialog() {
        d = new Dialog(this);
        d.setContentView(R.layout.lose_dialog);
        d.setCancelable(true);
        again = d.findViewById(R.id.again);
        menu = d.findViewById(R.id.menu2);
        again.setOnClickListener(this);
        menu.setOnClickListener(this);
        d.show();
    }
    public void createPauseDialog() {
        d = new Dialog(this);
        d.setContentView(R.layout.pause_dialog);
        d.setCancelable(true);
        keep = d.findViewById(R.id.keep);
        again = d.findViewById(R.id.again2);
        menu = d.findViewById(R.id.menu3);
        again.setOnClickListener(this);
        menu.setOnClickListener(this);
        keep.setOnClickListener(this);
        d.show();
    }

    @Override
    public void onClick(View v) {// buttons of dialogs
        if(v == next){
            ChooseLevelActivity.levelnumber++;
            this.recreate();
            d.dismiss();
        }
        else if (v == menu) {
            Intent intent = new Intent(GameActivity.this, ChooseLevelActivity.class);
            if(infilvl){
                saveResult(infiApples);
            }
            startActivity(intent);
            d.dismiss();
        } else if (v == again) {
            this.recreate();
            d.dismiss();
        }else if(v == keep){
            onResume();
            StartUpdateHandler();
            backPressed = false;
            d.dismiss();
        }
    }

    @Override
    public void onBackPressed() { // calls dialog if you press on Back button
        onPause();
        sp.play(stopId, 1, 1, 0, 0, 1);
        createPauseDialog();
    }
    public void saveResult(int newRes){ // saves your best record in SharedPreferences
        SharedPreferences.Editor editor=MainActivity.sp1.edit();
        editor.putString("result",Integer.toString(newRes));
        if(MainActivity.sp1.getString("bestResult",null) == null)
            editor.putString("bestResult",Integer.toString(newRes));
        else if(newRes>Integer.valueOf(MainActivity.sp1.getString("bestResult",null)))
            editor.putString("bestResult",Integer.toString(newRes));
        editor.commit();
    }
}


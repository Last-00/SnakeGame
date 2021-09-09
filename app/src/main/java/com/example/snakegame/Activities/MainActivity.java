package com.example.snakegame.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.example.snakegame.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button start,exit,settings;
    Dialog d;
    public static SharedPreferences sp1; // creation SharedPreference variable
    static boolean newplayer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // fullscreen
        setContentView(R.layout.activity_main);
        sp1 = getSharedPreferences("progressInfo",0);
        start = findViewById(R.id.startGame);
        start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == start){
            // if player is new, then he will be transferred to NewPlayerActivity to write down his data
           if(sp1.getString("name",null)!=null){
               newplayer = false;
               Intent intent = new Intent(this, ChooseLevelActivity.class);
               startActivity(intent);
           }// if player has already written down his data, he will be transferred to ChooseLevelActivity
           else {
               Intent intent = new Intent(this, NewPlayerActivity.class);
               startActivity(intent);
           }
        }
        if(v == exit){
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(v == settings){
            Intent intent= new Intent(this, SettingsActivity.class);
            startActivity(intent);
            d.dismiss();
        }

    }
    @Override
    // pressing button Back creates dialog with buttons of settings and exit
    public void onBackPressed() {
        createExitDialog();
    }
    public void createExitDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.exit_dialog);
        d.setCancelable(true);
        exit = d.findViewById(R.id.exit);
        settings = d.findViewById(R.id.settings);
        exit.setOnClickListener(this);
        settings.setOnClickListener(this);
        d.show();
    }
}
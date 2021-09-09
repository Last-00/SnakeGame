package com.example.snakegame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.snakegame.Classes.PlayService;
import com.example.snakegame.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
Button green,red,yellow,turnOn,turnOff,deletebtn,return_menu, admin_enter;
TextView tvColor;
Intent serviceIntent;
public SharedPreferences.Editor editor=MainActivity.sp1.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// fullscreen
        setContentView(R.layout.activity_settings);
        serviceIntent = new Intent(this, PlayService.class);
        tvColor = findViewById(R.id.tvColor);
        if(MainActivity.sp1.getString("snakeColor",null)!=null) // reference to SharedPreference
            tvColor.setText("Snake color is "+ (MainActivity.sp1.getString("snakeColor",null))); // shows already chosen snake color from SharedPreference
        green = findViewById(R.id.green);
        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);
        admin_enter = findViewById(R.id.ad_ent);
        turnOn = findViewById(R.id.turnOn);
        turnOff = findViewById(R.id.turnOff);
        deletebtn = findViewById(R.id.delete);
        return_menu = findViewById(R.id.return_back);
        return_menu.setOnClickListener(this);
        green.setOnClickListener(this);
        red.setOnClickListener(this);
        yellow.setOnClickListener(this);
        turnOff.setOnClickListener(this);
        turnOn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        admin_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { // onclick with buttons that change color of snake, turn on music, delete player`s data and return button
        if(v == green){
            editor.putString("snakeColor","green");
            editor.commit();
        }
        if(v == red){
            editor.putString("snakeColor","red");
            editor.commit();
        }
        if(v == yellow){
            editor.putString("snakeColor","yellow");
            editor.commit();
        }
        tvColor.setText("Snake color is "+ (MainActivity.sp1.getString("snakeColor",null)));
        if(v == turnOn){
            startService(serviceIntent);
        }
        if(v == turnOff){
            stopService(serviceIntent);
        }
        if(v == deletebtn){
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor=MainActivity.sp1.edit();
            editor.clear();
            editor.commit();
        }
        if(v == return_menu){
            Intent intent= new Intent(this, ChooseLevelActivity.class);
            startActivity(intent);
        }

        if(v == admin_enter){
            editor.putString("first_level", "completed");
            editor.putString("second_level", "completed");
            editor.putString("third_level", "completed");
            editor.commit();
            Intent intent = new Intent(this, ChooseLevelActivity.class);
            startActivity(intent);
        }

    }

}
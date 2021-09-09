package com.example.snakegame.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.snakegame.Classes.BroadcastBattery;
import com.example.snakegame.R;

public class ChooseLevelActivity extends AppCompatActivity implements View.OnClickListener {
TextView playerName, playerRecord, battery_lvl; // TextView for name of player, player`s record and battery level
BroadcastBattery broadcastBattery;
static String name,surname,encoded =""; // player`s name, surname, and encoded image from NewPlayersActivity
ImageView playerIv;// ImageView that gets encoded image of player
public static int levelnumber = 0; // the number showing the number of level you chose
Button level1,level2,level3,infilevel; // buttons that transfer you to the chosen level


public static TextView isCompleted1,isCompleted2,isCompleted3; // TextViews that show whether you completed the level or not


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // makes the activity full screen
        setContentView(R.layout.activity_chose_level);
        MainActivity.sp1 = getSharedPreferences("progressInfo",0); // reference to SharedPreferences
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tool_bar); // reference to self-made toolbar id
        setSupportActionBar(toolbar); // sets a self-made toolbar
        getSupportActionBar().setTitle(null); // removes app`s title from toolbar
        playerName = findViewById(R.id.PlayerName); // reference to player`s name id
        playerIv = findViewById(R.id.myPhoto);// reference to player`s image id
        playerRecord = findViewById(R.id.Record); // reference to player`s record id
        battery_lvl = findViewById(R.id.battery_lvl); // reference to battery level id
        broadcastBattery = new BroadcastBattery(battery_lvl); // create of BroadcastBattery object that gets battery_lvl
        registerReceiver(broadcastBattery,new IntentFilter(Intent.ACTION_BATTERY_CHANGED)); // usage of function that all the time checks the change of the battery level
        if(MainActivity.newplayer == true) { // checks if the player is new, and then gets his data from NewPlayerActivity
            Intent intent = getIntent();
            name = intent.getExtras().getString("name");
            surname = intent.getExtras().getString("surname");
            Bitmap playerBitmap = (Bitmap) intent.getExtras().get("bitmap");
            playerIv.setImageBitmap(playerBitmap);
        }
        else
            { // if the player is not new, then gets his data for SharedPreferences
        name = MainActivity.sp1.getString("name",null);
        surname =MainActivity.sp1.getString("surname",null);
        encoded = MainActivity.sp1.getString("bitmap",null);
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        playerIv.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }
        playerName.setText(name+"  "+surname);
        if(MainActivity.sp1.getString("bestResult",null) != null) // checks if the player`s record exists, and then gets it from SharedPreferences
            playerRecord.setText("Your record is "+MainActivity.sp1.getString("bestResult",null));
        isCompleted1 = findViewById(R.id.isCompleted1);
        isCompleted2 = findViewById(R.id.isCompleted2);
        isCompleted3 = findViewById(R.id.isCompleted3);
        // checks how many level the player completed, and then changes the text near level button on COMPLETED according to progress
        if(MainActivity.sp1.getString("first_level",null) !=null)
            isCompleted1.setText("Completed");
        if(MainActivity.sp1.getString("second_level",null) !=null)
            isCompleted2.setText("Completed");
        if(MainActivity.sp1.getString("third_level",null) !=null)
            isCompleted3.setText("Completed");
        level1 = findViewById(R.id.startBtn1);
        level2 = findViewById(R.id.startBtn2);
        level3 = findViewById(R.id.startBtn3);
        infilevel = findViewById(R.id.startBtn4);
        // use of onClick
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        infilevel.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // the creation of Option Menu
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // adding the buttons for settings and exit from the game
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent intent= new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_exit){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
             // check for SDK version of device
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                finishAndRemoveTask();
            }
            else
                this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return true;
    }
    // stops function of checking battery level
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastBattery);
    }

    @Override
    public void onClick(View v) { // onClick that contains using of level buttons
        if(v == level1) {
             Intent intent = new Intent(this, GameActivity.class);
             levelnumber = 1;
             startActivity(intent);// 1 means go to first level
        }
        if(v == level2) {
            if(MainActivity.sp1.getString("first_level",null) !=null)
            {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            // 2 means go to second level
            levelnumber = 2;
            }
            else Toast.makeText(this, "You haven`t completed first level yet!", Toast.LENGTH_SHORT).show();
        }
        if(v == level3) {
            if(MainActivity.sp1.getString("second_level",null) !=null)
            {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                // 3 means go to third level
                levelnumber = 3;
            }
            else Toast.makeText(this, "You haven`t completed second level yet!", Toast.LENGTH_SHORT).show();
        }
        if(v == infilevel) {
            if(MainActivity.sp1.getString("third_level",null) !=null)
            {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            // 4 means go to infinity level
            levelnumber = 4;
            }
            else Toast.makeText(this, "You haven`t completed all the levels yet!", Toast.LENGTH_SHORT).show();
        }
    }
}

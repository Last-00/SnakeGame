package com.example.snakegame.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class BroadcastBattery extends BroadcastReceiver {
    TextView tv;// TextView that shows level of battery

    public BroadcastBattery(TextView tv) { // constructor of the class BroadcastBattery
        this.tv = tv;
    }

    @Override
    public void onReceive(Context context, Intent intent) { // the function gets the level of battery
        int batteryLevel = intent.getIntExtra("level",0);
        if(batteryLevel!= 0)
            tv.setText(batteryLevel+"%");
    }
}

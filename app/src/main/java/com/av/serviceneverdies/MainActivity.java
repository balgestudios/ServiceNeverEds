package com.av.serviceneverdies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    long currentTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(RequestTaskService.class);
        Log.e(Util.TAG, "onCreate = " + MainActivity.class.getName());
    }


    public void startService(Class cls) {
        Log.e(Util.TAG, "startService = " + MainActivity.class.getName());
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, currentTimeMillis,
                3000, pendingIntent);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(intent);
        } else {
            getApplicationContext().startService(intent);
        }
    }
}

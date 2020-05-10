package com.av.serviceneverdies;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


public class RequestTaskService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(new Intent(this, RequestTaskService.class));
        } else {
            Notification notification = new Notification();
            startForeground(1, notification);
        }
        Log.e(Util.TAG, "onCreate = " + RequestTaskService.class.getName());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e(Util.TAG, "onStartCommand = " + RequestTaskService.class.getName());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(Util.TAG, "onDestroy = " + RequestTaskService.class.getName());
        Intent broadcastIntent = new Intent(this, RequestTaskBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(Util.TAG, "onTaskRemoved = " + RequestTaskService.class.getName());
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(Util.TAG, "onBind = " + RequestTaskService.class.getName());
        return null;

    }


}


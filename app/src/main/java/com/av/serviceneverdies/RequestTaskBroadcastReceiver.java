package com.av.serviceneverdies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class RequestTaskBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(Util.TAG, "onCreate = " + RequestTaskBroadcastReceiver.class.getName());
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
}

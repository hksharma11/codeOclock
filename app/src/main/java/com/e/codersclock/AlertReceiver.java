package com.e.codersclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String tittle=intent.getStringExtra("tittle");
        String message=intent.getStringExtra("message");

        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification(tittle,message);
        notificationHelper.getManager().notify(1,nb.build());


    }
}

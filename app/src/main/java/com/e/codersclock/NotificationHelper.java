package com.e.codersclock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static  final String channel1ID="channel1ID";
    public static final  String channel1Name="Channel 1";

    public static  final String channel2ID="channel2ID";
    public static final  String channel2Name="Channel 2";

    private NotificationManager mManager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);

        createChannels();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels()
    {
        NotificationChannel channel1=new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);


        NotificationChannel channel2=new NotificationChannel(channel2ID,channel2Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel2);
    }

    public  NotificationManager getManager(){
        if (mManager==null)
        {
            mManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        }

        return  mManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coderslogo);

        return  new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.coderslogo)
                .setLargeIcon(bitmap);

    }


    public NotificationCompat.Builder getChannel2Notification(String title, String message)
    {
        return  new NotificationCompat.Builder(getApplicationContext(),channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.coderslogo);

    }
}

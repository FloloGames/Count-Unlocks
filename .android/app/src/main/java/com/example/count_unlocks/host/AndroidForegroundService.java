package com.example.count_unlocks.host;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;

public class AndroidForegroundService extends Service {
    DeviceUnlockManager deviceUnlockManager;
    private static AndroidForegroundService instance;
    public static AndroidForegroundService getInstance(){
        return instance;
    }
    public void openFlutterApp(){
        Intent dialogIntent = new Intent(this, MainActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        // Handle service initialization and logic here
        deviceUnlockManager = new DeviceUnlockManager();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(deviceUnlockManager, filter);



        MainActivity.CallCBMethod("Registered Unlock Receiver");

        final String CHANNEL_ID = "ForegroundService ID";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.launch_background);

            startForeground(1001, notification.build());
            MainActivity.CallCBMethod("");
        } else {
            MainActivity.CallCBMethod("SDK is to old to run foreground service!");
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(deviceUnlockManager != null){
            unregisterReceiver(deviceUnlockManager);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
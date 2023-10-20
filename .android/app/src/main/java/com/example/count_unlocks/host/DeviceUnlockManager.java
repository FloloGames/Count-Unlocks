package com.example.count_unlocks.host;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;

public class DeviceUnlockManager extends BroadcastReceiver {
    //FlutterAppLauncher flutterAppLauncher = new FlutterAppLauncher();

    //private FlutterEngine flutterEngine;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.i("DeviceUnlockManager", "Phone got unlocked!");
            MainActivity.CallCBMethod("Phone got unlocked!");

            AndroidForegroundService foregroundService = AndroidForegroundService.getInstance();
            if(foregroundService == null){
                Log.e("DeviceUnlockManager","foregroundService is null");
                return;
            }

            foregroundService.openFlutterApp();

            // Create an intent to launch the camera app
            //Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            //camIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(camIntent);


            // Launch the Flutter app here
            //Intent flutterIntent = new Intent(context, MainActivity.class);
            //flutterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(flutterIntent);

            //flutterAppLauncher.startFlutterApp();

            // Launch the Flutter activity
            //Intent flutterIntent = FlutterActivity.createDefaultIntent(context);
                    //.initialRoute("/")  // Replace with your desired initial route
                    //.build(context);

            //flutterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //context.startActivity(flutterIntent);

            //FlutterAppLauncher.getInstance().startFlutterApp();

            //Intent appIntent = new Intent(context, MainActivity.class);
            //appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(appIntent);

            //String targetPackageName = "com.example.count_unlocks.host";
            // Create an intent to launch the target app
            //Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(targetPackageName);
            //if (launchIntent != null) {
              //  launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(launchIntent);
           // } else {
                // The target app is not installed or cannot be launched.
                // Handle the error or provide feedback to the user.
          //  }
        }
    }
}

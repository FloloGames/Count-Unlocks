package com.example.count_unlocks.host;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private final String testChannelName = "testChannel";
    private final String testCBChannelName = "testCBChannel";
    private final String testMethodName = "testMethod";
    private static final String testCBMethodName = "testCBMethod";

    private static MethodChannel testCBMethodChannel;


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        //GeneratedPluginRegistrant.registerWith(flutterEngine);

        MethodChannel testMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testChannelName);
        testCBMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testCBChannelName);

        testMethodChannel.setMethodCallHandler((call, result) -> {
            if(call.method.contentEquals(testMethodName)){
                Toast.makeText(this, "TestMethod works!", Toast.LENGTH_LONG).show();
                CallCBMethod("TestMethod works!!!");
                //Log.i("TestMethodChannel", "TestPrint");
            }
        });

        if(!isForegroundServiceRunning()) {
            Intent foregroundServiceIntent = new Intent(this, AndroidForegroundService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(foregroundServiceIntent);
                CallCBMethod("foreground service started!");
            } else {
                CallCBMethod("SDK is to old to run foreground service!");
            }
        }
    }
    public static void CallCBMethod(String msg){
        testCBMethodChannel.invokeMethod(testCBMethodName, msg);
    }

    private  boolean isForegroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(AndroidForegroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}

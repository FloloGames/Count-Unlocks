package com.example.count_unlocks.host;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity implements MethodChannel.MethodCallHandler {
    private final String testChannelName = "testChannel";
    private final String testCBChannelName = "testCBChannel";
    private final String testMethodName = "testMethod";
    private static final String testCBMethodName = "testCBMethod";
    private static final String getUnlockCountMethodName= "getUnlockCount";
    private static MethodChannel testCBMethodChannel;
    private static MethodChannel testMethodChannel;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        //GeneratedPluginRegistrant.registerWith(flutterEngine);

        testMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testChannelName);
        testCBMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testCBChannelName);

        testMethodChannel.setMethodCallHandler(this);

        if(!isForegroundServiceRunning()) {
            Intent foregroundServiceIntent = new Intent(this, AndroidForegroundService.class);

            startForegroundService(foregroundServiceIntent);
            CallCBMethod("foreground service started!");
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

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if(call.method.contentEquals(testMethodName)){
            Toast.makeText(this, "TestMethod works!", Toast.LENGTH_LONG).show();
            CallCBMethod("TestMethod works!!!");
        } else if(call.method.contentEquals(getUnlockCountMethodName)){
            Toast.makeText(this, "TestMethod works!", Toast.LENGTH_LONG).show();
            int unlockCount = AndroidForegroundService.getInstance().getUnlockCount();
            result.success(unlockCount);
        } else {
            result.notImplemented();
        }
    }
}

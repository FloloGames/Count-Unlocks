package com.example.count_unlocks.host;

import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private final String testChannelName = "testChannel";
    private final String testCBChannelName = "testCBChannel";
    private final String testMethodName = "testMethod";
    private static final String testCBMethodName = "testCBMethod";

    private static MethodChannel testCBMethodChannel;

    private DeviceUnlockManager deviceUnlockManager;

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver to prevent memory leaks
        if (deviceUnlockManager != null) {
            unregisterReceiver(deviceUnlockManager);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        deviceUnlockManager = new DeviceUnlockManager();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(deviceUnlockManager, filter);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        MethodChannel testMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testChannelName);
        testCBMethodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), testCBChannelName);

        testMethodChannel.setMethodCallHandler((call, result) -> {
            if(call.method.contentEquals(testMethodName)){
                Toast.makeText(this, "TestMethod works!", Toast.LENGTH_LONG).show();
                CallCBMethod("TestMethod works!");
                //Log.i("TestMethodChannel", "TestPrint");
            }
        });
    }
    public static void CallCBMethod(String msg){
        testCBMethodChannel.invokeMethod(testCBMethodName, msg);
    }
}

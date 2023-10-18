package com.example.count_unlocks.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.flutter.Log;

public class DeviceUnlockManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.i("TICK", "Phone got unlocked!");
            MainActivity.CallCBMethod("Phone got unlocked!");
        }
    }
}

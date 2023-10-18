package com.example.count_unlocks.host;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AndroidBackgroundService extends Service {



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

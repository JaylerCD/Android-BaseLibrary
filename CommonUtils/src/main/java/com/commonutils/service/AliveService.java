package com.commonutils.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.commonutils.R;

/**
 * @author tim
 */
public class AliveService extends Service {

    private final static int ALIVE_SERVICE_ID = 1001;
    private final static int API_KIT_KET = 19;

    static String CHANNEL_ONE_ID = "com.phoneixfairy.app";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < API_KIT_KET) {
            startForeground(ALIVE_SERVICE_ID, new Notification());
        } else {
            Intent innerIntent = new Intent(this, AliveInnerService.class);
            try {
                startService(innerIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            } else {
                startForeground(ALIVE_SERVICE_ID, new Notification());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 给API > 19的平台上用保活手段
     */
    public static class AliveInnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            } else {
                startForeground(ALIVE_SERVICE_ID, new Notification());
            }
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

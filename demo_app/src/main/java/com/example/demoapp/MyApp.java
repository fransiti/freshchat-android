package com.example.demoapp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FreshchatConfig freshchatConfig = new FreshchatConfig("YOUR_APP_ID", "YOUR_APP_KEY");
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);

        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(Freshchat.FRESHCHAT_USER_RESTORE_ID_GENERATED);
        getLocalBroadcastManager().registerReceiver(broadcastReceiver, intentFilter);
    }

    public LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        getLocalBroadcastManager().unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: Save this restoreId to app's backend for restoring across platforms
            String restoreId = Freshchat.getInstance(getApplicationContext()).getUser().getRestoreId();
            Toast.makeText(context, "Restore id: " + restoreId, Toast.LENGTH_SHORT).show();
        }
    };

}

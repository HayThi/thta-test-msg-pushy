package com.thta.develop.pushyapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thta.develop.pushyapp.async.RegisterForPushNotificationsAsync;

import java.util.ArrayList;
import java.util.List;

import me.pushy.sdk.Pushy;

@SuppressLint("LongLogTag")
public class MainActivity extends AppCompatActivity {
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Pushy's internal notification listening service will restart itself.
        Pushy.listen(this);
        setContentView(R.layout.activity_main);

        // Check runtime permission if android version 6.0 and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!arePermissionsEnabled()) {
                requestMultiplePermissions();
            }
        }

        // Asynchronous Implementation
        if (!Pushy.isRegistered(getApplicationContext())) {
            new RegisterForPushNotificationsAsync(getApplicationContext()).execute();
        }
    }

    private boolean arePermissionsEnabled() {
        for (String permission: permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestMultiplePermissions() {
        List<String> remainingPermission = new ArrayList<>();
        for (String permission: permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermission.add(permission);
            }
        }

        ActivityCompat.requestPermissions(this, remainingPermission.toArray(new String[remainingPermission.size()]), 101);
    }

}

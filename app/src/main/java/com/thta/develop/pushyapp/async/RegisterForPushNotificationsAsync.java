package com.thta.develop.pushyapp.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import me.pushy.sdk.Pushy;

@SuppressLint("LongLogTag")
public class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, Exception> {
    Context context;

    public RegisterForPushNotificationsAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Exception doInBackground(Void... voids) {
        try {
            /**
             * Assign a unique token to this device.
             * If user doesn't grant your app WRITE_EXTERNAL_STORAGE,
             * your app call Pushy.register() without storage access as a new device token.
             * */
            String deviceToken = Pushy.register(context);

            // Log it for debugging purposes
            Log.d("RegisterForPushNotificationsAsync", deviceToken + "");

        } catch (Exception e){
            return e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        //Failed?
        if (e != null) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        // Succeeded, optionally do something to alert the user.
    }

}
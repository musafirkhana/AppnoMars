package com.appnometry.appnomars.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.intro.CustomIntro;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.PersistUser;
import com.appnometry.appnomars.util.SharedPreferencesHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class SplashActivity extends Activity {

    private Context context;
    private ProgressBar mProgressBar;
    protected static final int TIMER_RUNTIME = 3000; // in ms --> 10s
    protected boolean mbActive;
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private PersistUser persistUser=new PersistUser();

    /**********************
     * Google Notification Initialization
     *************************************/
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String regId = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        context = this;
        applicationContext = getApplicationContext();

        if(persistUser.getPushId(context).equalsIgnoreCase("")){
            registerInBackground();
        }

        if (!SharedPreferencesHelper.isOnline(this)) {
            AlertDialogHelper.showAlert(context, "Please Connect to internet");

        } else {
            initUi();
        }

    }

    private void initUi() {

        final Thread timerThread = new Thread() {
            @Override
            public void run() {
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive && (waited < TIMER_RUNTIME)) {
                        sleep(5);
                        if (mbActive) {
                            waited += 5;
                        }
                    }
                } catch (InterruptedException e) {
                } finally {
                    onContinue();
                }
            }
        };
        timerThread.start();
    }

    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    public void onContinue() {
        runOnUiThread(new Runnable() {

            public void run() {
                if (sharedPreferencesHelper.getRegId(context).equalsIgnoreCase("")) {
                    Intent home = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(home);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, CustomIntro.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }

    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
        checkPlayServices();
    }

    /*************************
     * Getting Google GCM Push Notification Registration ID
     **************************************/
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    regId = gcmObj.register(AppConstant.GOOGLE_PROJ_ID);
                    persistUser.setPushId(context,regId);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    Log.i("GCM ID", "" + regId);
                    /*Toast.makeText(applicationContext, "Registered with GCM Server successfully.\n\n"
                            + regId, Toast.LENGTH_SHORT).show();*/
                } else {
                    Toast.makeText(applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(
                        applicationContext,
                        "This device supports Play services, App will work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


}

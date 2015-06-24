package com.appnometry.appnomars.intro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.activities.LoginActivity;
import com.appnometry.appnomars.activities.MainActivity;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

/**
 * Created by Ali PC on 6/18/2015.
 */
public class CustomIntro extends AppIntro {
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private Context context;
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(new FirstSlide(), getApplicationContext());
        addSlide(new SecondSlide(), getApplicationContext());
        addSlide(new ThirdSlide(), getApplicationContext());
        addSlide(new FourthSlide(), getApplicationContext());
        context=this;

        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(false);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    private void loadMainActivity(){
        if (sharedPreferencesHelper.getRegId(context).equalsIgnoreCase("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
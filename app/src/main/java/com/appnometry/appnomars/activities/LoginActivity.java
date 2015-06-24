package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.JsonUtility;
import com.appnometry.appnomars.util.PersistUser;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import java.io.IOException;

public class LoginActivity extends Activity implements View.OnClickListener {
    private String emailSting, passwordString, Email = "", Password = "";
    private Context context;
    public String results;
    private ApiImplementation apiImplementation = new ApiImplementation();
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private JsonUtility jsonUtility = new JsonUtility();
    private ProgressDialog progressDialog;

    /*************************Declare UI Inter Faces***************************/
    private Button login_btn;
    private Button btn_register;
    private TextView forgetpass_textview;
    private TextView log_terms_condition;
    private TextView log_privecy;
    private EditText email_edittext;
    private EditText password_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        Log.i("Reg ID from Login", PersistUser.getPushId(context));
        progressDialog = new CustomProgressDialog(this, "Loading...", true);
        initUI();
    }
    /*************************Initialize UI Inter Faces***************************/
    private void initUI() {
        login_btn = (Button) findViewById(R.id.login_btn);
        btn_register = (Button) findViewById(R.id.btn_register);
        forgetpass_textview = (TextView) findViewById(R.id.forgetpass_textview);
        log_terms_condition = (TextView) findViewById(R.id.log_terms_condition);
        log_privecy = (TextView) findViewById(R.id.log_privecy);
        email_edittext = (EditText) findViewById(R.id.lg_email_edittext);
        password_edittext = (EditText) findViewById(R.id.lg_password_edittext);

        emailSting = email_edittext.getText().toString();
        passwordString = password_edittext.getText().toString();
        login_btn.setOnClickListener(this);
        forgetpass_textview.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        log_terms_condition.setOnClickListener(this);
        log_privecy.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (emptyCheck()) {
                        String url = apiImplementation.GenarateFullURLforLogin(email_edittext.getText().toString(),
                                password_edittext.getText().toString(), "Android", PersistUser.getPushId(context));
                        new LoginAsync().execute(url);

                } else {

                }
                break;
            case R.id.forgetpass_textview:
                Intent forgotPass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPass);
                break;
            case R.id.btn_register:
                Intent register = new Intent(LoginActivity.this, RegistgrationActivity.class);
                startActivity(register);
                break;
            case R.id.log_terms_condition:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.appnometry.com/"));
                startActivity(browserIntent);
                break;
            case R.id.log_privecy:
                Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.appnometry.com/"));
                startActivity(privacyIntent);
                break;


        }

    }
    /*********************************Async Task For User Login*****************************/
    private class LoginAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                results = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(params[0]));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (jsonUtility.loginParser(results, context).equalsIgnoreCase("Success")) {
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                progressDialog.dismiss();
                AlertDialogHelper.showAlert(context, jsonUtility.loginParser(results, context));
            }


        }

    }
    /*********************************Empty Check Validation*****************************/
    public boolean emptyCheck() {

        if (email_edittext.getText().toString().length() == 0) {
            email_edittext.setError("Please Enter Email");
            return false;
        }
        email_edittext.setError(null);
        if (password_edittext.getText().toString().length() == 0) {
            password_edittext.setError("Please Enter Password");
            return false;
        }
        password_edittext.setError(null);

        return true;

    }


}

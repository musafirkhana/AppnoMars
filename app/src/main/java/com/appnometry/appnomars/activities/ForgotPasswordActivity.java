package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.CouponAdapter;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.parser.GlobalItemParser;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener {

    private Context context;
    private ProgressDialog progressDialog;
    private String results;
    ApiImplementation apiImplementation = new ApiImplementation();
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private EditText fp_email;
    private Button fp_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        context = this;
        progressDialog = new CustomProgressDialog(this, "Loading...", true);

        initUI();
    }

    private void initUI() {
        fp_email=(EditText)findViewById(R.id.fp_email);
        fp_login_btn=(Button)findViewById(R.id.fp_login_btn);
        fp_login_btn.setOnClickListener(this);

    }
    private class ForgotPassword extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                results = HttpRequest.GetText(HttpRequest
                        .getInputStreamForGetRequest(params[0]));
                Log.i("Result Are ", results);

                if (GlobalItemParser.connect(context, results)) ;
            } catch (JSONException e) {
                e.printStackTrace();
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
            AppConstant.isSearched = false;
            try {
                final JSONObject mainJsonObject = new JSONObject(results);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fp_login_btn:
                String apiURL = apiImplementation.forgotPasswordURL(fp_email.getText().toString());
                Log.i("apiURL Url Are", apiURL);
                new ForgotPassword().execute(apiURL);

                break;
            case R.id.forgetpass_textview:
                break;


        }

    }

  /*  public boolean emptyCheck() {

        if (email_edittext.getText().toString().equalsIgnoreCase("")) {
            return false;
        } else if (password_edittext.getText().toString().equalsIgnoreCase("")) {
            return false;
        } else {
            return true;
        }

    }*/



}

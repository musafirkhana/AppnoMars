package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.VanueListAdapter;
import com.appnometry.appnomars.parser.VanueListParser;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.HttpRequest;

import java.io.IOException;
import java.util.ArrayList;

public class VunueListActivity extends Activity implements
        View.OnClickListener {
    private ProgressDialog progressDialog;
    private Context context;
    public String results;
    ListView listView;
    ArrayAdapter<String> adapter;
    private ApiImplementation apiImplementation = new ApiImplementation();
    ArrayList<String> checkedValue;
    private VanueListAdapter vanueListAdapter;
    private Button btn_cancel, btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vunue_list);
        context = this;
        progressDialog = new CustomProgressDialog(this, "Loading...", true);
        initUI();


    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.lv_vanuelist);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        String url = apiImplementation.GenerateFullUrlforVenueList();
        Log.i("Vanue List Url", url);
        new VanuelistAsync().execute(url);




    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save:
                finish();
                break;
            case R.id.btn_cancel:
                AppConstant.vanueID="";
                AppConstant.vanueName="";
                finish();
                break;
        }
    }

    private class VanuelistAsync extends AsyncTask<String, Void, String> {
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
                Log.i("result", results);
                VanueListParser.connect(context, results);

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
            vanueListAdapter = new VanueListAdapter(context);
            listView.setAdapter(vanueListAdapter);
            vanueListAdapter.notifyDataSetChanged();
            progressDialog.dismiss();

        }

    }
    @Override
    protected void onResume() {
        AppConstant.vanueName="";
        AppConstant.vanueID="";

        super.onResume();
    }


}

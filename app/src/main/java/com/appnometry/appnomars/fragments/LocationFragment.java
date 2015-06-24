package com.appnometry.appnomars.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.CouponAdapter;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.parser.GlobalItemParser;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Daniel on 09.11.2014.
 */
public class LocationFragment extends Fragment {

    private ProgressDialog progressDialog;
    private String results;
    private Context context;
    private ApiImplementation apiImplementation = new ApiImplementation();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);
        context = getActivity();
        initUI(rootView);
        return rootView;
    }

    private void initUI(View view) {
        String apiURL = apiImplementation.GenerateFullUrlforVenueList();
        Log.i("apiURL Url Are", apiURL);
        new GetVanueLisitAsync().execute(apiURL);
    }

    private class GetVanueLisitAsync extends AsyncTask<String, Void, String> {
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

}

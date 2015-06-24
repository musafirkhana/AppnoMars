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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.CouponAdapter;
import com.appnometry.appnomars.adapter.NewsFeedAdapter;
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


/**
 * Created by Daniel on 09.11.2014.
 */
public class CouponFragment extends Fragment {
    private ProgressDialog progressDialog;
    ApiImplementation apiImplementation = new ApiImplementation();
    private Context context;
    private String results;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private CouponAdapter adapter;

    /**
     * ***********************Declare View Component********************
     */
    private GridView coupon_gridview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coupon, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);

        context = getActivity();
        initUI(rootView);

        return rootView;
    }

    public void initUI(View view) {
        coupon_gridview = (GridView) view.findViewById(R.id.coupon_gridview);
        coupon_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "List Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        if (AppConstant.isSearched) {
            String apiURL = apiImplementation.GenarateFullURLforBenefitCuponSearch("Boom", sharedPreferencesHelper.getRegId(context), sharedPreferencesHelper.getSessionId(context));
            Log.i("apiURL Url Are", apiURL);
            new CouponAsync().execute(apiURL);

        } else {
            String apiURL = apiImplementation.GenerateFullUrlforBenefitCupon(sharedPreferencesHelper.getSessionId(context)
                    , sharedPreferencesHelper.getRegId(context));
            Log.i("Url Are", apiURL);
            new CouponAsync().execute(apiURL);
        }


    }

    private class CouponAsync extends AsyncTask<String, Void, String> {
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
                if (mainJsonObject.getString("status").equalsIgnoreCase("true")) {
                    adapter = new CouponAdapter(getActivity());
                    coupon_gridview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context, "Server Error");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

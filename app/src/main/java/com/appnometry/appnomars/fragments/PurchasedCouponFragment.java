package com.appnometry.appnomars.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.activities.CouponDetailActivity;
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


/**
 * Created by Daniel on 09.11.2014.
 */
public class PurchasedCouponFragment extends Fragment {
    private ProgressDialog progressDialog;
    ApiImplementation apiImplementation = new ApiImplementation();
    private Context context;
    private String results;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private CouponAdapter adapter;
    Fragment fragment = null;

    /**
     * ***********************Declare View Component********************
     */
    private GridView purchasecoupon_gridview;

    /**
     * ***************Initiate Bottom Tab************************
     */
    private LinearLayout linear_benefitcoupon;
    private LinearLayout linear_purchasedcoupon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_purchasedcoupon, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);

        context = getActivity();
        initBottomTAb(rootView);
        initUI(rootView);

        return rootView;
    }

    public void initUI(View view) {
        purchasecoupon_gridview = (GridView) view.findViewById(R.id.purchasecoupon_gridview);
        purchasecoupon_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), CouponDetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        String apiURL = apiImplementation.GenerateFullUrlforPurchase(sharedPreferencesHelper.getSessionId(context)
                , sharedPreferencesHelper.getRegId(context));
        new CouponAsync().execute(apiURL);
       /* if (AppConstant.isSearched) {
            String apiURL = apiImplementation.GenarateFullURLforBenefitCuponSearch("Boom", sharedPreferencesHelper.getRegId(context), sharedPreferencesHelper.getSessionId(context));
            Log.i("apiURL Url Are", apiURL);
            new CouponAsync().execute(apiURL);

        } else {
            String apiURL = apiImplementation.GenerateFullUrlforBenefitCupon(sharedPreferencesHelper.getSessionId(context)
                    , sharedPreferencesHelper.getRegId(context));
            Log.i("Url Are", apiURL);
            new CouponAsync().execute(apiURL);
        }*/


    }
    /********************For Fragment Initialization************************/
    private void initiateFragmentView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
    }

    private void initBottomTAb(View view) {
        linear_benefitcoupon = (LinearLayout) view.findViewById(R.id.linear_benefitcoupon);
        linear_purchasedcoupon = (LinearLayout) view.findViewById(R.id.linear_purchasedcoupon);
        linear_purchasedcoupon.setBackgroundColor(getResources().getColor(R.color.bottom_select));
        linear_benefitcoupon.setOnClickListener(tabCoupononclick);
        linear_purchasedcoupon.setOnClickListener(tabCoupononclick);
    }
    public View.OnClickListener tabCoupononclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_benefitcoupon:
                     fragment = new CouponFragment();
                    initiateFragmentView();
                    break;
                case R.id.linear_purchasedcoupon:
//                    fragment = new MyCartFragment();

                    break;

            }
        }
    };
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
                   // coupon_gridview.setAdapter(adapter);
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

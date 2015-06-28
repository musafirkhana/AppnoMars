package com.appnometry.appnomars.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.ShopListAdapter;
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
 * Created by Ali
 */
public class ShopFragment extends Fragment {


    Fragment fragment = null;
    private ProgressDialog progressDialog;
    ApiImplementation apiImplementation = new ApiImplementation();
    private Context context;
    private String results;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();

    /**
     * ***************Initiate Bottom Tab************************
     */
    private LinearLayout linear_shop;
    private LinearLayout linear_mycart;
    private LinearLayout linear_history;

    /*******************Declare Shop UI***************************/
    private GridView shop_list;
    private ShopListAdapter shopListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);

        context = getActivity();

        initBottomTAb(rootView);
        initUI(rootView);
        return rootView;
    }
private void initUI(View view){
    shop_list=(GridView)view.findViewById(R.id.shop_list);
    String apiURL = apiImplementation.GenerateFullUrlforShopView(sharedPreferencesHelper.getSessionId(context)
            , sharedPreferencesHelper.getRegId(context));
    Log.i("Url Are", apiURL);
    new ShopAsync().execute(apiURL);
}
    private void initiateFragmentView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }
    }

    private void initBottomTAb(View view) {
        linear_shop = (LinearLayout) view.findViewById(R.id.linear_shop);
        linear_mycart = (LinearLayout) view.findViewById(R.id.linear_mycart);
        linear_history = (LinearLayout) view.findViewById(R.id.linear_history);
        linear_shop.setBackgroundColor(getResources().getColor(R.color.bottom_select));

        linear_shop.setOnClickListener(tabonclick);
        linear_mycart.setOnClickListener(tabonclick);
        linear_history.setOnClickListener(tabonclick);
    }

    public View.OnClickListener tabonclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_shop:
                    // fragment = new ShopFragment();
                    break;
                case R.id.linear_mycart:
                    fragment = new MyCartFragment();
                    initiateFragmentView();
                    break;
                case R.id.linear_history:
                    fragment = new HistoryFragment();
                    initiateFragmentView();
                    break;
            }
        }
    };
    private class ShopAsync extends AsyncTask<String, Void, String> {
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
                    shopListAdapter = new ShopListAdapter(getActivity());
                    shop_list.setAdapter(shopListAdapter);
                    shopListAdapter.notifyDataSetChanged();
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

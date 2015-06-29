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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.MyCartAdapter;
import com.appnometry.appnomars.database.AppnomarsDBHandler;
import com.appnometry.appnomars.database.KudoroBDModel;
import com.appnometry.appnomars.parser.ShoppingCartParser;
import com.appnometry.appnomars.util.AppConstant;

import org.json.JSONException;

import java.io.IOException;
import java.util.Vector;


/**
 * Created by Ali
 */
public class MyCartFragment extends Fragment {


    Fragment fragment = null;
    private AppnomarsDBHandler appnomarsDBHandler;
    private Vector<KudoroBDModel> kudoroBDModel;
    private MyCartAdapter myCartAdapter;
    private Context context;
    private ProgressDialog busyDialog;
    /**
     * ***************Initiate Bottom Tab************************
     */
    private LinearLayout linear_shop;
    private LinearLayout linear_mycart;
    private LinearLayout linear_history;

    private ListView mycart_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycart, container, false);
        context = getActivity();
        busyDialog=new ProgressDialog(context);
        initBottomTAb(rootView);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View view) {
        mycart_list = (ListView) view.findViewById(R.id.mycart_list);
        new getAllShoppingList().execute(AppConstant.elements.toString());

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
        linear_mycart.setBackgroundColor(getResources().getColor(R.color.bottom_select));
        linear_shop.setOnClickListener(tabonclick);
        linear_mycart.setOnClickListener(tabonclick);
        linear_history.setOnClickListener(tabonclick);
    }

    public View.OnClickListener tabonclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_shop:
                    fragment = new ShopFragment();
                    initiateFragmentView();
                    break;
                case R.id.linear_mycart:

                    break;
                case R.id.linear_history:
                    fragment = new HistoryFragment();
                    initiateFragmentView();
                    break;
            }
        }
    };

    private class getAllShoppingList extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            busyDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.i("results", "are" + params[0]);
                if (ShoppingCartParser.connect(context, params[0]))
                    ;
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
            myCartAdapter = new MyCartAdapter(getActivity());
            mycart_list.setAdapter(myCartAdapter);
            myCartAdapter.notifyDataSetChanged();

            busyDialog.dismiss();
        }

    }

}

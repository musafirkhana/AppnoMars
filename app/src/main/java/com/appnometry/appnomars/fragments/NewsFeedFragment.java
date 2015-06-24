package com.appnometry.appnomars.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.adapter.NewsFeedAdapter;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.parser.GlobalItemParser;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ali on 09.06.2015.
 */
public class NewsFeedFragment extends Fragment {

    ArrayList<String> dialogItems;

    private SwipeRefreshLayout swipeLayout;
    private ProgressDialog progressDialog;
    ApiImplementation apiImplementation = new ApiImplementation();
    private GridView mens_gridview;
    private Context context;
    private String results;
    private NewsFeedAdapter adapter;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);

        context = getActivity();
        initUI(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }


    public void initUI(View view) {
        mens_gridview = (GridView) view.findViewById(R.id.mens_gridview);
        mens_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "List Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });


       String apiURL = apiImplementation.GenerateFullUrlforStream(sharedPreferencesHelper.getSessionId(context)
                ,sharedPreferencesHelper.getRegId(context));
        Log.i("Url Are",apiURL);
        new NewsFeedAsync().execute(apiURL);

      /*  swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();

            }
        });*/

    }
/*
    public void refreshContent() {
        String apiURL = apiImplementation.MenListURL(20);
        new MenListAsync().execute(apiURL);
        new MenListAsync().execute(apiURL);
        if (swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(false);
        }
    }*/


    private class NewsFeedAsync extends AsyncTask<String, Void, String> {
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

                if (GlobalItemParser.connect(context, results));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                final JSONObject mainJsonObject = new JSONObject(results);
                if(mainJsonObject.getString("status").equalsIgnoreCase("true")){
                    adapter = new NewsFeedAdapter(getActivity());
                    mens_gridview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context,"Server Error");
                }

            } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

    }
    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStand:
                MainActivity.showMyDialog("Dialog", "Test message.", "cancel", "ok", new Standard_Dialog.MyDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogNegativeClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.buttonSingle:
                MainActivity.showMySingleDialog("Dialog", dialogItems, "cancel", "ok", new SingleDialog.MyDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogNegativeClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogItemClick(DialogFragment dialog, View view, int position) {

                    }
                });
                break;
            case R.id.buttonPersSingle:
                MainActivity.showMyRadioDialog("Dialog", dialogItems, "cancel", "ok");
                break;
            case R.id.buttonMulti:
                MainActivity.showMyMultiDialog("Dialog", dialogItems, "cancel", "ok");
                break;
        }*/
//    }


}

package com.appnometry.appnomars.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.activities.EventDetailActivity;
import com.appnometry.appnomars.adapter.EventsAdapter;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.parser.GlobalItemParser;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Daniel on 09.11.2014.
 */
public class EventsFragment extends Fragment {
    private ProgressDialog progressDialog;
    ApiImplementation apiImplementation = new ApiImplementation();
    private Context context;
    private String results;
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private EventsAdapter adapter;

    /**************************Declare View Component*********************/
    private GridView event_gridview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        progressDialog = new CustomProgressDialog(getActivity(), "Loading...", true);

        context = getActivity();
        initUI(rootView);
        return rootView;
    }
    public void initUI(View view) {
        event_gridview = (GridView) view.findViewById(R.id.event_gridview);
        event_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        String apiURL = apiImplementation.GenarateFullURLforEvent(sharedPreferencesHelper.getSessionId(context)
                , sharedPreferencesHelper.getRegId(context));
        Log.i("Url Are", apiURL);
        new EventsAsync().execute(apiURL);


    }
    private class EventsAsync extends AsyncTask<String, Void, String> {
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
                    adapter = new EventsAdapter(getActivity());
                    event_gridview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context, "Server Error");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

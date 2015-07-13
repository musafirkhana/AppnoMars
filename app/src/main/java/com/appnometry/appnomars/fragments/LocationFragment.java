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
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Ali.
 */
public class LocationFragment extends Fragment {

    private ProgressDialog progressDialog;
    private String results;
    private Context context;
    private ApiImplementation apiImplementation = new ApiImplementation();
    SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();

    //static final LatLng HAMBURG = new LatLng(53.558, 9.927);
   // static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;
    private double latiTude[]=new double[50];
    private double longiTude[]=new double[50];



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
            progressDialog.dismiss();
            try {
                final JSONObject mainJsonObject = new JSONObject(results);
                final JSONArray top_list = new JSONArray(mainJsonObject.getString("data"));
                Log.i("top_list",""+top_list);
                for (int i = 0; i < top_list.length(); i++) {
                    JSONObject top_list_jsonObject = top_list.getJSONObject(i);
                    if(top_list_jsonObject.getString("latitude").equalsIgnoreCase("NA")){

                    }else {
                        latiTude[i]=Double.parseDouble(top_list_jsonObject.getString("latitude"));
                        longiTude[i]=Double.parseDouble(top_list_jsonObject.getString("longitude"));
                        Log.i("Latitude",""+longiTude[i]);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            initMAP(latiTude,longiTude);
        }

    }

    private void initMAP(double []latitude,double [] longiTude){
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        for(int i=0;i<latitude.length;i++){
            Log.i("latiTude[i]",""+i+"Are "+latitude[i]);
            Marker hamburg = map.addMarker(new MarkerOptions().position(new LatLng(latitude[i], longiTude[i]))
                    .title("Appnometry"));

//            Marker kiel = map.addMarker(new MarkerOptions()
//                    .position(KIEL)
//                    .title("Kiel")
//                    .snippet("Kiel is cool")
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
            // Move the camera instantly to hamburg with a zoom of 15.
           // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latiTude[i], longiTude[i]), 15));

            // Zoom in, animating the camera.
           // map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }



    }

}

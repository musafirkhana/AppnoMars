package com.appnometry.appnomars.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.activities.VunueListActivity;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.AppJsonUtility;
import com.appnometry.appnomars.util.CommonUtils;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Ali on 06.11.2015.
 */
public class MyprofileFragment extends Fragment {

    private AppJsonUtility jsonUtility = new AppJsonUtility();
    private ProgressDialog progressDialog;
    public String results;
    private Context context;
    private ApiImplementation apiImplementation = new ApiImplementation();
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private AlertDialog.Builder builder;
    /**
     * Decleare View
     */
    private EditText mp_email;
    private EditText mp_password;
    private EditText mp_confirmpassword;
    private EditText mp_firstname;
    private EditText mp_lastname;
    private EditText mp_sex;
    private EditText mp_birthday;
    private EditText mp_address;
    private EditText mp_city;
    private EditText mp_postcode;
    private EditText mp_country;
    private EditText mp_courses;

    private Button mp_submit_btn;
    private boolean isVanue=false;
    private String vanumeName="";
    private String vanumeID="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        context = getActivity();
        progressDialog = new CustomProgressDialog(context, getResources().getString(R.string.loading_text), true);
        String url = apiImplementation.GenerateFullUrlforMyProfile(
                sharedPreferencesHelper.getRegId(context), sharedPreferencesHelper.getSessionId(context));
        new getProfileDataAsync().execute(url);
        initUI(rootView);
        return rootView;
    }


    /**
     * *********************************Initiate User Interfaces***************************************************
     */
    private void initUI(View view) {
        mp_email = (EditText) view.findViewById(R.id.mp_email);
        mp_password = (EditText) view.findViewById(R.id.mp_password);
        mp_confirmpassword = (EditText) view.findViewById(R.id.mp_confirmpassword);
        mp_firstname = (EditText) view.findViewById(R.id.mp_firstname);
        mp_lastname = (EditText) view.findViewById(R.id.mp_lastname);
        mp_sex = (EditText) view.findViewById(R.id.mp_sex);
        mp_birthday = (EditText) view.findViewById(R.id.mp_birthday);
        mp_address = (EditText) view.findViewById(R.id.mp_address);
        mp_city = (EditText) view.findViewById(R.id.mp_city);
        mp_postcode = (EditText) view.findViewById(R.id.mp_postcode);
        mp_country = (EditText) view.findViewById(R.id.mp_country);
        mp_courses = (EditText) view.findViewById(R.id.mp_courses);
        mp_submit_btn = (Button) view.findViewById(R.id.mp_submit_btn);

        mp_submit_btn.setOnClickListener(clickListener);
        mp_sex.setOnClickListener(clickListener);
        mp_birthday.setOnClickListener(clickListener);
        mp_courses.setOnClickListener(clickListener);
        mp_submit_btn.setOnClickListener(clickListener);
        mp_courses.setKeyListener(null);
        mp_birthday.setKeyListener(null);
        mp_sex.setKeyListener(null);


    }

    /**
     * *********************************Set Data From Api***************************************************
     */
    private void setData() {
        mp_email.setText(AppConstant.myprofileErray[0]);
        //mp_password.setText(AppConstant.myprofileErray[1]);
        //mp_confirmpassword.setText(AppConstant.myprofileErray[1]);
        mp_firstname.setText(AppConstant.myprofileErray[2]);
        mp_lastname.setText(AppConstant.myprofileErray[3]);
        mp_sex.setText(AppConstant.myprofileErray[4]);
        mp_birthday.setText(AppConstant.myprofileErray[5]);
        mp_address.setText(AppConstant.myprofileErray[6]);
        mp_city.setText(AppConstant.myprofileErray[7]);
        mp_postcode.setText(AppConstant.myprofileErray[8]);
        mp_country.setText(AppConstant.myprofileErray[9]);
        //mp_courses.setText(AppConstant.myprofileErray[10]);
        getVanume();


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.mp_submit_btn:
                    if (isRequired()) {
                        String url = apiImplementation.GenerateFullUrlforUpdate(
                                sharedPreferencesHelper.getRegId(context),
                                mp_email.getText().toString().trim(),
                                mp_password.getText().toString().trim(),
                                mp_confirmpassword.getText().toString().trim(),
                                "Phone",
                                mp_firstname.getText().toString().trim(),
                                mp_lastname.getText().toString().trim(),
                                mp_postcode.getText().toString().trim(),
                                mp_address.getText().toString().trim(),
                                mp_city.getText().toString().trim(),
                                mp_country.getText().toString().trim(),
                                "Android",
                                mp_sex.getText().toString().trim(),
                                mp_birthday.getText().toString().trim(),
                                AppConstant.myprofileErray[10].toString().trim(),
                                "",
                                "");
                        Log.i("URL Are ",url);
                        new RegistrationAsync().execute(url);
                    } else {


                    }
                    break;
                case R.id.mp_sex:
                    showSimpleListDialog(v);
                    break;
                case R.id.mp_birthday:
                    showDatePicker();
                    break;
                case R.id.mp_courses:
                    isVanue=true;
                    Intent intent=new Intent(getActivity(),VunueListActivity.class);
                    startActivity(intent);

                    break;
            }
        }
    };

    private String getVanume(){
        if(isVanue){
            vanumeName=AppConstant.vanueName;
            vanumeID=AppConstant.vanueID;
            if(getNumberOfVanue(vanumeName).equalsIgnoreCase("0")){
                mp_courses.setText("Select Courses");
            }else {
                mp_courses.setText(getNumberOfVanue(vanumeName)+" Places Selected");
            }

        }else {
            mp_courses.setText(getNumberOfVanue(AppConstant.myprofileErray[10])+" Places Selected");
        }

        return "";
    }

    public boolean isRequired() {

        if (mp_email.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_email));
            mp_email.setError(getResources().getString(R.string.req_email));
            return false;
        }
        mp_email.setError(null);
        if (mp_password.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_password));
            mp_password.setError(getResources().getString(R.string.req_password));
            return false;
        }
        mp_password.setError(null);
        if (mp_confirmpassword.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_confirm_password));
            mp_confirmpassword.setError(getResources().getString(R.string.req_confirm_password));
            return false;

        }
        mp_confirmpassword.setError(null);
        if (mp_firstname.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_firstname).toString());
            mp_firstname.setError(getResources().getString(R.string.req_firstname).toString());
            return false;
        }
        mp_firstname.setError(null);
        if (mp_lastname.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_lastname));
            mp_lastname.setError(getResources().getString(R.string.req_lastname));
            return false;
        }
        mp_lastname.setError(null);
        if (mp_courses.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_course));
            mp_courses.setError(getResources().getString(R.string.req_course));
            return false;
        }
        mp_courses.setError(null);

        if (mp_sex.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Mention Sex");
            mp_sex.setError("Mention Sex");
            return false;
        }
        mp_sex.setError(null);

        if (mp_birthday.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Date of birth Required");
            mp_birthday.setError("Date of birth Required");
            return false;
        }
        mp_birthday.setError(null);

        if (mp_city.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "City Required");
            mp_city.setError("City Required");
            return false;
        }
        mp_city.setError(null);
        if (mp_postcode.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Post Code Required");
            mp_postcode.setError("Post Code Required");
            return false;
        }
        mp_postcode.setError(null);
        if (mp_country.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Country Required");
            mp_country.setError("Country Required");
            return false;
        }
        mp_country.setError(null);
        if (mp_address.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Address required");
            mp_address.setError("Address required");
            return false;
        }
        mp_address.setError(null);

        return true;


    }
    private boolean isPasswodOK() {
        if (mp_password.getText().toString().trim().equalsIgnoreCase(mp_confirmpassword.getText().toString().trim())) {

            return true;
        } else {
            AlertDialogHelper.showAlert(context, "Password Not Match");
            return false;
        }

    }
    private void showSimpleListDialog(View view) {
        builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Title");

        final String[] Items={"Male","Female","Other"};
        builder.setItems(Items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mp_sex.setText(Items[i]);
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    /**
     * Builds a custom dialog based on the defined layout
     * 'res/layout/datepicker_layout.xml' and shows it
     */
    public void showDatePicker() {
        // Initializiation
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final AlertDialog.Builder dialogBuilder =
                new AlertDialog.Builder(getActivity());
        View customView = inflater.inflate(R.layout.datepicker_layout, null);
        dialogBuilder.setView(customView);
        final Calendar now = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker) customView.findViewById(R.id.dialog_datepicker);
        final TextView dateTextView = (TextView) customView.findViewById(R.id.dialog_dateview);
        final SimpleDateFormat dateViewFormatter =  new SimpleDateFormat("EEEE,dd.MM.yyyy", Locale.ENGLISH);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        // Minimum date
        Calendar minDate = Calendar.getInstance();
        try {
            minDate.setTime(formatter.parse("12.12.1900"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePicker.setMinDate(minDate.getTimeInMillis());
        // View settings
        dialogBuilder.setTitle("Choose a date");
        Calendar choosenDate = Calendar.getInstance();
        int year = choosenDate.get(Calendar.YEAR);
        int month = choosenDate.get(Calendar.MONTH);
        int day = choosenDate.get(Calendar.DAY_OF_MONTH);
        try {
            Date choosenDateFromUI = formatter.parse( mp_birthday.getText().toString()
            );
            choosenDate.setTime(choosenDateFromUI);
            year = choosenDate.get(Calendar.YEAR);
            month = choosenDate.get(Calendar.MONTH);
            day = choosenDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar dateToDisplay = Calendar.getInstance();
        dateToDisplay.set(year, month, day);
        dateTextView.setText( dateViewFormatter.format(dateToDisplay.getTime())
        );
        // Buttons
        dialogBuilder.setNegativeButton("Go to today",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mp_birthday.setText(formatter.format(now.getTime()));
                        dialog.dismiss();
                    }
                }
        );
        dialogBuilder.setPositiveButton("Choose",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar choosen = Calendar.getInstance();
                        choosen.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth()
                        );
                        mp_birthday.setText( dateViewFormatter.format(choosen.getTime())
                        );
                        dialog.dismiss();
                    }
                }
        );
        final AlertDialog dialog = dialogBuilder.create();
        // Initialize datepicker in dialog atepicker
        datePicker.init( year, month, day, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        Calendar choosenDate = Calendar.getInstance();
                        choosenDate.set(year, monthOfYear, dayOfMonth);
                        dateTextView.setText( dateViewFormatter.format(choosenDate.getTime())
                        );
                        /*if (choosenDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || now.compareTo(choosenDate) < 0) {
                            dateTextView.setTextColor( Color.parseColor("#023B4C")
                            );
                            ((Button) dialog.getButton( AlertDialog.BUTTON_POSITIVE)).setEnabled(false);
                        } else {*/
                        dateTextView.setTextColor( Color.parseColor("#ffffff"));
                        ((Button) dialog.getButton(  AlertDialog.BUTTON_POSITIVE)).setEnabled(true);
//                        }
                    }
                }
        );
        // Finish
        dialog.show();
    }




    /**
     * *****************************Get Data From Profile Api *********************************************************
     */
    private class getProfileDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                results = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(params[0]));


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
            Log.i("result", results);
            if (jsonUtility.myProfileParser(results).equalsIgnoreCase("Success")) {

                progressDialog.dismiss();
                Log.i("Appconstant", "" + AppConstant.myprofileErray);

                setData();
            } else {
                progressDialog.dismiss();
                AlertDialogHelper.showAlert(context, jsonUtility.loginParser(results, context));
            }


        }

    }
    private class RegistrationAsync extends AsyncTask<String, Void, String> {
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
            try {
                JSONObject jsonObject = new JSONObject(results);
                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                    progressDialog.dismiss();
                  
                    Toast.makeText(context,"Saved",Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context, jsonObject.getString("message"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }



        }

    }
    private String getNumberOfVanue(String places){
        int numberOfPlaces = CommonUtils.getNumberOfCoupon(places);
        return ""+(numberOfPlaces-1);
    }
}

package com.appnometry.appnomars.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appnometry.appnomars.R;
import com.appnometry.appnomars.dialog.AlertDialogHelper;
import com.appnometry.appnomars.ui.CustomProgressDialog;
import com.appnometry.appnomars.util.ApiImplementation;
import com.appnometry.appnomars.util.AppConstant;
import com.appnometry.appnomars.util.HttpRequest;
import com.appnometry.appnomars.util.SharedPreferencesHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistgrationActivity extends Activity implements View.OnClickListener {
    private Context context;
    String[] REGIONS = {"Dhaka", "Barisal", "Chittagong", "Khulna", "Rajshahi", "Sylhet"};
    int spinnerPosition = 0;
    public String results, region;
    private AlertDialog.Builder builder;
    private ApiImplementation apiImplementation = new ApiImplementation();
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private ProgressDialog progressDialog;
    private String vanumeName="";
    private String vanumeID="";


    private EditText su_firstname;
    private EditText su_lastname;
    private EditText su_email;
    private EditText su_password;
    private EditText su_confirmpassword;
    private EditText su_courses;
    private EditText su_sex;
    private EditText su_birthday;
    private EditText su_city;
    private EditText su_postcode;
    private EditText su_country;
    private EditText su_address;
    private EditText su_phone;
    private Button login_btn;
    private CheckBox su_terms_checkbox;
    private TextView termsCondition,su_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registgration);
        context = this;
        progressDialog = new CustomProgressDialog(this, "Loading...", true);
        vanumeName=AppConstant.vanueName;
        initUI();
    }

    public void initUI() {
        su_firstname = (EditText) findViewById(R.id.su_firstname);
        su_lastname = (EditText) findViewById(R.id.su_lastname);
        su_email = (EditText) findViewById(R.id.su_email);
        su_password = (EditText) findViewById(R.id.su_password);
        su_confirmpassword = (EditText) findViewById(R.id.su_confirmpassword);
        su_courses = (EditText) findViewById(R.id.su_courses);
        su_sex = (EditText) findViewById(R.id.su_sex);
        su_birthday = (EditText) findViewById(R.id.su_birthday);
        su_address = (EditText) findViewById(R.id.su_address);
        su_postcode = (EditText) findViewById(R.id.su_postcode);
        su_country = (EditText) findViewById(R.id.su_country);
        su_city = (EditText) findViewById(R.id.su_city);
        su_login=(TextView)findViewById(R.id.su_login);
        su_phone=(EditText)findViewById(R.id.su_phone);


        termsCondition = (TextView) findViewById(R.id.terms_condition);
        termsCondition.setClickable(true);
        termsCondition.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.google.com'> I have read and agree to the Privacy Policy </a>";
        termsCondition.setText(Html.fromHtml(text));
        su_terms_checkbox = (CheckBox) findViewById(R.id.su_terms_checkbox);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, REGIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        su_sex.setOnClickListener(this);
        su_birthday.setOnClickListener(this);
        su_courses.setOnClickListener(this);
        su_courses.setKeyListener(null);
        su_birthday.setKeyListener(null);
        su_sex.setKeyListener(null);
        su_login.setOnClickListener(this);



    }


    private boolean isPasswodOK() {
        if (su_password.getText().toString().trim().equalsIgnoreCase(su_confirmpassword.getText().toString().trim())) {

            return true;
        } else {
            AlertDialogHelper.showAlert(context, "Password Not Match");
            return false;
        }

    }

    private boolean isTermsCheck() {
        if (su_terms_checkbox.isChecked()) {
            return true;
        } else {
            AlertDialogHelper.showAlert(context, "PLease Check Terms And Condition");
            return false;

        }

    }

    public boolean isRequired() {

        if (su_email.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_email));
            su_email.setError(getResources().getString(R.string.req_email));
            return false;
        }
        su_email.setError(null);
        if (su_password.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_password));
            su_password.setError(getResources().getString(R.string.req_password));
            return false;
        }
        su_password.setError(null);
        if (su_confirmpassword.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_confirm_password));
            su_confirmpassword.setError(getResources().getString(R.string.req_confirm_password));
            return false;

        }
        su_confirmpassword.setError(null);
        if (su_firstname.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_firstname).toString());
            su_firstname.setError(getResources().getString(R.string.req_firstname).toString());
            return false;
        }
        su_firstname.setError(null);
        if (su_lastname.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_lastname));
            su_lastname.setError(getResources().getString(R.string.req_lastname));
            return false;
        }
        su_lastname.setError(null);
        if (su_courses.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, getResources().getString(R.string.req_course));
            su_courses.setError(getResources().getString(R.string.req_course));
            return false;
        }
        su_courses.setError(null);

        if (su_sex.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Mention Sex");
            su_sex.setError("Mention Sex");
            return false;
        }
        su_sex.setError(null);

        if (su_birthday.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Date of birth Required");
            su_birthday.setError("Date of birth Required");
            return false;
        }
        su_birthday.setError(null);

        if (su_city.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "City Required");
            su_city.setError("City Required");
            return false;
        }
        su_city.setError(null);
        if (su_postcode.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Post Code Required");
            su_postcode.setError("Post Code Required");
            return false;
        }
        su_postcode.setError(null);
        if (su_country.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Country Required");
            su_country.setError("Country Required");
            return false;
        }
        su_country.setError(null);
        if (su_address.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Address required");
            su_address.setError("Address required");
            return false;
        }
        su_address.setError(null);

        if (su_phone.getText().toString().trim().length() == 0) {
            AlertDialogHelper.showAlert(context, "Phone required");
            su_phone.setError("Phone required");
            return false;
        }
        su_phone.setError(null);




        return true;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_btn:


               if (isRequired() && isPasswodOK() && isTermsCheck()) {
                   String url = apiImplementation.GenetareFullURLforRegistration(
                           su_firstname.getText().toString().trim(),
                           su_lastname.getText().toString().trim(),
                           su_email.getText().toString().trim(),
                           su_password.getText().toString().trim(),
                           su_phone.getText().toString().trim(),
                           su_postcode.getText().toString().trim(),
                           su_address.getText().toString().trim(),
                           su_city.getText().toString().trim(),
                           su_country.getText().toString().trim(), "Android",
                           su_sex.getText().toString().trim(),
                           su_birthday.getText().toString().trim(),
                           vanumeID.replaceFirst(",", "")  );
                   Log.i("URL Are ",url);
                   new RegistrationAsync().execute(url);
                } else {


                }

                break;

            case R.id.su_sex:
                showSimpleListDialog(v);
                break;
            case R.id.su_birthday:
                showDatePicker();
                break;
            case R.id.su_courses:
                Intent intent=new Intent(RegistgrationActivity.this,VunueListActivity.class);
                startActivity(intent);

                break;

            case R.id.su_login:
                Intent intent1=new Intent(RegistgrationActivity.this,LoginActivity.class);
                startActivity(intent1);
                break;
        }


    }


    private void showSimpleListDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Title");

        final String[] Items={"Male","Female","Other"};
        builder.setItems(Items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                su_sex.setText(Items[i]);
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
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
            Date choosenDateFromUI = formatter.parse( su_birthday.getText().toString()
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
                        su_birthday.setText(formatter.format(now.getTime()));
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
                        su_birthday.setText( dateViewFormatter.format(choosen.getTime())
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
                    finish();
                    Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistgrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
                    AlertDialogHelper.showAlert(context, jsonObject.getString("message"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }



        }

    }

    @Override
    protected void onResume() {
        vanumeName=AppConstant.vanueName;
        vanumeID=AppConstant.vanueID;
        su_courses.setText(vanumeName);
        super.onResume();
    }
}



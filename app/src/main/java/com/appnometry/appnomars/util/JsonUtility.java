package com.appnometry.appnomars.util;

import android.content.Context;

import org.json.JSONObject;


public class JsonUtility {

    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private PersistUser persistUser = new PersistUser();

    public String loginParser(String response, Context context) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toString().equalsIgnoreCase("true")) {
                org.json.JSONObject jsonObject1 = new org.json.JSONObject(jsonObject.getString("users"));
                sharedPreferencesHelper.setRegId(context, jsonObject1.getString("userId"));
                sharedPreferencesHelper.setSessionId(context, jsonObject1.getString("session"));

                String[] existErray = {jsonObject1.getString("userId"),
                        jsonObject1.getString("session"),
                        (jsonObject1.getString("lastname") + jsonObject1.getString("lastname")),
                        jsonObject1.getString("expireDate")};
                persistUser.saveArray(existErray, "USEREXIST", context);


                return "Success";
            } else {
                return jsonObject.getString("message").toString();
            }
        } catch (Exception f) {
            return f.getMessage();
        }


    }

    public String myProfileParser(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").toString().equalsIgnoreCase("true")) {
                org.json.JSONObject jsonObject1 = new org.json.JSONObject(jsonObject.getString("data"));
                AppConstant.myprofileErray[0] = jsonObject1.getString("email");
                AppConstant.myprofileErray[1] = jsonObject1.getString("password");
                AppConstant.myprofileErray[2] = jsonObject1.getString("firstname");
                AppConstant.myprofileErray[3] = jsonObject1.getString("lastname");
                AppConstant.myprofileErray[4] = jsonObject1.getString("sex");
                AppConstant.myprofileErray[5] = jsonObject1.getString("birthDate");
                AppConstant.myprofileErray[6] = jsonObject1.getString("usecompanyaddress");
                AppConstant.myprofileErray[7] = jsonObject1.getString("town");
                AppConstant.myprofileErray[8] = jsonObject1.getString("postcode");
                AppConstant.myprofileErray[9] = jsonObject1.getString("country");
                AppConstant.myprofileErray[10] = jsonObject1.getString("user_venues");
                return "Success";
            } else {
                return jsonObject.getString("message").toString();
            }
        } catch (Exception f) {
            return f.getMessage();
        }


    }

}

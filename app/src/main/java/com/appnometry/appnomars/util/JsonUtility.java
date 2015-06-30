package com.appnometry.appnomars.util;

import android.content.Context;
import android.util.Log;

import com.appnometry.appnomars.parser.ShoppingCartParser;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;

public class JsonUtility {

    public void createJsonFile(JSONObject jsonObject,Context context) {
        String string = "" + jsonObject;
        System.out.println(string);
        AppConstant.elements.add(string);
        try {
            ShoppingCartParser.connect(context, string);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Writing JSON object to file    " + jsonObject);


    }

    public void removeAddtocartList(int position) {
        AppConstant.elements.remove(position);
    }


    public static String UpdateJson(String valueName, String value) {
        String resuLT = "";
        JSONObject jsonObj = AppConstant.ADDTOCART;
        Log.w("jsonObj", "" + jsonObj);
        jsonObj.put(valueName, value);
        resuLT = "" + jsonObj;
        Log.i("return Json", resuLT);
        return "" + jsonObj;
    }

}

package com.appnometry.appnomars.parser;

import android.content.Context;
import android.util.Log;

import com.appnometry.appnomars.holder.AllVanueList;
import com.appnometry.appnomars.model.VanueListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class VanueListParser {

    public static boolean connect(Context con, String result)
            throws JSONException, IOException {

        AllVanueList.removeVanueList();
        if (result.length() < 1) {
            return false;

        }

        final JSONObject mainJsonObject = new JSONObject(result);
        final JSONArray top_list = new JSONArray(mainJsonObject.getString("data"));
        VanueListModel vanueListModel;
        for (int i = 0; i < top_list.length(); i++) {
            JSONObject top_list_jsonObject = top_list.getJSONObject(i);
            vanueListModel = new VanueListModel();
            AllVanueList allVanueList = new AllVanueList();

            vanueListModel.setId(top_list_jsonObject.getString("id"));
            vanueListModel.setVenue_name(top_list_jsonObject.getString("venue_name"));
            vanueListModel.setCity_name(top_list_jsonObject.getString("city_name"));

            Log.i("city_name", top_list_jsonObject.getString("city_name"));

            allVanueList.setVanueList(vanueListModel);
            vanueListModel = null;
        }

        return true;
    }
}

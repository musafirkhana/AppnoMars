package com.appnometry.appnomars.parser;

import android.content.Context;
import android.util.Log;

import com.appnometry.appnomars.holder.AllGlobalItemList;
import com.appnometry.appnomars.model.GlobalItemListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class GlobalItemParser {

    public static boolean connect(Context con, String result)
            throws JSONException, IOException {

        AllGlobalItemList.removeNewsFeedList();
        if (result.length() < 1) {
            return false;

        }

        final JSONObject mainJsonObject = new JSONObject(result);
        final JSONArray top_list = new JSONArray(mainJsonObject.getString("data"));
        GlobalItemListModel globalItemListModel;
        for (int i = 0; i < top_list.length(); i++) {
            JSONObject top_list_jsonObject = top_list.getJSONObject(i);
            globalItemListModel = new GlobalItemListModel();
            AllGlobalItemList allGlobalItemList = new AllGlobalItemList();

            globalItemListModel.setTitle(top_list_jsonObject.getString("title"));
            globalItemListModel.setDescription(top_list_jsonObject.getString("description"));
            globalItemListModel.setImage(top_list_jsonObject.getString("image"));
            globalItemListModel.setId(top_list_jsonObject.getString("id"));
            globalItemListModel.setValid_to(top_list_jsonObject.getString("valid_to"));
            globalItemListModel.setValid_from(top_list_jsonObject.getString("valid_from"));
            globalItemListModel.setItem_type(top_list_jsonObject.getString("item_type"));
            globalItemListModel.setItem_category(top_list_jsonObject.getString("item_category"));
            globalItemListModel.setVenue_name(top_list_jsonObject.getString("venue_name"));
            globalItemListModel.setCity_name(top_list_jsonObject.getString("city_name"));
            globalItemListModel.setItem_venues(top_list_jsonObject.getString("item_venues"));
            globalItemListModel.setLatitude(top_list_jsonObject.getString("latitude"));
            globalItemListModel.setLongitude(top_list_jsonObject.getString("longitude"));
            globalItemListModel.setItem_price(top_list_jsonObject.getString("item_price"));
            globalItemListModel.setItem_deal_price(top_list_jsonObject.getString("item_deal_price"));
            globalItemListModel.setTickets(top_list_jsonObject.getString("tickets"));
            Log.i("tickets",top_list_jsonObject.getString("tickets"));

            allGlobalItemList.setNewsFeedList(globalItemListModel);
            globalItemListModel = null;
        }

        return true;
    }
}

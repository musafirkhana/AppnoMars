package com.appnometry.appnomars.parser;

import android.content.Context;

import com.appnometry.appnomars.holder.AllShoppingCartList;
import com.appnometry.appnomars.model.ShoppingCartListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ShoppingCartParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		AllShoppingCartList.removeShoppingList();
		if (result.length() < 1) {
			return false;

		}

		// final JSONObject mainJsonObject = new JSONObject(result);
		final JSONArray top_list = new JSONArray(result);
		ShoppingCartListModel shoppingCartListModel;
		for (int i = 0; i < top_list.length(); i++) {
			JSONObject top_list_jsonObject = top_list.getJSONObject(i);
            shoppingCartListModel = new ShoppingCartListModel();
            AllShoppingCartList allShoppingCartList = new AllShoppingCartList();

            shoppingCartListModel.setItemID(top_list_jsonObject.getString("ID"));
            shoppingCartListModel.setItemName(top_list_jsonObject.getString("TITLE"));
            shoppingCartListModel.setItemPrice(top_list_jsonObject.getString("PRICE"));
            shoppingCartListModel.setItemQuantity(top_list_jsonObject.getString("QUANTITY"));
            shoppingCartListModel.setItemDetail(top_list_jsonObject.getString("DETAIL"));
			shoppingCartListModel.setItemUnitPrice(top_list_jsonObject.getString("UNIT_PRICE"));


            allShoppingCartList.setShoppingList(shoppingCartListModel);
            shoppingCartListModel = null;
		}

		return true;
	}
}

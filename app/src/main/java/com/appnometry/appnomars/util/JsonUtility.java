package com.appnometry.appnomars.util;

import org.json.simple.JSONObject;

public class JsonUtility {

	public void createJsonFile(JSONObject jsonObject) {
		String string = "" + jsonObject;
		System.out.println(string);
        AppConstant.elements.add(string);
		System.out.println("Writing JSON object to file    " + jsonObject);


	}
    public void removeAddtocartList(int position){
        AppConstant.elements.remove(position);
    }



	/*public static String UpdateJson(String valueName, String value) {
		String resuLT="";
		JSONObject jsonObj = AppConstant.ADDTOCART;
		Log.w("jsonObj", "" + jsonObj);
		jsonObj.put(valueName, value);
		resuLT=""+jsonObj;
	saveJsontoSD(AppConstantFragment.jobNumber + ".txt", resuLT, AppConstantFragment.jobNumber);
		return "" + jsonObj;
	}*/

}

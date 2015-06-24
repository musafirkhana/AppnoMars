package com.appnometry.appnomars.util;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Ali PC on 5/17/2015.
 */
public class AppConstant extends Application {
    public static String vanueName = "";
    public static String vanueID = "";
    public static ArrayList<String> elements = new ArrayList<>();
    public static String[] myprofileErray = new String[100];

   /********************Google Push Noticifation Constant******************************************/
    public static final String GOOGLE_PROJ_ID = "563517251658";
    public static final String MSG_KEY = "price";

    /*********************Search Functionality Constants****************************************/
    public static boolean isSearched=false;


}

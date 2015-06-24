package com.appnometry.appnomars.util;

import android.util.Log;

/**
 * Created by Ali PC on 6/18/2015.
 */
public class CommonUtils {


    public static int getNumberOfCoupon(String string) {

        int specials = string.split(",", -1).length - 1;
        Log.i("specials", "" + specials);
        return specials + 1;
    }
}

package com.appnometry.appnomars.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ali PC on 6/11/2015.
 */
public class PersistUser {

    public static final String GCM_REG_ID = "regID";
    private static final String GCM = "GCM";

    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("persitUser", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("persitUser", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }


    /************************
     * Setting And Getting Gcm Push ID Onto SharedPreference
     ***********************/

    public static void setPushId(final Context ctx, final String user) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistUser.GCM, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PersistUser.GCM_REG_ID, user);
        editor.commit();
    }

    public static String getPushId(final Context ctx) {
        return ctx.getSharedPreferences( PersistUser.GCM, Context.MODE_PRIVATE)
                .getString(PersistUser.GCM_REG_ID, "");
    }


}

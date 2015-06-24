/*
 * Copyright (C) 2010 Mathieu Favez - http://mfavez.com
 *
 *
 * This file is part of FeedGoal.
 * 
 * FeedGoal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeedGoal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FeedGoal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.appnometry.appnomars.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Provides helpful getter/setter methods to wrap shared preferences
 * (application prefs + user prefs + manifest properties).
 * 
 * @author Mathieu Favez Created 15/04/2010
 */

public final class SharedPreferencesHelper {

	private static final String LOG_TAG = "SharedPreferencesHelper";

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	// Menu Groups Id

	private static final String FIRSTTime = "FIRSTTime";
	private static final String WHATS_NEW = "whatsNew";

	private static final String PHOTO = "photo";
	private static final String SHAKE = "shake";

	private static final String LOGGED = "logged";
	private static final String SPONSOR = "Sponsor";
	 private static final String APPLICATION_PUSHID = "pushid";
	private static final String PushItem = "Item";
	// App Preferences
	private static final String PREFS_FILE_NAME = "AppPreferences";
	private static final String REG_ID = "reg-id";
	private static final String SESSION = "session";





	public static void setPushId(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.APPLICATION_PUSHID, user);
		editor.commit();
	}

	public static String getPushId(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.APPLICATION_PUSHID, "");
	}
	public static void setRegId(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.REG_ID, user);
		editor.commit();
	}

	public static String getRegId(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.REG_ID, "");
	}
	public static void setSessionId(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.SESSION, user);
		editor.commit();
	}

	public static String getSessionId(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.SESSION, "");
	}





	public static void setPushItem(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.PushItem, user);
		editor.commit();
	}

	public static String getPushITem(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.PushItem, "1");
	}



	// end waseeTM

	// Getters for Application configuration attributes and preferences defined
	// in Android Manifest

	public static boolean getFirstTime(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(FIRSTTime, false);
	}

	public static boolean getWhatsNew(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(WHATS_NEW, false);
	}

	/*
	 * is photo enabled
	 */

	public static boolean isPhotoEnabled(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(PHOTO, true);
	}

	/*
	 * is photo enabled
	 */

	public static boolean isLoggedIn(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(LOGGED, false);
	}

	public static void setLogin(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(LOGGED, flag);
		editor.commit();
	}

	/*
	 * is shake enabled
	 */
	public static boolean isShakeEnabled(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SHAKE, true);
	}

	public static void setPhotoEnable(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(PHOTO, flag);
		editor.commit();
	}

	public static void setShakeEnable(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SHAKE, flag);
		editor.commit();
	}

	public static void setFirstTime(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(FIRSTTime, flag);
		editor.commit();
	}

	public static void setWhatsNew(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(WHATS_NEW, flag);
		editor.commit();
	}

	public static String getSponsor(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SPONSOR, "");
	}

	/*
	 * Langauge
	 */

	/*
	 * Langauge
	 */
	public static void setSponsor(final Context ctx, final String data) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SPONSOR, data);
		editor.commit();
	}

	public static CharSequence getVersionName(final Context ctx) {
		CharSequence version_name = "";
		try {
			final PackageInfo packageInfo = ctx.getPackageManager()
					.getPackageInfo(ctx.getPackageName(), 0);
			version_name = packageInfo.versionName;
		} catch (final NameNotFoundException nnfe) {
			Log.e(LOG_TAG, "", nnfe);
		}
		return version_name;
	}

	public static int getVersionCode(final Context ctx) {
		int version_code = 1;
		try {
			final PackageInfo packageInfo = ctx.getPackageManager()
					.getPackageInfo(ctx.getPackageName(), 0);
			version_code = packageInfo.versionCode;
		} catch (final NameNotFoundException nnfe) {
			Log.e(LOG_TAG, "", nnfe);
		}
		return version_code;
	}

	public static boolean isOnline(final Context ctx) {

		try {
			final ConnectivityManager cm = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				return ni.isConnectedOrConnecting();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	public static void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final int appVersion = getVersionCode(context);
		// Log.i(TAG, "Saving regId on app version " + appVersion);
		final Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there
	 * is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	@SuppressLint("NewApi")
	public static String getRegistrationId(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.w("GCM", "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		final int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		final int currentVersion = getVersionCode(context);
		if (registeredVersion != currentVersion) {
			Log.w("GCM", "App version changed.");
			return "";
		}
		return registrationId;
	}

}

package com.gteam.auctionsystem.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefManager {

	private Context context;
	private SharedPreferences prefs;

	public PrefManager(Context context) {
		this.context = context;
	}

	public void writeIntoPreferences(String[] names, String[] values) {
		Editor editor = getSharedPreferences().edit();
		for (int i = 0; i < names.length; i++) {
			editor.putString(names[i], values[i]);
		}
		editor.commit();
	}

	public String readPreference(String name, String defaultValue) {
		return getSharedPreferences().getString(name, defaultValue);
	}

	public void clearPreference(String[] names) {
		Editor editor = getSharedPreferences().edit();
		for (int i = 0; i < names.length; i++) {
			editor.putString(names[i], "");
		}
		editor.commit();
	}

	public void clearAll() {
		Editor editor = getSharedPreferences().edit();
		editor.clear();
		editor.commit();
	}

	private SharedPreferences getSharedPreferences() {
		if (prefs == null) {
			prefs = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
		}
		return prefs;
	}
}

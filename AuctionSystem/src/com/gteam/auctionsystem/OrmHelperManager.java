package com.gteam.auctionsystem;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class OrmHelperManager extends OpenHelperManager {

	public static <T extends OrmLiteSqliteOpenHelper> T getHelper(final String activityName, final Context context, final Class<T> openHelperClass) {
		Log.d(activityName, String.format("[orm open] %s", activityName));
		return OpenHelperManager.getHelper(context, openHelperClass);
	}

	public static void releaseHelper(final String activityName) {
		Log.d(activityName, String.format("[orm close] %s", activityName));
		OpenHelperManager.releaseHelper();
	}
}

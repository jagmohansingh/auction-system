package com.gteam.auctionsystem;

import com.gteam.auctionsystem.db.DatabaseHelper;

public class OrmActivity extends BaseActivity {

	private DatabaseHelper databaseHelper = null;

	public DatabaseHelper getDBHelper() {
		if (databaseHelper == null) {
			databaseHelper = OrmHelperManager.getHelper(TAG, this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OrmHelperManager.releaseHelper(TAG);
			databaseHelper = null;
		}
	}
}
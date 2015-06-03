package com.gteam.auctionsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;
import com.gteam.auctionsystem.util.PrefManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class BaseActivity extends ActionBarActivity {

	protected static final String TAG = BaseActivity.class.getSimpleName();
	protected ProgressDialog mProgressDialog;
	protected PrefManager prefManager;
	protected Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefManager = new PrefManager(this);
	}

	// use with caution
	protected void setupToolbar() {
		toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
	}

	protected User getUser() {
		User user = new User();
		PrefManager prefManager = new PrefManager(this);
		user.setId(Long.valueOf(prefManager.readPreference(Constants.PREF_LOGGED_USER_ID, "-1")));
		return user;
	}

	protected void logout() {
		prefManager.clearAll();
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(Constants.EXTRA_NO_SPLASH, true);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected void showProgressDialog(String title, String message) {
		try {
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			mProgressDialog = ProgressDialog.show(this, title, message);
		}
		catch (Exception ex) {
			Log.e(TAG, "## --> " + ex);
		}
	}

	protected void hideProgressDialog() {
		try {
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			mProgressDialog = null;
		}
		catch (Exception ex) {
			Log.e(TAG, "## --> " + ex);
		}
	}

	protected DisplayImageOptions getDisplayOptions() {
		return new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.no_photo).showImageOnFail(R.drawable.no_photo).showImageOnLoading(R.drawable.no_photo).displayer(new FadeInBitmapDisplayer(500)).cacheOnDisk(true).handler(new Handler()).build();
	}

	protected void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
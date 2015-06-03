package com.gteam.auctionsystem.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.gteam.auctionsystem.OrmActivity;
import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;
import com.gteam.auctionsystem.util.PrefManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class BaseFragment extends Fragment {

	protected static final String TAG = BaseFragment.class.getSimpleName();
	protected ProgressDialog mProgressDialog;

	protected void showProgressDialog(String title, String message) {
		try {
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			mProgressDialog = ProgressDialog.show(getActivity(), title, message);
		}
		catch (Exception ex) {
			Log.e(TAG, "##### --> " + ex);
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
			Log.e(TAG, "##### --> " + ex);
		}
	}

	protected DatabaseHelper getDBHelper() {
		DatabaseHelper dbHelper = null;
		Activity mActivity = getActivity();
		if (mActivity != null && mActivity instanceof OrmActivity) {
			dbHelper = ((OrmActivity) mActivity).getDBHelper();
		}
		return dbHelper;
	}

	protected DisplayImageOptions getDisplayOptions() {
		return new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.no_photo).showImageOnFail(R.drawable.no_photo).showImageOnLoading(R.drawable.no_photo).displayer(new FadeInBitmapDisplayer(500)).cacheOnDisk(true).handler(new Handler()).build();
	}

	protected User getUser() {
		User user = new User();
		PrefManager prefManager = new PrefManager(getActivity());
		user.setId(Long.valueOf(prefManager.readPreference(Constants.PREF_LOGGED_USER_ID, "-1")));
		return user;
	}

	protected void updateTitle(String title) {
		Activity mActivity = getActivity();
		if (mActivity != null && mActivity instanceof ActionBarActivity) {
			mActivity.setTitle(title);
			((ActionBarActivity) mActivity).getSupportActionBar().setTitle(title);
		}
	}

	protected void showToast(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
}

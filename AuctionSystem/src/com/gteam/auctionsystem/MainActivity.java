package com.gteam.auctionsystem;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.fragment.LoginFragment;
import com.gteam.auctionsystem.fragment.LoginFragment.LoginCallbacks;
import com.gteam.auctionsystem.fragment.SignupFragment;
import com.gteam.auctionsystem.fragment.SignupFragment.SignupCallbacks;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;
import com.gteam.auctionsystem.util.Utils;

public class MainActivity extends OrmActivity implements LoginCallbacks, SignupCallbacks {

	private static final String FRG_LOGIN = "FRG_LOGIN";
	private static final String FRG_SIGNUP = "FRG_SIGNUP";	

	private View welcomeArea;
	private SplashTask splashTask;
	private FragmentManager fragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		welcomeArea = findViewById(R.id.welcome_area);
		fragmentManager = getSupportFragmentManager();
		boolean skipSplash = getIntent().getBooleanExtra(Constants.EXTRA_NO_SPLASH, false);
		if (skipSplash) {
			navigateToLogin();
		} else {			
			splashTask = new SplashTask();
			splashTask.execute();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (splashTask != null && !splashTask.isCancelled()) {
			splashTask.cancel(true);
		}
	}

	private class SplashTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			int result = -1;
			// check if there is logged in user
			long userId = Long.valueOf(prefManager.readPreference(Constants.PREF_LOGGED_USER_ID, "-1"));
			boolean remember = Boolean.valueOf(prefManager.readPreference(Constants.PREF_REMEMBER_ME, "false"));
			if (userId > 0 && remember) {
				result = 0; // OK
			}
			// add some wait time
			try {
				Thread.sleep(Constants.SPLASH_TIMEOUT);
			}
			catch (InterruptedException ex) {
				Log.e(TAG, "## --> " + ex);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 0) {
				navigateToHome();
			} else {
				// no login found
				navigateToLogin();
			}
		}
	}

	private void navigateToLogin() {
		welcomeArea.setVisibility(View.GONE);
		LoginFragment loginFragment = new LoginFragment();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(R.id.fragment_container, loginFragment, FRG_LOGIN);
		ft.commit();
	}

	private void navigateToHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void login(String username, String password, boolean remember) {
		try {
			DatabaseHelper dbHelper = getDBHelper();
			password = Utils.MD5(password);
			User user = dbHelper.loginUser(username, password);
			if (user != null) {
				prefManager.writeIntoPreferences(new String[] { Constants.PREF_LOGGED_USER_ID, Constants.PREF_REMEMBER_ME }, new String[] { String.valueOf(user.getId()), String.valueOf(remember) });
				navigateToHome();
			} else {
				showToast(getString(R.string.invalid_login));
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
		catch (UnsupportedEncodingException ex) {
			Log.e(TAG, "## --> " + ex);
		}
		catch (NoSuchAlgorithmException ex) {
			Log.e(TAG, "## --> " + ex);
		}
	}

	@Override
	public void showSignup() {
		SignupFragment signupFragment = new SignupFragment();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.fragment_container, signupFragment);
		ft.addToBackStack(FRG_SIGNUP);
		ft.commit();
	}

	@Override
	public void register(User user) {
		DatabaseHelper dbHelper = getDBHelper();
		try {
			List<User> users = dbHelper.getUsersByUsername(user.getUsername());
			if (users == null || users.isEmpty()) {
				if (dbHelper.getUserRuntimeDao().create(user) == 1) {
					// user created successfully
					showToast(getString(R.string.user_created));
					prefManager.writeIntoPreferences(new String[] { Constants.PREF_LOGGED_USER_ID, Constants.PREF_REMEMBER_ME }, new String[] { String.valueOf(user.getId()), String.valueOf(true) });
					navigateToHome();
				} else {
					showToast(getString(R.string.db_user_insert_failed));
				}
			} else {
				showToast(getString(R.string.db_username_exists));
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex.getMessage());
		}
	}

	@Override
	public void cancel() {
		if (fragmentManager == null)
			fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStack();
	}
}
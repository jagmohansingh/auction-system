package com.gteam.auctionsystem.fragment;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Utils;

public class SignupFragment extends BaseFragment implements OnClickListener {

	private SignupCallbacks signupCallbacks;
	private EditText etName, etEmail, etUsername, etPassword;

	public static interface SignupCallbacks {
		void register(User user);

		void cancel();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof SignupCallbacks) {
			signupCallbacks = (SignupCallbacks) activity;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		signupCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_signup, null);
		etName = (EditText) rootView.findViewById(R.id.et_fullname);
		etEmail = (EditText) rootView.findViewById(R.id.et_email);
		etUsername = (EditText) rootView.findViewById(R.id.et_username);
		etPassword = (EditText) rootView.findViewById(R.id.et_password);
		rootView.findViewById(R.id.btnRegister).setOnClickListener(this);
		rootView.findViewById(R.id.btnCancel).setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnRegister:
				try {
					User user = new User();
					user.setFullName(etName.getText().toString().trim());
					user.setEmail(etEmail.getText().toString().trim());
					user.setUsername(etUsername.getText().toString().trim());
					user.setPassword(etPassword.getText().toString().trim());
					if (user.getUsername().length() == 0) {
						showToast(getString(R.string.empty_username));
						etUsername.requestFocus();
					} else if (user.getUsername().length() < 3) {
						showToast(getString(R.string.short_username));
						etUsername.requestFocus();
					} else if (user.getPassword().length() == 0) {
						showToast(getString(R.string.empty_password));
						etPassword.requestFocus();
					} else if (user.getPassword().length() < 5) {
						showToast(getString(R.string.short_password));
						etPassword.requestFocus();
					} else if (signupCallbacks != null) {
						user.setPassword(Utils.MD5(etPassword.getText().toString().trim()));
						signupCallbacks.register(user);
					}
				}
				catch (UnsupportedEncodingException ex) {
					Log.e(TAG, "## --> " + ex);
					showToast(getString(R.string.password_encoding));
				}
				catch (NoSuchAlgorithmException ex) {
					Log.e(TAG, "## --> " + ex);
					showToast(getString(R.string.password_encoding));
				}
				break;
			case R.id.btnCancel:
				if (signupCallbacks != null) {
					signupCallbacks.cancel();
				}
				break;
		}
	}
}
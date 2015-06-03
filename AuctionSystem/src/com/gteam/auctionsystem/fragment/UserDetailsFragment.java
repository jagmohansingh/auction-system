package com.gteam.auctionsystem.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;

public class UserDetailsFragment extends DialogFragment {

	private TextView tvName, tvUsername, tvEmail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_user_details, null);
		tvUsername = (TextView) rootView.findViewById(R.id.tvUsername);
		tvName = (TextView) rootView.findViewById(R.id.tvName);
		tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle arguments = getArguments();
		User user = (User) arguments.getParcelable(Constants.EXTRA_USER);
		tvUsername.setText(user.getUsername());
		tvName.setText(user.getFullName());
		tvEmail.setText(user.getEmail());
		getDialog().setTitle(R.string.title_user_details);
	}
}
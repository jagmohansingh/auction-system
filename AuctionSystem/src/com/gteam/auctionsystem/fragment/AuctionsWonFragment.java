package com.gteam.auctionsystem.fragment;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;

public class AuctionsWonFragment extends BaseFragment {

	private TextView emptyText;
	private ListView listAuctionsWon;
	private List<AuctionItem> auctionItems;
	private AuctionsAdapter auctionsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_auctions_won, null);
		emptyText = (TextView) rootView.findViewById(R.id.empty);
		listAuctionsWon = (ListView) rootView.findViewById(R.id.listAuctionsWon);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateTitle(getString(R.string.title_auctions_won));
		DatabaseHelper dbHelper = getDBHelper();
		try {
			if (dbHelper != null) {
				auctionItems = dbHelper.getAuctionsWon(getUser());
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
		showAuctionsWon();
	}

	private void showAuctionsWon() {
		if (auctionItems == null || auctionItems.isEmpty()) {
			emptyText.setVisibility(View.VISIBLE);
			listAuctionsWon.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			listAuctionsWon.setVisibility(View.VISIBLE);
			Context context = getActivity().getApplicationContext();
			auctionsAdapter = new AuctionsAdapter(context, auctionItems, getDisplayOptions());
			listAuctionsWon.setAdapter(auctionsAdapter);
			listAuctionsWon.setOnItemClickListener(onItemClick);
		}
	}

	private OnItemClickListener onItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			AuctionItem auctionItem = auctionItems.get(position);
			UserDetailsFragment userFragment = new UserDetailsFragment();
			Bundle arguments = new Bundle();
			arguments.putParcelable(Constants.EXTRA_USER, getOwner(auctionItem.getOwner().getId()));
			userFragment.setArguments(arguments);
			userFragment.show(getChildFragmentManager(), "FRG_USER_DETAILS");
		}
	};

	private User getOwner(long ownerId) {
		User user = null;
		DatabaseHelper dbHelper = getDBHelper();
		if (dbHelper != null) {
			user = dbHelper.getUserRuntimeDao().queryForId(ownerId);
		}
		return user;
	}
}
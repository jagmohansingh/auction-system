package com.gteam.auctionsystem.fragment;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.gteam.auctionsystem.AuctionItemActivity;
import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.util.Constants;

public class OpenAuctionsFragment extends BaseFragment {

	private TextView emptyText;
	private ListView listOpenAuctions;
	private List<AuctionItem> auctionItems;
	private AuctionsAdapter auctionsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_open_auctions, null);
		emptyText = (TextView) rootView.findViewById(R.id.empty);
		listOpenAuctions = (ListView) rootView.findViewById(R.id.listOpenAuctions);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		updateTitle(getString(R.string.title_live_auctions));		
		DatabaseHelper dbHelper = getDBHelper();
		try {
			if (dbHelper != null) {
				auctionItems = dbHelper.getOpenAuctions(getUser());
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
		showOpenAuctions();
	}

	private void showOpenAuctions() {
		if (auctionItems == null || auctionItems.isEmpty()) {
			emptyText.setVisibility(View.VISIBLE);
			listOpenAuctions.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			listOpenAuctions.setVisibility(View.VISIBLE);
			Context context = getActivity().getApplicationContext();
			auctionsAdapter = new AuctionsAdapter(context, auctionItems, getDisplayOptions());
			listOpenAuctions.setAdapter(auctionsAdapter);
			listOpenAuctions.setOnItemClickListener(onItemClick);
		}
	}
	
	private OnItemClickListener onItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			Intent intent = new Intent(getActivity(), AuctionItemActivity.class);
			intent.putExtra(Constants.EXTRA_ITEM_ID, id);
			startActivity(intent);
		}
	};
}
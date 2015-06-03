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

import com.gteam.auctionsystem.AuctionBidsActivity;
import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.util.Constants;

public class AuctionItemsFragment extends BaseFragment {

	private TextView emptyText;
	private ListView listMyAuctions;
	private List<AuctionItem> auctionItems;
	private AuctionsAdapter auctionsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_auction_items, null);
		emptyText = (TextView) rootView.findViewById(R.id.empty);
		listMyAuctions = (ListView) rootView.findViewById(R.id.listMyAuctions);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateTitle(getString(R.string.title_my_auctions));
		DatabaseHelper dbHelper = getDBHelper();
		try {
			if (dbHelper != null) {
				auctionItems = dbHelper.getMyAuctions(getUser());
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
		showMyAuctions();
	}

	private void showMyAuctions() {
		if (auctionItems == null || auctionItems.isEmpty()) {
			emptyText.setVisibility(View.VISIBLE);
			listMyAuctions.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			listMyAuctions.setVisibility(View.VISIBLE);
			Context context = getActivity().getApplicationContext();
			auctionsAdapter = new AuctionsAdapter(context, auctionItems, getDisplayOptions());
			listMyAuctions.setAdapter(auctionsAdapter);
			listMyAuctions.setOnItemClickListener(onItemClick);
		}
	}

	private OnItemClickListener onItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(), AuctionBidsActivity.class);
			AuctionItem auctionItem = auctionItems.get(position);
			intent.putExtra(Constants.EXTRA_ITEM_ID, auctionItem.getId());
			intent.putExtra(Constants.EXTRA_TITLE, auctionItem.getTitle());
			startActivity(intent);
		}
	};
}

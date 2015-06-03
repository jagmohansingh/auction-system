package com.gteam.auctionsystem;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gteam.auctionsystem.model.Bid;
import com.gteam.auctionsystem.util.Constants;

public class AuctionBidsActivity extends OrmActivity {

	private TextView emptyText;
	private ListView listAuctionBids;
	private List<Bid> bids;
	private BidsAdapter bidsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction_bids);
		setupToolbar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		emptyText = (TextView) findViewById(R.id.empty);
		listAuctionBids = (ListView) findViewById(R.id.listAuctionBids);
		long auctionId = getIntent().getLongExtra(Constants.EXTRA_ITEM_ID, -1);
		String title = getIntent().getStringExtra(Constants.EXTRA_TITLE);
		getSupportActionBar().setTitle(String.format(getString(R.string.title_auction_bids), title));
		if (auctionId == -1) {
			finish();
		} else {
			try {
				bids = getDBHelper().getAuctionBids(auctionId);
			}
			catch (SQLException ex) {
				Log.e(TAG, "## --> " + ex);
			}
		}
		showAuctionBids();
	}

	private void showAuctionBids() {
		if (bids == null || bids.isEmpty()) {
			emptyText.setVisibility(View.VISIBLE);
			listAuctionBids.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			listAuctionBids.setVisibility(View.VISIBLE);
			bidsAdapter = new BidsAdapter();
			listAuctionBids.setAdapter(bidsAdapter);
		}
	}

	private class BidsAdapter extends BaseAdapter {

		LayoutInflater inflater;

		public BidsAdapter() {
			inflater = LayoutInflater.from(AuctionBidsActivity.this);
		}

		@Override
		public int getCount() {
			return bids.size();
		}

		@Override
		public Object getItem(int position) {
			return bids.get(position);
		}

		@Override
		public long getItemId(int position) {
			return bids.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			Bid bid = (Bid) getItem(position);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.bids_list_item, null);
				holder = new ViewHolder();
				holder.amount = (TextView) convertView.findViewById(R.id.tvBidAmount);
				holder.notes = (TextView) convertView.findViewById(R.id.tvBidNotes);
				holder.bidder = (TextView) convertView.findViewById(R.id.tvBidder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.bidder.setText(String.format(getString(R.string.bid_by), bid.getBidder().getUsername()));
			holder.amount.setText(String.format(getString(R.string.bid_amount), bid.getQuotePrice()));
			if (bid.getBidNotes() != null && bid.getBidNotes().length() > 0) {
				holder.notes.setText(bid.getBidNotes());
			} else {
				holder.notes.setText("NA");
			}
			convertView.setTag(holder);
			return convertView;
		}

		class ViewHolder {
			TextView amount, notes, bidder;
		}
	}
}
package com.gteam.auctionsystem;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.gteam.auctionsystem.db.DatabaseHelper;
import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.model.Bid;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;

public class BidderService extends IntentService {

	private static final String TAG = BidderService.class.getSimpleName();
	private DatabaseHelper databaseHelper;

	public BidderService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (databaseHelper == null) {
			databaseHelper = OrmHelperManager.getHelper(TAG, this, DatabaseHelper.class);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		long itemId = intent.getLongExtra(Constants.EXTRA_ITEM_ID, -1);
		double baseAmount = intent.getDoubleExtra(Constants.EXTRA_BASE_AMOUNT, 0.0);
		// auto-bidder service
		try {
			List<User> users = databaseHelper.getSystemUser();
			if (users != null && users.size() == 1) {
				User user = users.get(0);
				AuctionItem bidFor = new AuctionItem();
				bidFor.setId(itemId);
				Bid newBid = new Bid();
				newBid.setBidFor(bidFor);
				newBid.setBidder(user);
				newBid.setQuotePrice(generateBidAmount(baseAmount));
				newBid.setBidNotes("How's our offer?");
				databaseHelper.getBidRuntimeDao().create(newBid);
			}
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
	}

	private double generateBidAmount(double baseAmount) {
		double bidAmount = 0.0;
		Random random = new Random();
		bidAmount = random.nextInt(101) + baseAmount;
		return bidAmount;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OrmHelperManager.releaseHelper(TAG);
			databaseHelper = null;
		}
	}
}

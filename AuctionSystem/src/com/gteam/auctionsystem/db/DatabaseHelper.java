package com.gteam.auctionsystem.db;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.model.Bid;
import com.gteam.auctionsystem.model.ItemPhoto;
import com.gteam.auctionsystem.model.User;
import com.gteam.auctionsystem.util.Constants;
import com.gteam.auctionsystem.util.Utils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	protected static final String TAG = DatabaseHelper.class.getName();
	public static final int DBVERSION = 1;
	public static final String DBNAME = "auctionsystem_db";

	private RuntimeExceptionDao<User, Long> userRuntimeDao = null;
	private RuntimeExceptionDao<Bid, Long> bidRuntimeDao = null;
	private RuntimeExceptionDao<AuctionItem, Long> auctionItemRuntimeDao = null;
	private RuntimeExceptionDao<ItemPhoto, Long> photosRuntimeDao = null;

	public DatabaseHelper(final Context context) {
		super(context, DatabaseHelper.DBNAME, null, DatabaseHelper.DBVERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db, final ConnectionSource connectionSource) {
		createTables();
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final ConnectionSource connectionSource, int oldVersion, final int newVersion) {
		if (oldVersion != newVersion) {
			dropAllTables();
		}
	}

	public void createTables() {
		try {
			// create the tables
			TableUtils.createTableIfNotExists(connectionSource, User.class);
			TableUtils.createTableIfNotExists(connectionSource, Bid.class);
			TableUtils.createTableIfNotExists(connectionSource, AuctionItem.class);
			TableUtils.createTableIfNotExists(connectionSource, ItemPhoto.class);
			// create system bot
			User user = new User();
			user.setUsername(Constants.SYSTEM_USERNAME);
			user.setEmail("system@gteam.com");
			user.setFullName("Auction System");
			user.setPassword(Utils.MD5("password"));
			getUserRuntimeDao().create(user);
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

	public void dropAllTables() {
		try {
			TableUtils.dropTable(connectionSource, Bid.class, true);
			TableUtils.dropTable(connectionSource, ItemPhoto.class, true);
			TableUtils.dropTable(connectionSource, AuctionItem.class, true);
			TableUtils.dropTable(connectionSource, User.class, true);
		}
		catch (SQLException ex) {
			Log.e(TAG, "## --> " + ex);
		}
	}

	public RuntimeExceptionDao<User, Long> getUserRuntimeDao() {
		if (userRuntimeDao == null) {
			userRuntimeDao = getRuntimeExceptionDao(User.class);
		}
		return userRuntimeDao;
	}

	public RuntimeExceptionDao<AuctionItem, Long> getItemRuntimeDao() {
		if (auctionItemRuntimeDao == null) {
			auctionItemRuntimeDao = getRuntimeExceptionDao(AuctionItem.class);
		}
		return auctionItemRuntimeDao;
	}

	public RuntimeExceptionDao<ItemPhoto, Long> getPhotoRuntimeDao() {
		if (photosRuntimeDao == null) {
			photosRuntimeDao = getRuntimeExceptionDao(ItemPhoto.class);
		}
		return photosRuntimeDao;
	}

	public RuntimeExceptionDao<Bid, Long> getBidRuntimeDao() {
		if (bidRuntimeDao == null) {
			bidRuntimeDao = getRuntimeExceptionDao(Bid.class);
		}
		return bidRuntimeDao;
	}

	public List<User> getUsersByUsername(String username) throws SQLException {
		QueryBuilder<User, Long> queryBuilder = getUserRuntimeDao().queryBuilder();
		queryBuilder.where().like("username", username);
		return getUserRuntimeDao().query(queryBuilder.prepare());
	}

	public User loginUser(String username, String password) throws SQLException {
		QueryBuilder<User, Long> queryBuilder = getUserRuntimeDao().queryBuilder();
		queryBuilder.where().like("username", username).and().like("password", password);
		List<User> users = getUserRuntimeDao().query(queryBuilder.prepare());
		return users.size() == 1 ? users.get(0) : null;
	}

	public List<AuctionItem> getOpenAuctions(User user) throws SQLException {
		Calendar calendar = Calendar.getInstance();
		QueryBuilder<AuctionItem, Long> queryBuilder = getItemRuntimeDao().queryBuilder();
		queryBuilder.where().le("biddingStartOn", calendar.getTime()).and().ge("biddingClosesOn", calendar.getTime()).and().ne("owner_id", user);
		return getItemRuntimeDao().query(queryBuilder.prepare());
	}

	public List<AuctionItem> getMyAuctions(User user) throws SQLException {
		QueryBuilder<AuctionItem, Long> queryBuilder = getItemRuntimeDao().queryBuilder();
		queryBuilder.where().eq("owner_id", user);
		return getItemRuntimeDao().query(queryBuilder.prepare());
	}

	public Bid getHighestBid(long auctionId) throws SQLException {
		QueryBuilder<Bid, Long> queryBuilder = getBidRuntimeDao().queryBuilder();
		queryBuilder.where().eq("item_id", auctionId);
		queryBuilder.orderBy("quotePrice", false);
		List<Bid> bids = getBidRuntimeDao().query(queryBuilder.prepare());
		if (bids != null && bids.size() > 0) {
			return bids.get(0);
		} else {
			return null;
		}
	}

	public Bid getUserBid(long itemId, long userId) throws SQLException {
		QueryBuilder<Bid, Long> queryBuilder = getBidRuntimeDao().queryBuilder();
		queryBuilder.where().eq("bidder_id", userId).and().eq("item_id", itemId);
		List<Bid> bids = getBidRuntimeDao().query(queryBuilder.prepare());
		if (bids != null && bids.size() > 0) {
			return bids.get(0);
		} else {
			return null;
		}
	}

	public List<AuctionItem> getAuctionsWon(User user) throws SQLException {
		List<AuctionItem> auctionsWon = new ArrayList<AuctionItem>();
		Calendar calendar = Calendar.getInstance();
		QueryBuilder<AuctionItem, Long> queryBuilder = getItemRuntimeDao().queryBuilder();
		queryBuilder.where().le("biddingClosesOn", calendar.getTime()).and().ne("owner_id", user);
		List<AuctionItem> expiredAuctions = getItemRuntimeDao().query(queryBuilder.prepare());
		for (AuctionItem auction : expiredAuctions) {
			Bid maxBid = new Bid();
			for (Bid bid : auction.getBids()) {
				if (bid.getQuotePrice() > maxBid.getQuotePrice()) {
					maxBid = bid;
				}
			}
			if (maxBid.getBidder() != null && maxBid.getBidder().getId() == user.getId()) {
				auctionsWon.add(auction);
			}
		}
		return auctionsWon;
	}

	public List<Bid> getAuctionBids(long auctionId) throws SQLException {
		QueryBuilder<Bid, Long> queryBuilder = getBidRuntimeDao().queryBuilder();
		queryBuilder.where().eq("item_id", auctionId);
		return getBidRuntimeDao().query(queryBuilder.prepare());
	}

	public List<User> getSystemUser() throws SQLException {
		QueryBuilder<User, Long> queryBuilder = getUserRuntimeDao().queryBuilder();
		queryBuilder.where().like("username", Constants.SYSTEM_USERNAME);
		return getUserRuntimeDao().query(queryBuilder.prepare());
	}
}
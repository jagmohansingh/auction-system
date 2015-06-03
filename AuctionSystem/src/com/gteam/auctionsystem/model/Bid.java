package com.gteam.auctionsystem.model;

import java.util.Calendar;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bids")
public class Bid {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String bidNotes;
	@DatabaseField
	private double quotePrice;
	@DatabaseField(dataType = DataType.DATE)
	private Date bidDate;
	@DatabaseField(foreign = true, columnName = "item_id")
	private AuctionItem bidFor;
	@DatabaseField(foreign = true, columnName = "bidder_id", foreignAutoRefresh=true)
	private User bidder;
	@DatabaseField(defaultValue = "0")
	private int status;

	public Bid() {
		// Default constructor
		quotePrice = 0.0;
		bidDate = Calendar.getInstance().getTime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBidNotes() {
		return bidNotes;
	}

	public void setBidNotes(String bidNotes) {
		this.bidNotes = bidNotes;
	}

	public double getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(double quotePrice) {
		this.quotePrice = quotePrice;
	}

	public AuctionItem getBidFor() {
		return bidFor;
	}

	public void setBidFor(AuctionItem bidFor) {
		this.bidFor = bidFor;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public User getBidder() {
		return bidder;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
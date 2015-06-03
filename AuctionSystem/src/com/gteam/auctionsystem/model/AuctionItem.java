package com.gteam.auctionsystem.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "auction_items")
public class AuctionItem {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField
	private double basePrice;
	@DatabaseField(dataType = DataType.DATE)
	private Date biddingStartOn;
	@DatabaseField(dataType = DataType.DATE)
	private Date biddingClosesOn;
	@DatabaseField(foreign = true, columnName = "owner_id")
	private User owner;
	@DatabaseField(dataType = DataType.DATE)
	private Date createDate;
	@ForeignCollectionField
	private Collection<ItemPhoto> itemPhotos;
	@ForeignCollectionField
	private Collection<Bid> bids;

	public AuctionItem() {
		// Default constructor
		createDate = Calendar.getInstance().getTime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public Date getBiddingStartOn() {
		return biddingStartOn;
	}

	public void setBiddingStartOn(Date biddingStartOn) {
		this.biddingStartOn = biddingStartOn;
	}

	public Date getBiddingClosesOn() {
		return biddingClosesOn;
	}

	public void setBiddingClosesOn(Date biddingClosesOn) {
		this.biddingClosesOn = biddingClosesOn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<ItemPhoto> getItemPhotos() {
		return itemPhotos;
	}

	public void setItemPhotos(Collection<ItemPhoto> itemPhotos) {
		this.itemPhotos = itemPhotos;
	}

	public Collection<Bid> getBids() {
		return bids;
	}

	public void setBids(Collection<Bid> bids) {
		this.bids = bids;
	}
}

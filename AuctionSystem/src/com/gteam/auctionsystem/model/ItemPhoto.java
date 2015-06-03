package com.gteam.auctionsystem.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item_photos")
public class ItemPhoto {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String path;
	@DatabaseField(foreign = true, columnName = "item_id")
	private AuctionItem auctionItem;

	public ItemPhoto() {
		// Default constructor
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AuctionItem getAuctionItem() {
		return auctionItem;
	}

	public void setAuctionItem(AuctionItem auctionItem) {
		this.auctionItem = auctionItem;
	}
}

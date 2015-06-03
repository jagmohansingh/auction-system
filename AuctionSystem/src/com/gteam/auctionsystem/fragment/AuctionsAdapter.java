package com.gteam.auctionsystem.fragment;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.model.AuctionItem;
import com.gteam.auctionsystem.model.ItemPhoto;
import com.gteam.auctionsystem.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AuctionsAdapter extends BaseAdapter {

	private List<AuctionItem> auctions;
	private LayoutInflater inflater;
	private String auctionEndsFormat;
	private DisplayImageOptions displayImageOptions;
	private ImageLoader imageLoader;

	public AuctionsAdapter(Context context, List<AuctionItem> items, DisplayImageOptions displayImageOptions) {
		this.auctions = items;
		this.displayImageOptions = displayImageOptions;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		auctionEndsFormat = context.getString(R.string.bidding_ends_on);
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return auctions.size();
	}

	@Override
	public Object getItem(int position) {
		return auctions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return auctions.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AuctionItem item = (AuctionItem) getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.auctions_list_item, null);
			holder = new ViewHolder();
			holder.itemImage = (ImageView) convertView.findViewById(R.id.ivItem);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.itemTitle);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.itemDesc);
			holder.tvEndsOn = (TextView) convertView.findViewById(R.id.itemAuctionEnd);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Collection<ItemPhoto> itemPhotos = item.getItemPhotos();		
		if(itemPhotos != null) {
			Iterator<ItemPhoto> iterator = itemPhotos.iterator();
			while(iterator.hasNext()) {
				ItemPhoto photo = iterator.next();				
				imageLoader.displayImage(Uri.fromFile(new File(photo.getPath())).toString(), holder.itemImage, displayImageOptions);
				break;
			}						
		}
		holder.tvTitle.setText(item.getTitle());
		holder.tvDesc.setText(item.getDescription());
		holder.tvEndsOn.setText(String.format(auctionEndsFormat, Utils.getFormattedDate(item.getBiddingClosesOn())));
		convertView.setTag(holder);
		return convertView;
	}

	static class ViewHolder {
		ImageView itemImage;
		TextView tvTitle, tvDesc, tvEndsOn;
	}
}

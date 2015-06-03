package com.gteam.auctionsystem;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class AuctionSystem extends Application {
	
	private static final int SIZE_MEM_CACHE = 5 * 1024 * 1024;
    private static final int SIZE_DISC_CACHE = 10 * 1024 * 1024;

	@Override
	public void onCreate() {
		super.onCreate();
		Context context = getApplicationContext();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCacheSize(SIZE_MEM_CACHE).diskCacheSize(SIZE_DISC_CACHE).imageDownloader(new BaseImageDownloader(context)).imageDecoder(new BaseImageDecoder(false)).threadPoolSize(50).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
	}
}
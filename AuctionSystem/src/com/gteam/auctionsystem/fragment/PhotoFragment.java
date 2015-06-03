package com.gteam.auctionsystem.fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gteam.auctionsystem.R;
import com.gteam.auctionsystem.util.Constants;

public class PhotoFragment extends BaseFragment {

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	protected AlertDialog mDialog;
	protected String mCurrentPhotoPath;

	public File getAlbumStorageDir(String albumName) {
		return new File(Environment.getExternalStorageDirectory(), albumName);
	}

	private File getAlbumDir() {
		File storageDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDir = getAlbumStorageDir(Constants.DIRECTORY_NAME);
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						showToast(getString(R.string.external_storage_na));
						return null;
					}
				}
			}
		} else {
			showToast(getString(R.string.external_storage_na));
		}
		return storageDir;
	}

	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		imageF.createNewFile();
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		File file = createImageFile();
		mCurrentPhotoPath = file.getAbsolutePath();
		return file;
	}

	protected void takePicture(int actionCode) {
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = null;
		try {
			file = setUpPhotoFile();
			mCurrentPhotoPath = file.getAbsolutePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		}
		catch (IOException ex) {
			Log.e(TAG, ex.getMessage(), ex);
			file = null;
			mCurrentPhotoPath = null;
		}
		startActivityForResult(intent, actionCode);
	}

	protected void showImage(ImageView imageView, String path) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		bitmap = BitmapFactory.decodeFile(path, options);
		imageView.setImageBitmap(bitmap);
		imageView.setVisibility(View.VISIBLE);
	}

	protected void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}
}
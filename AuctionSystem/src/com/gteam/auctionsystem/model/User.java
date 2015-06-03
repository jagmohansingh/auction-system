package com.gteam.auctionsystem.model;

import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User implements Parcelable {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(unique = true)
	private String username;
	@DatabaseField
	private String email;
	@DatabaseField
	private String fullName;
	@DatabaseField
	private String password;
	@DatabaseField(dataType = DataType.DATE)
	private Date createDate;

	public User() {
		// Default constructor
		createDate = Calendar.getInstance().getTime();
	}

	private User(Parcel in) {
		id = in.readLong();
		username = in.readString();
		email = in.readString();
		fullName = in.readString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeStringArray(new String[] { String.valueOf(id), username, email, fullName });
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
package net.accumulation.dev.android.selectimage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/11/15.
 * user: Administrator
 * date: 2018/11/15
 * time; 17:35
 * name: 视频
 */
public class VideoMedia implements Parcelable {
    private String mId;
    private String mTitle;
    private String mPath;
    private String mDuration;
    private String mSize;
    private String mDateTaken;
    private String mMimeType;

    public VideoMedia(String mId, String mTitle, String mPath, String mDuration, String mSize, String mDateTaken, String mMimeType) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mPath = mPath;
        this.mDuration = mDuration;
        this.mSize = mSize;
        this.mDateTaken = mDateTaken;
        this.mMimeType = mMimeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPath);
        dest.writeString(this.mDuration);
        dest.writeString(this.mSize);
        dest.writeString(this.mDateTaken);
        dest.writeString(this.mMimeType);
    }

    public VideoMedia() {
    }

    protected VideoMedia(Parcel in) {
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mPath = in.readString();
        this.mDuration = in.readString();
        this.mSize = in.readString();
        this.mDateTaken = in.readString();
        this.mMimeType = in.readString();
    }

    public static final Creator<VideoMedia> CREATOR = new Creator<VideoMedia>() {
        @Override
        public VideoMedia createFromParcel(Parcel source) {
            return new VideoMedia(source);
        }

        @Override
        public VideoMedia[] newArray(int size) {
            return new VideoMedia[size];
        }
    };

}

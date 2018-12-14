package net.accumulation.dev.android.selectimage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/10/23.
 * user: Administrator
 * date: 2018/10/23
 * time; 10:52
 * name: net.zhiyuan51.dev.android.getphoneimage
 */
public class ImageBean implements Parcelable {
    //图片名称
    String image_name;
    //图片大小
    long image_size;
    //图片地址
    String image_path;
    //图片描述
    String image_desc;
    //图片缩略图
    String image_thumbnails;
    //是否选中
    boolean isSelect;

    public ImageBean(String image_name, long image_size, String image_path, String image_desc, String image_thumbnails, boolean isSelect) {
        this.image_name = image_name;
        this.image_size = image_size;
        this.image_path = image_path;
        this.image_desc = image_desc;
        this.image_thumbnails = image_thumbnails;
        this.isSelect = isSelect;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_desc() {
        return image_desc;
    }

    public void setImage_desc(String image_desc) {
        this.image_desc = image_desc;
    }

    public String getImage_thumbnails() {
        return image_thumbnails;
    }

    public void setImage_thumbnails(String image_thumbnails) {
        this.image_thumbnails = image_thumbnails;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImageBean) {
            ImageBean question = (ImageBean) obj;
            return this.image_path.equals(question.getImage_path());
        }
        return super.equals(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image_name);
        dest.writeLong(this.image_size);
        dest.writeString(this.image_path);
        dest.writeString(this.image_desc);
        dest.writeString(this.image_thumbnails);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected ImageBean(Parcel in) {
        this.image_name = in.readString();
        this.image_size = in.readLong();
        this.image_path = in.readString();
        this.image_desc = in.readString();
        this.image_thumbnails = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    public long getImage_size() {
        return image_size;
    }

    public void setImage_size(long image_size) {
        this.image_size = image_size;
    }
}

package net.accumulation.dev.android.selectimage.helper;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import net.accumulation.dev.android.selectimage.ImageBean;
import net.accumulation.dev.android.selectimage.VideoMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/30.
 * user: Administrator
 * date: 2018/10/30
 * time; 9:07
 * name: net.zhiyuan51.dev.android.getphoneimage
 */
public class ImageHelper {
    String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
            + MediaStore.Images.Media.MIME_TYPE + "=? or "
            + MediaStore.Images.Media.MIME_TYPE + "=?";
    String[] whereArgs = new String[]{"image/jpeg", "image/png", "image/jpg"};
    private final static String[] MEDIA_COL = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.DURATION
    };
    List<ImageBean> imageBeans = new ArrayList<>();
    Map<Object, String> thum;
    Context mContext;
    private static ImageHelper getImageHelperInstance;

    private ImageHelper() {

    }

    public static ImageHelper getGetImageHelperInstance() {
        if (getImageHelperInstance == null) {
            synchronized (ImageHelper.class) {
                if (getImageHelperInstance == null) {
                    getImageHelperInstance = new ImageHelper();
                }
            }
        }
        return getImageHelperInstance;
    }

    /**
     * 获取缩略图
     */
    public void getThumbnail(Context mContext) {
        this.mContext = mContext;
        String[] projection = {
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA
        };
        Cursor mCursor = null;
        thum = new HashMap<>();
        //查询
        try {
            mCursor = MediaStore.Images.Thumbnails.queryMiniThumbnails(mContext.getContentResolver(), MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Thumbnails.MINI_KIND, projection);
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    String id = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
                    String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    thum.put(id, data);
                } while (mCursor.moveToNext() && !mCursor.isLast());
            }
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
    }

    /**
     * h获取正常图片的path
     */
    public List<ImageBean> getRealAddress() {
        imageBeans.clear();
        Cursor mCursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DESCRIPTION,
                },
                where,
                whereArgs,
                MediaStore.Images.Media.DATE_MODIFIED + " desc "
        );
        if (mCursor.moveToFirst()) {
            do {
                String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String data = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String desc = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String id = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
                imageBeans.add(new ImageBean(name, size, data, desc, thum.get(id), false));
            } while (mCursor.moveToNext());
            mCursor.close();
        }
        return imageBeans;
    }


    public List<VideoMedia> getVideoList() {
        final List<VideoMedia> videoMedias = new ArrayList<>();
        final Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                MEDIA_COL, null, null,
                MediaStore.Images.Media.DATE_MODIFIED);
        try {
            int count = 0;
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getCount();
                do {
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                    String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    String date = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));
                    String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    VideoMedia video = new VideoMedia(id, title, data, duration, size, date, type);
                    videoMedias.add(video);

                } while (!cursor.isLast() && cursor.moveToNext());
                //   postMedias(videoMedias, count);
            } else {
                // postMedias(videoMedias, 0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return videoMedias;
    }

}

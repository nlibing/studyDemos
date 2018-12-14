package net.accumulation.dev.android.selectimage.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import net.accumulation.dev.android.selectimage.ImageBean;
import net.accumulation.dev.android.selectimage.helper.ImageLoadCallBack;
import net.accumulation.dev.android.worklib.R;

import java.lang.ref.WeakReference;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2018/11/14.
 * user: Administrator
 * date: 2018/11/14
 * time; 11:36
 * name: 预览frmanet
 */
public class PreviewFrament extends BoxingBaseFragment {
    private static final String BUNDLE_IMAGE="preview_iamge";
    private static final int MAX_SCALE = 15;
    private static final long MAX_IMAGE1 = 1024 * 1024L;
    private static final long MAX_IMAGE2 = 4 * MAX_IMAGE1;
    ImageBean imageBean;
    private PhotoView mImageView;
    private ProgressBar mProgress;
    private PhotoViewAttacher mAttacher;
    public static PreviewFrament newInstance(ImageBean imageBean) {
        PreviewFrament fragment = new PreviewFrament();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_IMAGE, imageBean);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageBean = getArguments().getParcelable(BUNDLE_IMAGE);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgress = view.findViewById(R.id.loading);
        mImageView =  view.findViewById(R.id.photo_view);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setRotatable(true);
        mAttacher.setToRightAngle(true);
    }
    private PreviewSelectImageActivity getThisActivity() {
        Activity activity = getActivity();
        if (activity instanceof PreviewSelectImageActivity) {
            return (PreviewSelectImageActivity) activity;
        }
        return null;
    }
    private void dismissProgressDialog() {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
        }
        PreviewSelectImageActivity activity = getThisActivity();
        if (activity != null && activity.progressDialog!= null) {
            if (activity.progressDialog.isShowing()) {
                activity.progressDialog.dismiss();
            }
            activity.progressDialog=null;
        }
    }

    @Override
    void setUserVisibleCompat(boolean userVisibleCompat) {
        if (userVisibleCompat) {
            Point point = getResizePointer(imageBean.getImage_size());
            displayRaw(mImageView, imageBean.getImage_path(), point.x, point.y, new BoxingCallback(this));
        }
    }

    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, int width, int height, final BoxingCallback callback) {
        String path = "file://" + absPath;
        BitmapTypeRequest<String> request = Glide.with(img.getContext())
                .load(path)
                .asBitmap();
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onFail(e);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (resource != null && callback != null) {
                    img.setImageBitmap(resource);
                    callback.onSuccess();
                    return true;
                }
                return false;
            }
        }).into(img);

    }
    /**
     * resize the image or not according to size.
     *
     * @param size the size of image
     */
    private Point getResizePointer(long size) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Point point = new Point(metrics.widthPixels, metrics.heightPixels);
        if (size >= MAX_IMAGE2) {
            point.x >>= 2;
            point.y >>= 2;
        } else if (size >= MAX_IMAGE1) {
            point.x >>= 1;
            point.y >>= 1;
        } else if (size > 0) {
            // avoid some images do not have a size.
            point.x = 0;
            point.y = 0;
        }
        return point;
    }



    private static class BoxingCallback implements ImageLoadCallBack {
        private WeakReference<PreviewFrament> mWr;

        BoxingCallback(PreviewFrament fragment) {
            mWr = new WeakReference<>(fragment);
        }

        @Override
        public void onSuccess() {
            if (mWr.get() == null || mWr.get().mImageView == null) {
                return;
            }
            mWr.get().dismissProgressDialog();
            Drawable drawable = mWr.get().mImageView.getDrawable();
            PhotoViewAttacher attacher = mWr.get().mAttacher;
            if (attacher != null) {
                if (drawable.getIntrinsicHeight() > (drawable.getIntrinsicWidth() << 2)) {
                    // handle the super height image.
                    int scale = drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                    scale = Math.min(MAX_SCALE, scale);
                    attacher.setMaximumScale(scale);
                    attacher.setScale(scale, true);
                }
                attacher.update();
            }
            PreviewSelectImageActivity activity = mWr.get().getThisActivity();
            if (activity != null && activity.vp_images != null) {
                activity.vp_images.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFail(Throwable t) {
            if (mWr.get() == null) {
                return;
            }
            Log.d("onFail", "load raw image error.");
            mWr.get().dismissProgressDialog();
            mWr.get().mImageView.setImageResource(R.drawable.ic_boxing_default_image);
            if (mWr.get().mAttacher != null) {
                mWr.get().mAttacher.update();
            }
        }
    }

}

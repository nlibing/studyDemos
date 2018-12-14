package net.accumulation.dev.android.selectimage.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/11/9.
 * user: Administrator
 * date: 2018/11/9
 * time; 14:09
 * name: net.zhiyuan51.dev.android.selectimage.view
 */
public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener,ViewTreeObserver.OnGlobalLayoutListener{

    private static final  float SCALE_MAX = 4.0f;

    //初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0；
    private float initScale = 1.0f;
    //用于存放矩阵的9个值
    private final float[] matrixValues = new float[9];
    private boolean once = true;

    //缩放的手势检测
    private ScaleGestureDetector mScaleGestureDetector = null;

    private final Matrix mScaleMatrix = new Matrix();
    private float scale;
    private RectF matrixRectF;


    public ZoomImageView(Context context) {
        this(context,null);
    }
    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }



    @Override
    public void onGlobalLayout() {
        if(once){
            Drawable d = getDrawable();
            if(d == null){
                return;
            }
            int width =getWidth();
            int height = getHeight();
            //拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;
            //如果图片的宽和高大于屏幕，则缩放至屏幕的宽或高
            if(dw > width && dh <= height){
                scale = width*1.0f/dw;
            }
            if(dh>height && dw <=width){
                scale = height*1.0f/dh;
            }
            //如果高度和宽度都大于屏幕，则让其按比例适应屏幕大小
            if(dw > width && dh > height){
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            initScale = scale;

            //图片移到屏幕中心
            mScaleMatrix.postTranslate((width - dw)/2,(height - dh)/2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);
            once = false;
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null){
            return true;
        }

        //缩放范围控制
        if((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale>initScale && scaleFactor<1.0f)){
            //最大最小值判断
            if(scaleFactor*scale <initScale){
                scaleFactor = initScale/scale;
            }
            if(scaleFactor*scale > SCALE_MAX){
                scaleFactor = SCALE_MAX / scale;
            }
            //设置缩放比例
            //  mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(),detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rectF = getMatrixRectF();
        float deltaX= 0,deltaY = 0;
        int width = getWidth();
        int height = getHeight();

        //如果宽或高大于屏幕，则控制范围
        if(rectF.width() >= width){
            if(rectF.left>0){
                deltaX = -rectF.left;
            }
            if (rectF.right<width){
                deltaX  =width - rectF.right;
            }
        }

        if(rectF.height()>=height){

            if (rectF.top >0){
                deltaY = -rectF.top;
            }
            if(rectF.bottom < height){
                deltaY = height - rectF.bottom;
            }
        }

        //如果宽度或高度小于屏幕，则居中、
        if (rectF.width()<width){
            deltaX = width*0.5f  - rectF.right + 0.5f*rectF.width();
        }
        if(rectF.height()<height){
            deltaY = height*0.5f - rectF.bottom + 0.5f*rectF.height();
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    /**
     * 获得当前的缩放比例
     * @return
     */
    public float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 根据当前图片的Matrix获取图片的范围
     * @return
     */
    public RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if(d != null){
            rectF.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }
}
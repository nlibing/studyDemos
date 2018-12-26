package dev.android.studydemo.view.doodling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/12/21.
 * user: Administrator
 * date: 2018/12/21
 * time; 9:35
 * name: 涂鸦板（未完成）
 */
public class DrawView extends View {
    private Path linePath;
    private Paint linePaint;
    private float mPreX, mPreY;
    public static String PATTERN_INPUT = "input";//输入文本
    public static String PATTERN_DOODLING = "doodling";//涂鸦
    private String PATTERN_DEFAULT = "doodling";//默认
    private Paint titlePaint;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float down_x;
    private float down_y;
    private String input_content = "";
    private Paint inputPaint;
    private Canvas mBmpCanvas;
    private Bitmap bitmap;
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePath = new Path();
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(2);
        inputPaint = new Paint();
        inputPaint.setColor(Color.RED);
        inputPaint.setTextSize(32);
        inputPaint.setTextAlign(Paint.Align.LEFT);
        titlePaint = new Paint();
        titlePaint.setStyle(Paint.Style.STROKE);
        titlePaint.setStrokeWidth(2);
        titlePaint.setColor(Color.RED);
        startX = 100;
        startY = 10;
        endX = 200;
        endY = 60;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        mBmpCanvas = new Canvas(this.bitmap);
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (PATTERN_DEFAULT.equals(PATTERN_DOODLING)) {
                    linePath.moveTo(event.getX(), event.getY());
                    mPreX = event.getX();
                    mPreY = event.getY();
                    return true;
                } else {
                    if (startY < event.getY() && event.getY() < endY && startX < event.getX() && event.getX() < endX) {
                        down_x = event.getX();
                        down_y = event.getY();
                        return true;
                    } else {
                        return false;
                    }
                }


            case MotionEvent.ACTION_MOVE:
                if (PATTERN_DEFAULT.equals(PATTERN_DOODLING)) {
                    float endX = (mPreX + event.getX()) / 2;
                    float endY = (mPreY + event.getY()) / 2;
                    linePath.quadTo(endX, endY, event.getX(), event.getY());
                    mPreX = event.getX();
                    mPreY = event.getY();
                    invalidate();
                } else {
                    //进入文本输入模式，显示框可以挪移
                    startX = startX + (event.getX() - down_x);
                    endX = startX + 100;
                    startY = startY + (event.getY() - down_y);
                    endY = startY + 50;
                    //判断是否到边界，进行处理
                    if (endX > getWidth()) {
                        startX = getWidth() - 100;
                        endX = getWidth();
                    }
                    if (startX < 0) {
                        startX = 0;
                        endX = 100;
                    }
                    if (startY < 0) {
                        startY = 0;
                        endY = 50;
                    }
                    if (endY > getHeight()) {
                        startY = getHeight() - 50;
                        endY = getHeight();
                    }
                    invalidate();
                    down_x = event.getX();
                    down_y = event.getY();
                }
            default:
                break;

        }
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBmpCanvas!=null){
            if (PATTERN_DEFAULT.equals(PATTERN_INPUT)) {
                mBmpCanvas.drawPath(linePath, linePaint);
                mBmpCanvas.drawText(input_content, startX, endY, inputPaint);
                mBmpCanvas.drawRect(startX, startY, endX, endY, titlePaint);
            }else {
                if(!TextUtils.isEmpty(input_content)){
                    mBmpCanvas.drawText(input_content, startX, endY, inputPaint);
                    mBmpCanvas.drawRect(startX, startY, endX, endY, titlePaint);
                }
                mBmpCanvas.drawPath(linePath, linePaint);
            }
            canvas.drawBitmap(bitmap, 0,0, linePaint);
        }

    }

    public void setContent(String content) {
        this.input_content = content;
        invalidate();
    }

    public void replacePattern(String pattern) {
        this.PATTERN_DEFAULT = pattern;
        invalidate();
    }
}

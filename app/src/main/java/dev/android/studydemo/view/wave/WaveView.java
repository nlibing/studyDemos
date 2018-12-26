package dev.android.studydemo.view.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2018/12/26.
 * user: Administrator
 * date: 2018/12/26
 * time; 15:02
 * name: 波浪线
 */
public class WaveView extends View {
    private int startX = 0;
    private int startY = 200;
    int dx;
    Path path ;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path =  new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        //因为平移200，所以话弧线的起始点要在坐标-200开始绘制
        startX=-200+dx;
        //起始点-200，y
        path.moveTo(startX, startY);
        for (int i = -100+dx; i <= getWidth() + 100+dx; i += 100) {
            startX = i - 100 + i;
            //x1弧线的起点(例如2个点的距离是100，如果想让这个弧线的在这2个点之间那么就是50)
            //y1：弧线的高度
            // (如果只是一个方向的弧度那么这个值设置一样就可，
            //   *      *
            // *   *  *   *
            //*     *       *
            // 如果想出现波浪，向上的弧度如果距离y轴是50)
            //   *           *
            // *   *       *   *
            //*     *     *     *
            //       *   *
            //         *
            //第一条线path.quadTo(x1, y2-50, x2, y2);
            //第二条线path.quadTo(x1, y2+50, x2, y2);
            //x2:连接点的x坐标
            //y2:连接点的y坐标
            Log.d("onDraw","x1="+startX / 2+"y1="+startY+"x2="+i+"y2="+startY);
            path.quadTo((startX )/ 2, 100, i, startY);
        }
       /* path.moveTo(0, 400);
        path.quadTo(50, 200, 100, 400);
        path.quadTo(150, 600, 200, 400);
        path.quadTo(250, 200, 300, 400);
        path.quadTo(350, 600, 400, 400);
        path.quadTo(450, 200, 500, 400);*/
        canvas.drawPath(path, paint);
    }

    public void startAnim(){
        //平移200
        ValueAnimator animator = ValueAnimator.ofInt(0,200);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}

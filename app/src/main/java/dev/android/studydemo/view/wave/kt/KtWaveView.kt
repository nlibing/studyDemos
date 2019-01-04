package dev.android.studydemo.view.wave.kt

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout

/**
 * Created by Administrator on 2019/1/4.
 * user: Administrator
 * date: 2019/1/4
 * time; 10:39
 * name: kt波浪线
 */
class KtWaveView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint:Paint=Paint()
    var mPach:Path=Path()
    var startX:Float=0f
    var startY:Float=200f
    var dx:Int=0
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPach.reset()
        mPaint.color=Color.BLACK
        mPaint.style=Paint.Style.STROKE
        startX= (-200+dx).toFloat()
        mPach.moveTo(startX,startY)
        for (i in (-100+dx)..(width+dx+100)step 100){
            startX = (i - 100 + i).toFloat()
            Log.d("onDraw","x1="+startX / 2+"y1="+startY+"x2="+i+"y2="+startY)
            mPach.quadTo((startX )/ 2, 100F, i.toFloat(), startY)
        }
        canvas!!.drawPath(mPach,mPaint)
    }
    fun startAnim(){
        val animator:ValueAnimator= ValueAnimator.ofInt(200)
        animator.duration=2000
        animator.repeatCount=ValueAnimator.RESTART
        animator.interpolator=LinearInterpolator()
        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            dx = it.animatedValue as Int
            postInvalidate()
        })
        animator.start()
    }
}
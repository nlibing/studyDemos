package dev.android.studydemo.view.calendar.kt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dev.android.studydemo.view.calendar.java.CalendarUtils
import java.util.*

/**
 * Created by Administrator on 2019/1/4.
 * user: Administrator
 * date: 2019/1/4
 * time; 15:47
 * name:
 */
class KtCalendarView : View {
    private lateinit var week: Array<String>
    private lateinit var linePath: Path
    private lateinit var linePaint: Paint
    private lateinit var weekPaint: Paint
    private lateinit var surplusPaint: Paint
    private var nowYear: Int = 0
    private var nowMonth: Int = 0
    private var multipleX: Int = 0
    private var multipleY: Int = 0
    private var downX: Double = 0.0
    private var downY: Double = 0.0
    private lateinit var surplusPaintMetrics: Paint.FontMetricsInt
    private lateinit var   fontMetrics: Paint.FontMetricsInt
    private var num:Int=1
    private var isFirst:Boolean=true
    private var day:Int=0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        week = arrayOf("日", "一", "二", "三", "四", "五", "六")
        linePath = Path()
        linePaint = Paint()
        linePaint.style=Paint.Style.STROKE
        linePaint.strokeWidth=2f
        linePaint.color=Color.RED
        weekPaint = Paint()
        weekPaint.style=Paint.Style.STROKE
        weekPaint.color=Color.BLACK
        weekPaint.textAlign=Paint.Align.CENTER
        surplusPaint = Paint()
        weekPaint.style=Paint.Style.STROKE
        weekPaint.color=Color.RED
        weekPaint.textAlign=Paint.Align.CENTER
        nowYear=getCurrentYear()
        nowMonth=getCurrentMonth()
        surplusPaintMetrics = surplusPaint.fontMetricsInt
        fontMetrics=weekPaint.fontMetricsInt
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                downX= event!!.x.toDouble()
                downY= event!!.y.toDouble()
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        multipleX=width/7
        multipleY=height/7
        for (i:Int in 0 until 7){
            linePath.moveTo(0f, (i * multipleY).toFloat())
            linePath.lineTo(width.toFloat(), (i * multipleY).toFloat())
        }
        for (i:Int in 0 until 7) {
            linePath.moveTo((i * multipleX).toFloat(), 0f)
            linePath.lineTo((i * multipleX).toFloat(), height.toFloat())
        }
        for (i in 0 until week.size) {
            //星期
            canvas!!.drawText(week[i], (i * multipleX + multipleX / 2).toFloat(), ((multipleY - fontMetrics.bottom - fontMetrics.top) / 2).toFloat(), weekPaint)
        }
        for (i:Int in 0..getMonthLastDay(nowYear,nowMonth)){
             day = getWeekNum(nowYear, nowMonth, i);
            if (isFirst) {
                //addUpSurplusDay(day,canvas,num);
                isFirst=false
            }
            if (day == 7) {
                num++//换行也就会y轴高度增加1倍
                day = 0//换行后x轴起点应该从头开始
            }
            if (i == getMonthLastDay(nowYear, nowMonth) - 1) {
                //判断时候是当月的最后一天
                //当前还有空余位置则显示下一个月的日期
                //addSurplusDay(day, canvas, num);
            }
            if(downX>day*multipleX&&downX<(day+1)*multipleX
                    &&downY>num*multipleY&&downY<(num+1)*multipleY){
                weekPaint.color=Color.BLUE
                //选中效果
                canvas!!.drawCircle((day * multipleX + multipleX / 2).toFloat(),
                        (multipleY/ 2 + multipleY * num).toFloat(),
                        25f,weekPaint)
                canvas!!.drawText((i + 1).toString(),
                        (day * multipleX + multipleX / 2).toFloat(),
                        ((multipleY - fontMetrics.bottom - fontMetrics.top) / 2 + multipleY * num).toFloat(),
                        weekPaint)
                CalendarUtils.getLunarDateINT(nowYear,nowMonth,(i+1))
                CalendarUtils.getLunarString(nowYear,nowMonth,(i+1))
            }else {
                weekPaint.color=Color.BLACK
                //公历显示
                canvas!!.drawText(((i + 1).toString()),
                        (day * multipleX + multipleX / 2).toFloat(),
                        ((multipleY - fontMetrics.bottom - fontMetrics.top) / 2 + multipleY * num).toFloat(),
                        weekPaint)

            }
        }
    }
   private fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }
    private  fun  getCurrentYear():Int{
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    /**
     * 获取当前年月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private fun getMonthLastDay(year: Int, month: Int): Int {
        val a = Calendar.getInstance()
        a.set(Calendar.YEAR, year)
        a.set(Calendar.MONTH, month - 1)
        a.set(Calendar.DATE, 1)//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1)//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE)
    }

    /**
     * 获取当前日期是是星期几
     * 0是1号
     *
     * @param year
     * @param month
     * @return
     */
   private fun getWeekNum(year: Int, month: Int, day: Int): Int {
        val calendar = Calendar.getInstance()
        val currYear = calendar.get(Calendar.YEAR)
        val currMouth = calendar.get(Calendar.MONTH) + 1
        val currDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month - 1, day)

        val i1 = calendar.get(Calendar.DAY_OF_WEEK)//这就是星期几\
        return i1
    }
}
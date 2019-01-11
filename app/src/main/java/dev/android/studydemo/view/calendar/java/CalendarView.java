package dev.android.studydemo.view.calendar.java;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/12/27.
 * user: Administrator
 * date: 2018/12/27
 * time; 15:10
 * name: 日历
 */
public class CalendarView extends View {
    private String[] week = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    //农历月份
    private Path linePath;
    private Paint linePaint;
    private Paint weekPaint;
    private Paint surplusPaint;
    private int nowYear;
    private int nowMonth;
    private int multipleX;
    private int multipleY;
    private int downX;
    private int downY;
    Paint.FontMetricsInt surplusPaintMetrics;
    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePath = new Path();
        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        weekPaint = new Paint();
        weekPaint.setStyle(Paint.Style.STROKE);
        weekPaint.setColor(Color.BLACK);
        weekPaint.setTextAlign(Paint.Align.CENTER);
        surplusPaint = new Paint();
        surplusPaint.setStyle(Paint.Style.STROKE);
        surplusPaint.setColor(Color.RED);
        surplusPaint.setTextAlign(Paint.Align.CENTER);
        nowYear = getCurrentYear();
        nowMonth = getCurrentMonth();
        surplusPaintMetrics = surplusPaint.getFontMetricsInt();
    }

    public String getTime() {
        return nowYear + "年" + nowMonth + "月";
    }

    public void addMonth() {
        this.nowMonth = this.nowMonth + 1;
        if (this.nowMonth > 12) {
            this.nowMonth = 1;
            this.nowYear = this.nowYear + 1;
        }
        invalidate();
    }

    public void delMonth() {
        this.nowMonth = this.nowMonth - 1;
        if (this.nowMonth == 0) {
            this.nowYear = this.nowYear - 1;
            this.nowMonth = 12;
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX= (int) event.getX();
                downY= (int) event.getY();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        multipleX = getWidth() / 7;
        multipleY = getHeight() / 7;
        for (int i = 0; i < 7; i++) {
            linePath.moveTo(0, i * multipleY);
            linePath.lineTo(getWidth(), i * multipleY);
        }
        for (int i = 0; i < 7; i++) {
            linePath.moveTo(i * multipleX, 0);
            linePath.lineTo(i * multipleX, getHeight());
        }
        Paint.FontMetricsInt fontMetrics = weekPaint.getFontMetricsInt();
        for (int i = 0; i < week.length; i++) {
            //星期
            canvas.drawText(week[i], i * multipleX + multipleX / 2, (multipleY - fontMetrics.bottom - fontMetrics.top) / 2, weekPaint);
        }
        int num = 1;
        boolean isFirst = true;
        for (int i = 0; i < getMonthLastDay(nowYear, nowMonth); i++) {
            int day = getWeekNum(nowYear, nowMonth, i);
            //返回的数据格式是7,1,2,3,4,5,6等于7的时候就需要换一行
            if (isFirst) {
                addUpSurplusDay(day,canvas,num);
                isFirst=false;
            }
            if (day == 7) {
                num++;//换行也就会y轴高度增加1倍
                day = 0;//换行后x轴起点应该从头开始
            }
            if (i == getMonthLastDay(nowYear, nowMonth) - 1) {
                //判断时候是当月的最后一天
                //当前还有空余位置则显示下一个月的日期
                addSurplusDay(day, canvas, num);
            }
            if(downX>day*multipleX&&downX<(day+1)*multipleX
                    &&downY>num*multipleY&&downY<(num+1)*multipleY){
                weekPaint.setColor(Color.BLUE);
                //选中效果
                canvas.drawCircle(day * multipleX + multipleX / 2,
                        multipleY/ 2 + multipleY * num,
                        25,weekPaint);
                canvas.drawText(i + 1 + "",
                        day * multipleX + multipleX / 2,
                        (multipleY - fontMetrics.bottom - fontMetrics.top) / 2 + multipleY * num,
                        weekPaint);
                CalendarUtils.getLunarDateINT(nowYear,nowMonth,(i+1));
                CalendarUtils.getLunarString(nowYear,nowMonth,(i+1));
            }else {
                weekPaint.setColor(Color.BLACK);
                //公历显示
                canvas.drawText((i + 1 )+"",
                        day * multipleX + multipleX / 2,
                        (multipleY - fontMetrics.bottom - fontMetrics.top) / 2 + multipleY * num,
                        weekPaint);

            }
        }
        canvas.drawPath(linePath, linePaint);
    }

    /**
     * 补全下方当前缺失的日期
     *
     * @param day
     * @param canvas
     * @param num
     */
    public void addSurplusDay(int day, Canvas canvas, int num) {
        //判断从第几个开始
        if (day == 7) {
            day = 0;
        }
        int lastNum = 0;
        if (day != 6) {
            for (int n = 0; n < 7 - day; n++) {
                if (n == 0) {
                    n = 1;
                }
                canvas.drawText(n + "", (day + n) * multipleX + multipleX / 2, (multipleY - surplusPaintMetrics.bottom - surplusPaintMetrics.top) / 2 + multipleY * num, surplusPaint);
                lastNum = n;
            }
        }
        while (num != 6) {
            num++;
            for (int n = 0; n < 7 + lastNum; n++) {
                canvas.drawText((n + 1 + lastNum) + "", (n) * multipleX + multipleX / 2, (multipleY - surplusPaintMetrics.bottom - surplusPaintMetrics.top) / 2 + multipleY * num, surplusPaint);
            }
        }
    }

    /**
     * 不全初始缺少的
     * @param day
     * @param canvas
     * @param num
     */
    public void addUpSurplusDay(int day, Canvas canvas, int num){
        int up_month_day;
        int stopDay=day;
        //判断上一个月的天数
        if(nowMonth==1){
            up_month_day =getMonthLastDay(nowYear-1,12);
        }else {
            up_month_day =getMonthLastDay(nowYear,nowMonth-1);
        }
        for (int f =up_month_day; f >= up_month_day-day; f--) {
            stopDay--;
            canvas.drawText(f  + "", stopDay * multipleX + multipleX / 2,
                    (multipleY - surplusPaintMetrics.bottom - surplusPaintMetrics.top) / 2 + multipleY * num, surplusPaint);

        }
    }

    /**
     * 获取当前日期是是星期几
     * 0是1号
     *
     * @param year
     * @param month
     * @return
     */
    public int getWeekNum(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMouth = calendar.get(Calendar.MONTH) + 1;
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month - 1, day);

        int i1 = calendar.get(Calendar.DAY_OF_WEEK);//这就是星期几\
        return i1;
    }


    /**
     * @return 当前月份【1月就是1】
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前年月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }




}

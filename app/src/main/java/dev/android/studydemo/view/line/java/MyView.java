package dev.android.studydemo.view.line.java;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/12/17.
 * user: Administrator
 * date: 2018/12/17
 * time; 19:39
 * name: 折线图和曲线图(参考网址https://blog.csdn.net/harvic880925/article/details/50995587)
 */
public class MyView extends View {

    public String[] dataX = new String[]{"周一", "周二", "周三", "周四", "周五", "周六"};
    public String[] dataY = new String[]{"0", "1", "10", "100", "1000", "10000"};
    int[] value_spot = new int[]{0, 8, 367, 786, 666, 300};
    public int starX;
    //网格画笔
    private Paint gridPaint;
    private Path gridPath;
    //折线画笔
    private Paint foldingPaint;
    private Path foldingPath;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);//画网格
        drawGridStraight(canvas);//画折线
        // drawGridSpot(canvas);//画曲线全部向上
        drawWave(canvas);//画波浪曲线
    }


    //画曲线
    public void drawCurve(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        Path path = new Path();
        path.moveTo(100, 100);
        path.quadTo(150, 50, 200, 100);
        path.quadTo(250, 150, 300, 100);
        canvas.drawPath(path, paint);
    }

    //划单线x轴y轴
    public void drawLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        Path linePath = new Path();
        //Y轴
        linePath.moveTo(10, 10);
        linePath.lineTo(10, 400);
        //x轴
        linePath.moveTo(10, 400);
        linePath.lineTo(400, 400);
        canvas.drawPath(linePath, paint);
    }

    //画网格
    public void drawGrid(Canvas canvas) {
        gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(Color.BLUE);
        gridPath = new Path();
        for (int i = 0; i < 8; i++) {
            //x轴
            //x轴起始点(50,400)
            gridPath.moveTo(50, 400 - (50 * i));
            //轴起始终点(400,400)
            gridPath.lineTo(400, 400 - (50 * i));
            //循环形成7条x轴线
            //Y轴
            //y轴起始点(50,50)
            gridPath.moveTo(50 + (50 * i), 50);
            //y轴起始终点(50,400)
            gridPath.lineTo(50 + (50 * i), 400);
            //循环形成7条Y轴线
        }
        for (int i = 0; i < dataX.length; i++) {
            //x轴值
            gridPaint.setTextAlign(Paint.Align.CENTER);//意思这个文本在(100,420)这个点中间
            canvas.drawText(dataX[i], 100 + (50 * i), 420, gridPaint);
        }
        for (int i = 0; i < dataY.length; i++) {
            //y轴值
            gridPaint.setTextAlign(Paint.Align.RIGHT);//意思这个文本在(40,355)这个点右间
            canvas.drawText(dataY[i], 40, 355 - (50 * i), gridPaint);
        }
        canvas.drawPath(gridPath, gridPaint);
    }

    //画波浪曲线
    private void drawWave(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        Path path = new Path();
        for (int i = 0; i < value_spot.length; i++) {
            if (i == 0) {
                StartLine(path, 0);
            } else {
                //y1的值控制弧度
                if (value_spot[i] > 1 && value_spot[i] < 10) {
                    if (i % 2 == 0) {
                        path.quadTo((starX + (100 + i * 50)) / 2, 400, (100 + i * 50), 300 - (value_spot[i] % 10) * 5);
                    } else {
                        path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), 300 - (value_spot[i] % 10) * 5);
                    }
                } else if (value_spot[i] > 10 && value_spot[i] < 100) {
                    if (i % 2 == 0) {
                        path.quadTo((starX + (100 + i * 50)) / 2, 400, (100 + i * 50), (float) (250 - (value_spot[i] % 100) * 0.5));
                    } else {
                        path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (250 - (value_spot[i] % 100) * 0.5));
                    }
                } else if (value_spot[i] > 100 && value_spot[i] < 1000) {
                    if (i % 2 == 0) {
                        path.quadTo((starX + (100 + i * 50)) / 2, 400, (100 + i * 50), (float) (200 - (value_spot[i] % 1000) * 0.05));
                    } else {
                        path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (200 - (value_spot[i] % 1000) * 0.05));
                    }
                } else if (value_spot[i] > 1000 && value_spot[i] < 10000) {
                    if (i % 2 == 0) {
                        path.quadTo((starX + (100 + i * 50)) / 2, 400, (100 + i * 50), (float) (150 - (value_spot[i] % 10000) * 0.005));
                    } else {
                        path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (150 - (value_spot[i] % 10000) * 0.005));
                    }

                }
                starX = (100 + i * 50);
            }

        }
        canvas.drawPath(path, paint);
    }

    //画网格曲线图
    private void drawGridSpot(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        Path path = new Path();
        for (int i = 0; i < value_spot.length; i++) {
            if (i == 0) {
                StartLine(path, 0);
            } else {
                if (value_spot[i] > 1 && value_spot[i] < 10) {
                    path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), 300 - (value_spot[i] % 10) * 5);
                    starX = (100 + i * 50);
                } else if (value_spot[i] > 10 && value_spot[i] < 100) {
                    path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (250 - (value_spot[i] % 100) * 0.5));
                    starX = (100 + i * 50);
                } else if (value_spot[i] > 100 && value_spot[i] < 1000) {
                    path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (200 - (value_spot[i] % 1000) * 0.05));
                    starX = (100 + i * 50);
                } else if (value_spot[i] > 1000 && value_spot[i] < 10000) {
                    path.quadTo((starX + (100 + i * 50)) / 2, 25, (100 + i * 50), (float) (150 - (value_spot[i] % 10000) * 0.005));
                    starX = (100 + i * 50);
                }
            }

        }
        canvas.drawPath(path, paint);
    }

    //画网格折线图
    public void drawGridStraight(Canvas canvas) {
        foldingPaint = new Paint();
        foldingPaint.setStyle(Paint.Style.STROKE);
        foldingPaint.setColor(Color.RED);
        foldingPath = new Path();
        for (int i = 0; i < value_spot.length; i++) {
            if (i == 0) {
                //设置折线的起始点
                StartLine(foldingPath, 0);
            } else {
                //为什么都是100？，因为这里是从第二个点开始画，第一点是50，所以起始x轴100+
                if (value_spot[i] > 1 && value_spot[i] < 10) {
                    foldingPath.lineTo((100 + i * 50), 300 - (value_spot[i] % 10) * 5);
                } else if (value_spot[i] > 10 && value_spot[i] < 100) {
                    foldingPath.lineTo((100 + i * 50), (float) (250 - ((value_spot[i] % 100) * 0.5)));
                } else if (value_spot[i] > 100 && value_spot[i] < 1000) {
                    foldingPath.lineTo((100 + i * 50), (float) (200 - (value_spot[i] % 1000) * 0.05));
                } else if (value_spot[i] > 1000 && value_spot[i] < 10000) {
                    foldingPath.lineTo((100 + i * 50), (float) (150 - (value_spot[i] % 10000) * 0.005));
                } else if (value_spot[i] == 0) {
                    foldingPath.lineTo((100 + i * 50), 350);
                } else if (value_spot[i] == 1) {
                    foldingPath.lineTo((100 + i * 50), 300);
                } else if (value_spot[i] == 10) {
                    foldingPath.lineTo((100 + i * 50), 250);
                } else if (value_spot[i] == 100) {
                    foldingPath.lineTo((100 + i * 50), 200);
                } else if (value_spot[i] == 1000) {
                    foldingPath.lineTo((100 + i * 50), 150);
                } else if (value_spot[i] == 10000) {
                    foldingPath.lineTo((100 + i * 50), 100);
                }
            }
        }
        canvas.drawPath(foldingPath, foldingPaint);
    }

    //起始点
    public void StartLine(Path path, int i) {
        starX = 100;
        if (value_spot[i] == 0) {
            path.moveTo(starX, 350);
        } else if (value_spot[i] == 1) {
            path.moveTo(starX, 300);
        } else if (value_spot[i] == 10) {
            path.moveTo(starX, 250);
        } else if (value_spot[i] == 100) {
            path.moveTo(starX, 200);
        } else if (value_spot[i] == 1000) {
            path.moveTo(starX, 150);
        } else if (value_spot[i] == 10000) {
            path.moveTo(starX, 100);
        } else if (value_spot[i] > 1 && value_spot[i] < 10) {
            //为什么值是300，因为我分段的每一段值为50
            //当值为0，y轴为350,值为1的时候是300
            path.moveTo(starX, 300 - (value_spot[i] % 10) * 5);
        } else if (value_spot[i] > 10 && value_spot[i] < 100) {
            path.moveTo(starX, (float) (250 - ((value_spot[i] % 100) * 0.5)));
        } else if (value_spot[i] > 100 && value_spot[i] < 1000) {
            path.moveTo(starX, (float) (200 - ((value_spot[i] % 1000) * 0.05)));
        } else if (value_spot[i] > 1000 && value_spot[i] < 10000) {
            path.moveTo(starX, (float) (150 - ((value_spot[i] % 10000) * 0.005)));
        }
    }
}


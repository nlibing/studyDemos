package dev.android.studydemo.view.line.kt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by Administrator on 2019/1/4.
 * user: Administrator
 * date: 2019/1/4
 * time; 11:53
 * name: kt折线图
 */
class KtMyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val dataX = arrayOf("周一", "周二", "周三", "周四", "周五", "周六")
    private val dataY = arrayOf("0", "1", "10", "100", "1000", "10000")
    private var valueSpot = arrayOf(0, 8, 367, 786, 666, 300)
    private var starX: Float = 0f
    //网格画笔
    private var gridPaint: Paint = Paint()
    private var gridPath: Path = Path()
    //折线画笔
    private var foldingPaint: Paint = Paint()
    private var foldingPath: Path = Path()
    //曲线画笔
    private var wavePaint: Paint = Paint()
    private var wavePath: Path = Path()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawGrid(canvas)//画网格
        drawGridStraight(canvas)//画折线
        drawWave(canvas)//画波浪曲线
    }

    //画网格
    private fun drawGrid(canvas: Canvas?) {
        gridPaint.style = Paint.Style.STROKE
        gridPaint.color = Color.BLUE
        for (i: Int in 0..7) {
            //x轴
            //x轴起始点(50,400)
            gridPath.moveTo(50F, (400 - (50 * i)).toFloat())
            //轴起始终点(400,400)
            gridPath.lineTo(400F, (400 - (50 * i)).toFloat())
            //循环形成7条x轴线
            //Y轴
            //y轴起始点(50,50)
            gridPath.moveTo((50 + (50 * i)).toFloat(), 50F)
            //y轴起始终点(50,400)
            gridPath.lineTo((50 + (50 * i)).toFloat(), 400F)
            //循环形成7条Y轴线
        }
        for (i: Int in 0 until dataX.size) {
            //x轴值
            gridPaint.textAlign = Paint.Align.CENTER//意思这个文本在(100,420)这个点中间
            canvas!!.drawText(dataX[i], (100 + (50 * i)).toFloat(), 420F, gridPaint);
        }
        for (i: Int in 0 until dataY.size) {
            //y轴值
            gridPaint.textAlign = Paint.Align.RIGHT//意思这个文本在(40,355)这个点右间
            canvas!!.drawText(dataY[i], 40F, (355 - (50 * i)).toFloat(), gridPaint);
        }
        canvas!!.drawPath(gridPath, gridPaint)
    }

    //画波浪曲线
    private fun drawWave(canvas: Canvas?) {
        wavePaint.style = Paint.Style.STROKE
        wavePaint.color = Color.RED
        for (i: Int in 0 until valueSpot.size) {
            if (i == 0) {
                startLine(wavePath, 0)
            } else {
                when (valueSpot[i]) {
                    in 1..10 ->
                        fluctuation(i, 5.0, 10, 300f)
                    in 10..100 ->
                        fluctuation(i, 0.5, 100, 250f)
                    in 100..1000 ->
                        fluctuation(i, 0.05, 1000, 200f)
                    in 1000..10000 ->
                        fluctuation(i, 0.005, 10000, 150f)
                }
                starX = ((100 + i * 50).toFloat())
            }
        }
        canvas!!.drawPath(wavePath, wavePaint)
    }

    /**
     *  multiple 倍数
     *  percentage 百分比
     */
    private fun fluctuation(i: Int, multiple: Double, percentage: Int, starX2: Float) {
        if (i % 2 == 0) {
            wavePath.quadTo((starX + (100 + i * 50)) / 2, 400f, ((100 + i * 50).toFloat()), ((starX2 - (valueSpot[i] % percentage) * multiple).toFloat()))
        } else {
            wavePath.quadTo((starX + (100 + i * 50)) / 2, 25f, ((100 + i * 50).toFloat()), ((starX2 - (valueSpot[i] % percentage) * multiple).toFloat()))
        }
    }

    //起始点
    private fun startLine(path: Path, i: Int) {
        starX = 100f
        when (valueSpot[i]) {
            0 -> path.moveTo(starX, 350f)
            1 -> path.moveTo(starX, 300f)
            10 -> path.moveTo(starX, 250f)
            100 -> path.moveTo(starX, 200f)
            1000 -> path.moveTo(starX, 150f)
            10000 -> path.moveTo(starX, 100f)
        //为什么值是300，因为我分段的每一段值为50
        //当值为0，y轴为350,值为1的时候是300
            in 1..10 -> path.moveTo(starX, (300 - (valueSpot[i] % 10) * 5).toFloat())
            in 10..100 -> path.moveTo(starX, ((250 - ((valueSpot[i] % 100) * 0.5)).toFloat()))
            in 100..1000 -> path.moveTo(starX, ((200 - ((valueSpot[i] % 1000) * 0.05)).toFloat()))
            in 1000..10000 -> path.moveTo(starX, ((150 - ((valueSpot[i] % 10000) * 0.005)).toFloat()))
        }
    }

    private fun drawGridStraight(canvas: Canvas?) {
        foldingPaint.style = Paint.Style.STROKE
        foldingPaint.color = Color.RED
        for (i: Int in 0 until valueSpot.size) {
            if (i == 0) {
                startLine(foldingPath, 0)
            } else {
                //为什么都是100？，因为这里是从第二个点开始画，第一点是50，所以起始x轴100+
                when (valueSpot[i]) {
                    0 -> foldingPath.lineTo((100 + i * 50).toFloat(), 350f)
                    1 -> foldingPath.lineTo((100 + i * 50).toFloat(), 300f)
                    10 -> foldingPath.lineTo((100 + i * 50).toFloat(), 250f)
                    100 -> foldingPath.lineTo((100 + i * 50).toFloat(), 200f)
                    1000 -> foldingPath.lineTo((100 + i * 50).toFloat(), 150f)
                    10000 -> foldingPath.lineTo((100 + i * 50).toFloat(), 100f)
                    in 1..10 -> foldingPath.lineTo(((100 + i * 50).toFloat()), (300 - (valueSpot[i] % 10) * 5).toFloat())
                    in 10..100 -> foldingPath.lineTo((100 + i * 50).toFloat(), (250 - valueSpot[i] % 100 * 0.5).toFloat())
                    in 100..1000 -> foldingPath.lineTo((100 + i * 50).toFloat(), (200 - valueSpot[i] % 1000 * 0.05).toFloat())
                    in 1000..10000 -> foldingPath.lineTo(((100 + i * 50).toFloat()), ((150 - (valueSpot[i] % 10000) * 0.005).toFloat()));
                }
            }
        }
        canvas!!.drawPath(foldingPath, foldingPaint)
    }
}
package dev.android.studydemo.combinationView.kt

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import dev.android.studydemo.R

/**
 * Created by Administrator on 2019/1/7.
 * user: Administrator
 * date: 2019/1/7
 * time; 18:05
 * name: dev.android.studydemo.combinationView.kt
 */
class KtQuantityControlView : LinearLayout, View.OnClickListener {
    lateinit var remove: ImageView
    lateinit var add: ImageView
    private lateinit var tv_number: TextView
    private var mContext: Context? = null
    lateinit var quantityOnClickListener: QuantityOnClickListener;
    private var number: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        addView(View.inflate(mContext, R.layout.quantity_control_view, null))
        remove = findViewById(R.id.iv_remove)
        add = findViewById(R.id.iv_add)
        tv_number = findViewById(R.id.tv_number)
        remove.setOnClickListener(this)
        add.setOnClickListener(this)
        tv_number.text = number.toString()
    }

    fun addIncrease() {
        number++
        tv_number.text = number.toString()
    }

    fun reduce() {
        if (number == 0) {
            Toast.makeText(mContext, "最小值为1", Toast.LENGTH_SHORT).show()
        } else {
            number--
            tv_number.text = number.toString()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_add -> quantityOnClickListener.addNumber()
            R.id.iv_remove -> quantityOnClickListener.removeNumber()
        }
    }

    interface QuantityOnClickListener {
        fun addNumber()
        fun removeNumber()
    }
}
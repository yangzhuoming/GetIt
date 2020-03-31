package com.leon.lib_ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LogoView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private val paint = Paint()
    private val path = Path()
    private var typeFace: Typeface

    private lateinit var typedArray: TypedArray
    private var topText: String = ""
    private var bottomText: String = ""
    private var useWaveLine = true
    private var topColor = 0
    private var bottomColor = 0
    private var bottomTextColor = 0
    private var bottomTextSize = 0f
    private var topTextColor = 0
    private var topTextSize = 0f
    private var myHeight = 0
    private var myWidth = 0

    init {
        paint.strokeWidth = 2f

        typeFace = Typeface.createFromAsset(context.assets, "font/cute.ttf")
        //typeFace = context.resources.getFont(R.font.cute)

        if (null != attributeSet) {
            typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LogoView)
            topText = typedArray.getString(R.styleable.LogoView_topText) ?: ""
            bottomText = typedArray.getString(R.styleable.LogoView_bottomText) ?: ""
            topTextSize = typedArray.getDimension(R.styleable.LogoView_topTextSize, 20f)
            bottomTextSize = typedArray.getDimension(R.styleable.LogoView_bottomTextSize, 10f)
            topTextColor = typedArray.getColor(R.styleable.LogoView_topTextColor, Color.GRAY)
            bottomTextColor =
                typedArray.getColor(R.styleable.LogoView_bottomTextColor, Color.GRAY)
            topColor = typedArray.getColor(R.styleable.LogoView_topColor, Color.GRAY)
            bottomColor = typedArray.getColor(R.styleable.LogoView_bottomColor, Color.GRAY)
            useWaveLine = typedArray.getBoolean(R.styleable.LogoView_waveLine, false)
        } else run {
            topText = ""
            bottomText = ""
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        myHeight = MeasureSpec.getSize(heightMeasureSpec)
        myWidth = MeasureSpec.getSize(widthMeasureSpec)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(topColor)

        paint.textSize = topTextSize
        paint.textAlign = Paint.Align.CENTER
        paint.color = topTextColor
        paint.typeface = typeFace
        val topBounds = Rect()
        paint.getTextBounds(topText, 0, topText.length, topBounds)
        val offSet = ((topBounds.top + topBounds.bottom) / 2).toFloat()
        canvas.drawText(topText, (myWidth / 2).toFloat(), myHeight / 2 - offSet, paint)

        paint.textSize = bottomTextSize
        paint.textAlign = Paint.Align.CENTER
        paint.color = bottomTextColor
        val bottomBounds = Rect()
        paint.getTextBounds(bottomText, 0, bottomText.length, bottomBounds)
        val bottomOffset = ((bottomBounds.top + bottomBounds.bottom) / 2).toFloat()
        canvas.drawText(
            bottomText,
            (myWidth / 2).toFloat(),
            (myHeight / 2).toFloat() - offSet - bottomOffset * 4,
            paint
        )

        if (useWaveLine) {
            paint.color = bottomColor

            paint.alpha = 40
            path.moveTo(0f, (myHeight / 9 * 8).toFloat())
            path.quadTo(
                (myWidth / 6).toFloat(),
                (myHeight / 9 * 7).toFloat(),
                (myWidth / 3).toFloat(),
                (myHeight / 9 * 8).toFloat()
            )
            path.lineTo((myWidth / 3).toFloat(), myHeight.toFloat())
            path.lineTo(0f, myHeight.toFloat())
            canvas.drawPath(path, paint)

            path.reset()
            path.moveTo((myWidth / 3 * 2).toFloat(), (myHeight / 9 * 8).toFloat())
            path.quadTo(
                (myWidth / 6 * 5).toFloat(),
                (myHeight / 9 * 7).toFloat(),
                myWidth.toFloat(),
                (myHeight / 9 * 8).toFloat()
            )
            path.lineTo(myWidth.toFloat(), myHeight.toFloat())
            path.lineTo((myWidth / 3 * 2).toFloat(), myHeight.toFloat())
            canvas.drawPath(path, paint)

            path.reset()
            paint.alpha = 128
            path.moveTo(0f, myHeight.toFloat())
            path.quadTo(
                (myWidth / 6).toFloat(),
                (myHeight / 9 * 7).toFloat(),
                (myWidth / 3).toFloat(),
                myHeight.toFloat()
            )
            canvas.drawPath(path, paint)

            path.reset()
            path.moveTo((myWidth / 3 * 2).toFloat(), myHeight.toFloat())
            path.quadTo(
                (myWidth / 6 * 5).toFloat(),
                (myHeight / 9 * 7).toFloat(),
                myWidth.toFloat(),
                myHeight.toFloat()
            )
            canvas.drawPath(path, paint)

            path.reset()
            path.moveTo(0f, (myHeight / 9 * 8).toFloat())
            path.quadTo(
                (myWidth / 6).toFloat(),
                myHeight.toFloat(),
                (myWidth / 3).toFloat(),
                (myHeight / 9 * 8).toFloat()
            )
            path.quadTo(
                (myWidth / 2).toFloat(),
                (myHeight / 9 * 7).toFloat(),
                (myWidth / 3 * 2).toFloat(),
                (myHeight / 9 * 8).toFloat()
            )
            path.quadTo(
                (myWidth / 6 * 5).toFloat(),
                myHeight.toFloat(),
                myWidth.toFloat(),
                (myHeight / 9 * 8).toFloat()
            )

            path.lineTo(myWidth.toFloat(), myHeight.toFloat())
            path.lineTo(0f, myHeight.toFloat())
            paint.alpha = 255
            path.close()
            canvas.drawPath(path, paint)

        }

    }

}
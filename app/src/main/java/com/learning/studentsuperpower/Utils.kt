package com.learning.studentsuperpower

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation

object Utils {


    fun View.addBackground(
        startColorResId: Int? = null,
        endColorResId: Int? = null,
        middleColorResId: Int? = null,
        cornerRadiusResId: Int? = null,
        angle: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
        rippleColor: Int? = R.color.grey,
        strokeColorResId: Int? = null,
        strokeWidthResId: Int? = null
    ) {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE

        // Set Drawable Colors
        val startColor = startColorResId?.let { ContextCompat.getColor(context, it) }
        val endColor = endColorResId?.let { ContextCompat.getColor(context, it) }
        val middleColor = middleColorResId?.let { ContextCompat.getColor(context, it) }

        if (startColor != null && endColor != null) {
            if (middleColor != null) {
                val colors = intArrayOf(startColor, middleColor, endColor)
                shape.colors = colors
            } else {
                val colors = intArrayOf(startColor, endColor)
                shape.colors = colors
            }
        } else if (startColor != null) {
            shape.setColor(startColor)
        }

        // Set Corner Radius
        cornerRadiusResId?.let {
            shape.cornerRadius = resources.getDimension(it)
        }

        // Setting Angle for Drawable Background
        shape.orientation = angle

        // Set Stroke Color & Width
        strokeColorResId?.let { colorResId ->
            val strokeColor = ContextCompat.getColor(context, colorResId)
            strokeWidthResId?.let { widthResId ->
                val strokeWidth = resources.getDimensionPixelSize(widthResId)
                shape.setStroke(strokeWidth, strokeColor)
            }
        }

        // Set Ripple Effect
        val ripple = ColorStateList.valueOf(ContextCompat.getColor(context, rippleColor!!))
        val rippleDrawable = RippleDrawable(ripple, shape, null)

        this.background = rippleDrawable
    }

    @JvmStatic
    @SuppressLint("Range")
    fun getToolTip(
        ctx: Context?, message: CharSequence,
        @androidx.annotation.FloatRange arrowPosition: Float,
    ): Balloon {
        return Balloon.Builder(ctx!!)
            .setArrowSize(10)
            .setAutoDismissDuration(1500)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(arrowPosition)
            .setTextSize(15f)
            .setPaddingRight(16)
            .setPaddingLeft(16)
            .setHeight(56)
            .setCornerRadius(8f)
            .setAlpha(0.9f)
            .setDismissWhenClicked(true)
            .setText(message)
            .setTextColor(ContextCompat.getColor(ctx, R.color.white))
            .setTextIsHtml(true) // .setIconDrawable(ContextCompat.getDrawable(AddExamQnActivity.this, R.drawable.ic_report))
            .setBackgroundColor(ContextCompat.getColor(ctx, R.color.black))
            .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            .build()
    }

}
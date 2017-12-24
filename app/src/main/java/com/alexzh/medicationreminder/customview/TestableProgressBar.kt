package com.alexzh.medicationreminder.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ProgressBar
import com.alexzh.medicationreminder.R

class TestableProgressBar : ProgressBar {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setIndeterminateDrawable(drawable: Drawable) {
        super.setIndeterminateDrawable(getProgressDrawable(drawable))
    }

    private fun getProgressDrawable(drawable: Drawable) : Drawable {
        return if (isAnimationEnabled())
            ContextCompat.getDrawable(context, R.drawable.ic_refresh_black_16dp)!!
        else
            drawable
    }

    private fun isAnimationEnabled() : Boolean {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) == 0f
    }
}
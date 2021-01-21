/*
 * Copyright (c) 2018. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jahirfiquitiva.chip

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.Icon
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class ChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @StyleRes defStyle: Int = 0
                                             ) : CardView(context, attrs, defStyle) {
    
    private var contentLayout: View? = null
    private var textView: TextView? = null
    private var iconView: ImageView? = null
    private var actionIconView: ImageView? = null
    
    private var contentFillsChip: Boolean = false
    
    private var internalCustomRadius = -1F
        set(value) {
            field = value
            requestLayout()
        }
    
    var text: String
        get() = textView?.text.toString()
        set(value) {
            textView?.text = value
        }
    
    var textSize: Float
        get() = textView?.textSize ?: 0.0F
        set(value) {
            textView?.textSize = value
        }
    
    @ColorInt
    private var bgColor: Int = 0
        set(value) {
            field = value
            internalSetBackground()
        }
    
    @ColorInt
    var rippleColor: Int = 0
        set(value) {
            field = value
            internalSetBackground()
        }
    
    var strokeWidth: Int = 0
        set(value) {
            field = value
            internalSetBackground()
        }
    
    @ColorInt
    var strokeColor: Int = cardBackgroundColor.defaultColor
        set(value) {
            field = value
            internalSetBackground()
        }
    
    init {
        initChip(context, attrs)
    }
    
    private fun initChip(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.chip, parent as? ViewGroup ?: this)
        
        contentLayout = findViewById(R.id.chip_content)
        textView = findViewById(R.id.chip_text)
        iconView = findViewById(R.id.chip_icon)
        actionIconView = findViewById(R.id.chip_action_icon)
        
        val styles = context.obtainStyledAttributes(attrs, R.styleable.ChipView, 0, 0)
        try {
            text = styles.getString(R.styleable.ChipView_chipText) ?: ""
            
            contentFillsChip = styles.getBoolean(R.styleable.ChipView_chipContentFillsChip, false)
            
            val txtSize = styles.getDimensionPixelSize(R.styleable.ChipView_chipTextSize, 0)
            if (txtSize > 0)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize.toFloat())
            
            setTextColor(
                styles.getColor(
                    R.styleable.ChipView_chipTextColor,
                    ContextCompat.getColor(context, R.color.default_chip_text_color)))
            
            strokeColor = styles.getColor(
                R.styleable.ChipView_chipStrokeColor,
                ContextCompat.getColor(context, R.color.default_chip_bg_color))
            strokeWidth =
                styles.getDimension(R.styleable.ChipView_chipStrokeWidth, 0.0F).roundToInt()
            
            bgColor =
                styles.getColor(
                    R.styleable.ChipView_chipBgColor,
                    ContextCompat.getColor(context, R.color.default_chip_bg_color))
            
            rippleColor = styles.getColor(R.styleable.ChipView_chipRippleColor, 0)
            
            setIcon(styles.getDrawable(R.styleable.ChipView_chipIcon))
            setActionIcon(styles.getDrawable(R.styleable.ChipView_chipActionIcon))
            
            super.setRadius(0F)
            radius = styles.getDimension(R.styleable.ChipView_chipRadius, -1F)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            styles.recycle()
        }
    }
    
    @CallSuper
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val canModifyDimensions =
            layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT &&
                layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT
        if (canModifyDimensions) {
            val minHeight = if (minimumHeight <= 0) 32.dpToPx else minimumHeight
            val properHeight = if (measuredHeight < minHeight) minHeight else measuredHeight
            val minWidth =
                if (minimumWidth <= 0) (measuredHeight * 1.25F).roundToInt() else minimumWidth
            val properWidth = if (measuredWidth < minWidth) minWidth else measuredWidth
            setMeasuredDimension(properWidth, properHeight)
            if (radius < 0F) radius = (properHeight / 2.0F)
            // val horPadding = (radius / 2.0F).roundToInt()
            // setPaddingHorizontal(horPadding)
            // setPaddingVertical((horPadding / 2.0F).roundToInt())
            if (contentFillsChip) {
                contentLayout?.minimumWidth = properWidth
                contentLayout?.minimumHeight = properHeight
            }
        }
    }
    
    @CallSuper
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        super.setRadius(
            if (internalCustomRadius < 0F) measuredHeight / 2.0F else internalCustomRadius)
    }
    
    @CallSuper
    override fun setRadius(radius: Float) {
        internalCustomRadius = radius
        if (strokeWidth > 0) internalSetBackground()
    }
    
    override fun getRadius(): Float = internalCustomRadius
    
    @CallSuper
    open fun setText(@StringRes res: Int) {
        text = context.getString(res)
    }
    
    @CallSuper
    open fun setTextColorFromRes(@ColorRes res: Int) {
        textView?.setTextColor(ContextCompat.getColor(context, res))
    }
    
    @CallSuper
    open fun setTextColor(color: Int) {
        textView?.setTextColor(color)
    }
    
    @CallSuper
    open fun setTextColor(colors: ColorStateList) {
        textView?.setTextColor(colors)
    }
    
    @CallSuper
    open fun setTextSize(unit: Int, size: Float) {
        textSize = size
        textView?.setTextSize(unit, size)
    }
    
    @CallSuper
    open fun setTextTypeface(tf: Typeface? = textView?.typeface, style: Int = Typeface.NORMAL) {
        textView?.setTypeface(tf, style)
    }
    
    @Suppress("DEPRECATION")
    @CallSuper
    open fun setTextAppearance(@StyleRes style: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView?.setTextAppearance(style)
        } else {
            textView?.setTextAppearance(context, style)
        }
    }
    
    @CallSuper
    open fun setIcon(@DrawableRes drawable: Int) {
        setIcon(ContextCompat.getDrawable(context, drawable))
    }
    
    @CallSuper
    open fun setIcon(drawable: Drawable?) {
        iconView?.setImageDrawable(drawable)
        iconView?.visibility = if (drawable != null) View.VISIBLE else View.GONE
    }
    
    @CallSuper
    open fun setIcon(bitmap: Bitmap?) {
        iconView?.setImageBitmap(bitmap)
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    @CallSuper
    open fun setIcon(icon: Icon?) {
        iconView?.setImageIcon(icon)
    }
    
    @CallSuper
    open fun setActionIcon(@DrawableRes drawable: Int) {
        setActionIcon(ContextCompat.getDrawable(context, drawable))
    }
    
    @CallSuper
    open fun setActionIcon(drawable: Drawable?) {
        actionIconView?.setImageDrawable(drawable)
        actionIconView?.visibility = if (drawable != null) View.VISIBLE else View.GONE
    }
    
    @CallSuper
    open fun setActionIcon(bitmap: Bitmap?) {
        actionIconView?.setImageBitmap(bitmap)
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    @CallSuper
    open fun setActionIcon(actionIcon: Icon?) {
        actionIconView?.setImageIcon(actionIcon)
    }
    
    @ColorInt
    fun getBackgroundColor(): Int = bgColor
    
    override fun getCardBackgroundColor(): ColorStateList = ColorStateList.valueOf(bgColor)
    
    @CallSuper
    open fun setBackgroundColorFromRes(@ColorRes color: Int) {
        setBackgroundColor(ContextCompat.getColor(context, color))
    }
    
    @CallSuper
    override fun setBackgroundColor(@ColorInt color: Int) {
        bgColor = color
    }
    
    @CallSuper
    override fun setBackground(background: Drawable?) {
        contentLayout?.background = background
    }
    
    @CallSuper
    override fun setBackgroundResource(resId: Int) {
        contentLayout?.setBackgroundResource(resId)
    }
    
    @CallSuper
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        contentLayout?.setPadding(left, top, right, bottom)
    }
    
    /*
    fun setPaddingLeft(left: Int) {
        contentLayout?.setPaddingLeft(left)
    }
    
    fun setPaddingTop(padding: Int) {
        contentLayout?.setPaddingTop(padding)
    }
    
    fun setPaddingRight(padding: Int) {
        contentLayout?.setPaddingRight(padding)
    }
    
    fun setPaddingBottom(padding: Int) {
        contentLayout?.setPaddingBottom(padding)
    }
    
    fun setPaddingHorizontal(padding: Int) {
        contentLayout?.setPaddingHorizontal(padding)
    }
    
    fun setPaddingVertical(padding: Int) {
        contentLayout?.setPaddingVertical(padding)
    }
    
    fun setPadding(padding: Int) {
        contentLayout?.setPadding(padding)
    }
    */
    
    private fun internalSetBackground() {
        val fgResId: Int = try {
            val attrs = intArrayOf(R.attr.selectableItemBackground)
            val typedArray = context.obtainStyledAttributes(attrs)
            val fgRes = typedArray?.getResourceId(0, 0) ?: 0
            typedArray?.recycle()
            fgRes
        } catch (ignored: Exception) {
            0
        }
        
        val bgDrawable: Drawable? = if (strokeWidth > 0) {
            GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadii =
                    floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
                setColor(bgColor)
                setStroke(strokeWidth, strokeColor)
            }
        } else background
        
        @ColorInt
        val fgColor: Int = if (rippleColor == 0) {
            if (bgColor.isColorDark()) bgColor.lighten() else bgColor.darken()
        } else {
            rippleColor
        }
        
        val fgDrawable: Drawable? = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
                RippleDrawable(ColorStateList.valueOf(fgColor), null, bgDrawable)
            fgResId != 0 -> ContextCompat.getDrawable(context, fgResId)
            else -> null
        }
        
        if (strokeWidth > 0) {
            super.setCardBackgroundColor(Color.parseColor("#00000000"))
            super.setBackground(bgDrawable)
        } else {
            super.setCardBackgroundColor(bgColor)
        }
        foreground = fgDrawable
    }
    
    private fun makeActionIconClickable() {
        try {
            val outValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.actionBarItemBackground, outValue, true)
            actionIconView?.setBackgroundResource(outValue.resourceId)
        } catch (e: Exception) {
            Log.e("ChipView", e.message.toString())
        }
        actionIconView?.isClickable = true
        actionIconView?.isFocusable = true
    }
    
    @CallSuper
    open fun setOnActionClickListener(l: OnClickListener?) {
        makeActionIconClickable()
        actionIconView?.setOnClickListener(l)
    }
    
    @CallSuper
    open fun setOnActionClickListener(l: (View) -> Unit) {
        makeActionIconClickable()
        actionIconView?.setOnClickListener { l(it) }
    }
    
    @CallSuper
    open fun setOnActionLongClickListener(l: OnLongClickListener?) {
        makeActionIconClickable()
        actionIconView?.setOnLongClickListener(l)
    }
    
    @CallSuper
    open fun setOnActionLongClickListener(l: (View) -> Boolean) {
        makeActionIconClickable()
        actionIconView?.setOnLongClickListener { l(it) }
    }
}
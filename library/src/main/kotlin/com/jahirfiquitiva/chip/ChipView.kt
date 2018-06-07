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
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.Icon
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.support.annotation.CallSuper
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.roundToInt

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class ChipView : LinearLayout {
    
    private var chipRoot: LinearLayout? = null
    private var cardView: CardView? = null
    private var textView: TextView? = null
    private var iconView: ImageView? = null
    private var actionIconView: ImageView? = null
    
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
    
    var radius: Float
        get() = cardView?.radius ?: 0.0F
        set(value) {
            cardView?.radius = value
        }
    
    @ColorInt
    private var bgColor: Int = 0
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
    var strokeColor: Int = cardView?.cardBackgroundColor?.defaultColor ?: 0
        set(value) {
            field = value
            internalSetBackground()
        }
    
    constructor(context: Context) : super(context) {
        init(context, null)
    }
    
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }
    
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
        init(context, attrs)
    }
    
    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.chip, this)
        
        this.chipRoot = findViewById(R.id.chip_root)
        this.cardView = findViewById(R.id.chip_card)
        this.textView = findViewById(R.id.chip_text)
        this.iconView = findViewById(R.id.chip_icon)
        this.actionIconView = findViewById(R.id.chip_action_icon)
        
        val styles = context.obtainStyledAttributes(attrs, R.styleable.ChipView, 0, 0)
        try {
            text = styles.getString(R.styleable.ChipView_chipText) ?: ""
            
            val txtSize = styles.getDimensionPixelSize(R.styleable.ChipView_chipTextSize, 0)
            if (txtSize > 0)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize.toFloat())
            
            setTextColor(
                styles.getColor(
                    R.styleable.ChipView_chipTextColor,
                    ContextCompat.getColor(context, R.color.default_chip_text_color)))
            elevation = styles.getDimension(R.styleable.ChipView_chipElevation, 0.0F)
            radius = styles.getDimension(R.styleable.ChipView_chipRadius, 16.dpToPx.toFloat())
            
            strokeColor = styles.getColor(
                R.styleable.ChipView_chipStrokeColor,
                ContextCompat.getColor(context, R.color.default_chip_bg_color))
            strokeWidth =
                styles.getDimension(R.styleable.ChipView_chipStrokeWidth, 0.0F).roundToInt()
            
            bgColor =
                styles.getColor(
                    R.styleable.ChipView_chipBgColor,
                    ContextCompat.getColor(context, R.color.default_chip_bg_color))
            
            setIcon(styles.getDrawable(R.styleable.ChipView_chipIcon))
            setActionIcon(styles.getDrawable(R.styleable.ChipView_chipActionIcon))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            styles.recycle()
        }
    }
    
    @CallSuper
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        chipRoot?.setPadding(left, top, right, bottom)
    }
    
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
    
    override fun getBackground(): Drawable? = cardView?.background
    
    @ColorInt
    fun getBackgroundColor(): Int = bgColor
    
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getBackgroundTintList(): ColorStateList? = cardView?.backgroundTintList
    
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getBackgroundTintMode(): PorterDuff.Mode? = cardView?.backgroundTintMode
    
    @CallSuper
    open fun setBackgroundColorFromRes(@ColorRes color: Int) {
        setBackgroundColor(ContextCompat.getColor(context, color))
    }
    
    @CallSuper
    override fun setBackgroundColor(@ColorInt color: Int) {
        bgColor = color
    }
    
    @CallSuper
    override fun setBackgroundResource(resid: Int) {
        throw IllegalArgumentException("Background can only be set with a color")
    }
    
    @CallSuper
    override fun setBackground(background: Drawable?) {
        throw IllegalArgumentException("Background can only be set with a color")
    }
    
    @CallSuper
    @Suppress("OverridingDeprecatedMember")
    override fun setBackgroundDrawable(background: Drawable?) {
        throw IllegalArgumentException("Background can only be set with a color")
    }
    
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
        } else cardView?.background
        
        @ColorInt
        val fgColor: Int = if (bgColor.isColorDark()) bgColor.lighten() else bgColor.darken()
        
        val fgDrawable: Drawable? = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
                RippleDrawable(ColorStateList.valueOf(fgColor), null, bgDrawable)
            fgResId != 0 -> ContextCompat.getDrawable(context, fgResId)
            else -> null
        }
        
        if (strokeWidth > 0) {
            cardView?.setCardBackgroundColor(Color.TRANSPARENT)
            cardView?.background = bgDrawable
        } else {
            cardView?.setCardBackgroundColor(bgColor)
        }
        cardView?.foreground = fgDrawable
    }
    
    @CallSuper
    override fun setElevation(elevation: Float) {
        if (strokeWidth > 0) return
        cardView?.cardElevation = elevation
        if (elevation > 0.0F) {
            val padding = (elevation * 1.5F).toInt()
            chipRoot?.setPadding(padding, padding, padding, padding)
        } else chipRoot?.setPadding(0, 0, 0, 0)
    }
    
    @CallSuper
    override fun getElevation(): Float {
        return cardView?.cardElevation ?: 0.0F
    }
    
    @CallSuper
    override fun setOnClickListener(l: OnClickListener?) {
        cardView?.setOnClickListener(l)
    }
    
    @CallSuper
    override fun setOnLongClickListener(l: OnLongClickListener?) {
        cardView?.setOnLongClickListener(l)
    }
    
    @CallSuper
    open fun setOnActionClickListener(l: OnClickListener?) {
        actionIconView?.setOnClickListener(l)
    }
    
    @CallSuper
    open fun setOnActionClickListener(l: (View) -> Unit) {
        actionIconView?.setOnClickListener { l(it) }
    }
    
    @CallSuper
    open fun setOnActionLongClickListener(l: OnLongClickListener?) {
        actionIconView?.setOnLongClickListener(l)
    }
    
    @CallSuper
    open fun setOnActionLongClickListener(l: (View) -> Boolean) {
        actionIconView?.setOnLongClickListener { l(it) }
    }
}
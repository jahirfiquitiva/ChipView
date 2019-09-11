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

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.customview.widget.ViewDragHelper

/**
 * Created by Allan Wang
 * Taken from his awesome library: KAU (https://github.com/AllanWang/KAU/)
 */

internal inline val Float.dpToPx: Float
    get() = this * Resources.getSystem().displayMetrics.density

internal inline val Int.dpToPx: Int
    get() = toFloat().dpToPx.toInt()

internal fun Int.isColorDark(minDarkness: Float = 0.6F): Boolean =
    ((0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(
        this)) / 255.0) < minDarkness

@ColorInt
internal fun Int.lighten(
    @FloatRange(from = 0.0, to = 1.0)
    factor: Float = 0.2F
                        ): Int {
    val (red, green, blue) = intArrayOf(Color.red(this), Color.green(this), Color.blue(this))
        .map { (it * (1f - factor) + 255f * factor).toInt() }
    return Color.argb(Color.alpha(this), red, green, blue)
}

@ColorInt
internal fun Int.darken(
    @FloatRange(from = 0.0, to = 1.0)
    factor: Float = 0.2F
                       ): Int {
    val (red, green, blue) = intArrayOf(Color.red(this), Color.green(this), Color.blue(this))
        .map { (it * (1f - factor)).toInt() }
    return Color.argb(Color.alpha(this), red, green, blue)
}

/*
internal fun View.setPaddingLeft(padding: Int) = setPadding(padding, KAU_LEFT)

internal fun View.setPaddingTop(padding: Int) = setPadding(padding, KAU_TOP)

internal fun View.setPaddingRight(padding: Int) = setPadding(padding, KAU_RIGHT)

internal fun View.setPaddingBottom(padding: Int) = setPadding(padding, KAU_BOTTOM)

internal fun View.setPaddingHorizontal(padding: Int) = setPadding(padding, KAU_HORIZONTAL)

internal fun View.setPaddingVertical(padding: Int) = setPadding(padding, KAU_VERTICAL)

internal fun View.setPadding(padding: Int) = setPadding(padding, KAU_ALL)

private fun View.setPadding(padding: Int, flag: Int) {
    setPadding(
        if (flag and KAU_LEFT > 0) padding else paddingLeft,
        if (flag and KAU_TOP > 0) padding else paddingTop,
        if (flag and KAU_RIGHT > 0) padding else paddingRight,
        if (flag and KAU_BOTTOM > 0) padding else paddingBottom)
}

private const val KAU_LEFT = ViewDragHelper.EDGE_LEFT
private const val KAU_RIGHT = ViewDragHelper.EDGE_RIGHT
private const val KAU_TOP = ViewDragHelper.EDGE_TOP
private const val KAU_BOTTOM = ViewDragHelper.EDGE_BOTTOM
private const val KAU_HORIZONTAL = KAU_LEFT or KAU_RIGHT
private const val KAU_VERTICAL = KAU_TOP or KAU_BOTTOM
private const val KAU_ALL = KAU_HORIZONTAL or KAU_VERTICAL
*/
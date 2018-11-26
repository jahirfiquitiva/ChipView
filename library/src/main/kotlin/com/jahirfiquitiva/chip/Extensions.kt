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
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

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
    factor: Float = 0.25F
                        ): Int {
    val (red, green, blue) = intArrayOf(Color.red(this), Color.green(this), Color.blue(this))
        .map { (it * (1f - factor) + 255f * factor).toInt() }
    return Color.argb(Color.alpha(this), red, green, blue)
}

@ColorInt
internal fun Int.darken(
    @FloatRange(from = 0.0, to = 1.0)
    factor: Float = 0.25F
                       ): Int {
    val (red, green, blue) = intArrayOf(Color.red(this), Color.green(this), Color.blue(this))
        .map { (it * (1f - factor)).toInt() }
    return Color.argb(Color.alpha(this), red, green, blue)
}
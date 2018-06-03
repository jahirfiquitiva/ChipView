package com.jahirfiquitiva.chip

import android.content.res.Resources

internal inline val Float.dpToPx: Float
    get() = this * Resources.getSystem().displayMetrics.density

internal inline val Int.dpToPx: Int
    get() = toFloat().dpToPx.toInt()

internal inline val Float.pxToDp: Float
    get() = this / Resources.getSystem().displayMetrics.density

internal inline val Int.pxToDp: Int
    get() = toFloat().pxToDp.toInt()

internal inline val Float.dpToSp: Float
    get() = this * Resources.getSystem().displayMetrics.scaledDensity

internal inline val Int.dpToSp: Int
    get() = toFloat().dpToSp.toInt()

internal inline val Float.spToDp: Float
    get() = this / Resources.getSystem().displayMetrics.scaledDensity

internal inline val Int.spToDp: Int
    get() = toFloat().spToDp.toInt()
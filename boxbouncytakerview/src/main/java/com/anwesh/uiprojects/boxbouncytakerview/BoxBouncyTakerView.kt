package com.anwesh.uiprojects.boxbouncytakerview

/**
 * Created by anweshmishra on 09/11/19.
 */

import android.view.MotionEvent
import android.view.View
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.content.Context

val nodes : Int = 5
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val boxFactor : Float = 3f
val foreColor : Int = Color.parseColor("#4CAF50")
val backColor : Int = Color.parseColor("#BDBDBD")
val scGap : Float = 0.02f
val delay : Long = 30

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.dividrScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = 1 - Math.sin(Math.PI / 2 + (this) * (Math.PI / 2)).toFloat()

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
import android.graphics.RectF
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
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = Math.sin(Math.PI / 2 + (this) * (Math.PI / 2)).toFloat()

fun Canvas.drawBoxTaker(size : Float, w : Float, scale : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sc : Float = scale.divideScale(1, 2).sinify()
    val rSize : Float = size / boxFactor
    save()
    translate((w - size) * sf, 0f)
    drawLine(0f, 0f, size, 0f, paint)
    restore()
    save()
    translate(size / 2 + (w - size) * sc, 0f)
    drawRect(RectF(-rSize / 2, -rSize, rSize / 2, 0f), paint)
    restore()
}

fun Canvas.drawBBTNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size : Float = w / sizeFactor
    paint.color = foreColor
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    save()
    translate(0f, gap * (i + 1))
    drawBoxTaker(size, w, scale, paint)
    restore()
}

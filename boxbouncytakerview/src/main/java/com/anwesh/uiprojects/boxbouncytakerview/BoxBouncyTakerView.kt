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

class BoxBouncyTakerView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class BBTNode(var i : Int, val state : State = State()) {

        private var next : BBTNode? = null
        private var prev : BBTNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                next = BBTNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas?.drawBBTNode(i, state.scale, paint)
            next?.draw(canvas, paint)
        }

        fun update(cb : (Float) -> Unit) {
            state.update(cb)
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : BBTNode {
            var curr : BBTNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }

    data class BoxBouncyTaker(var i : Int) {

        private val root : BBTNode = BBTNode(0)
        private var curr : BBTNode = root
        private var dir : Int = 1

        fun draw(canvas : Canvas, paint : Paint) {
            root.draw(canvas, paint)
        }

        fun update(cb : (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                cb(it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            curr.startUpdating(cb)
        }
    }
}

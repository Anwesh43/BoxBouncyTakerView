package com.anwesh.uiprojects.boxbouncytakerview

/**
 * Created by anweshmishra on 09/11/19.
 */

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.dividrScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = 1 - Math.sin(Math.PI / 2 + (this) * (Math.PI / 2)).toFloat()

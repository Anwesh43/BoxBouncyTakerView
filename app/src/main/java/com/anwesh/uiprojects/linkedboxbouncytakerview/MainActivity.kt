package com.anwesh.uiprojects.linkedboxbouncytakerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.boxbouncytakerview.BoxBouncyTakerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BoxBouncyTakerView.create(this)
    }
}

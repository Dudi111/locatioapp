package com.example.locationapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.locationapp.frag.SplashFrag

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)








        val fragManager= supportFragmentManager
        val trasaction= fragManager.beginTransaction()
        val splashfrag= SplashFrag()
        trasaction.add(R.id.container, splashfrag).commit()

    }
}
package com.example.cell


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.widget.Button
import com.example.cell.loading.Myloading
//import com.malkinfo.progressbar.uitel.LoadingDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val second = findViewById<Button>(R.id.btn1 )
        second.setOnClickListener {
            val intent= Intent ( this,HomeActivity::class.java)
            startActivity(intent)
        }

        val loading = Myloading(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                loading.isDismiss()
            }

        },5000)

    }



}
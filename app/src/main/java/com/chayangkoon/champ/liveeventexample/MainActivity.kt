package com.chayangkoon.champ.liveeventexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chayangkoon.champ.liveevent.EventObserver

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.toastMessage.observe(this, EventObserver {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        findViewById<Button>(R.id.btnShowToast).setOnClickListener {
            mainViewModel.showToastMessage("Hi LiveEvent")
        }
    }
}
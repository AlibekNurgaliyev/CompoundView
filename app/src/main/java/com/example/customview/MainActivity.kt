package com.example.customview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {

                binding.bottomButtons.setPositiveButtonText("Updated OK")

            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Updated Cancel")

            }
        }
    }
}
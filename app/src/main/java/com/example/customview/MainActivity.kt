package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                binding.bottomButtons.setPositiveButtonText("Updated OK")
                binding.bottomButtons.isProgressMode = true
                Toast.makeText(this, "Positive button clicked", Toast.LENGTH_SHORT).show()
            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Updated Cancel")
                Toast.makeText(this, "Negative button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
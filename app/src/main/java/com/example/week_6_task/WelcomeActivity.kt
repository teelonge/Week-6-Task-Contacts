package com.example.week_6_task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week_6_task.databinding.ActivityWelcomeBinding
import com.example.week_6_task.secondTask.SecondImplementation
import com.example.week_6_task.ui.MainActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)


        // First Implementation
        binding.firstImp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Second Implementation
        binding.secondImp.setOnClickListener {
            startActivity(Intent(this, SecondImplementation::class.java))
        }
    }
}
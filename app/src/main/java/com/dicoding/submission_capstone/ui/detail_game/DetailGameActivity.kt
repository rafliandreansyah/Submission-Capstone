package com.dicoding.submission_capstone.ui.detail_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.submission_capstone.databinding.ActivityDetailGameBinding

class DetailGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
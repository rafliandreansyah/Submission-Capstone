package com.dicoding.submission_capstone.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.submission_capstone.databinding.ActivityGamesBinding

class GamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
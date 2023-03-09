package com.dicoding.submission_capstone.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.submission_capstone.core.ui.GameAdapter
import com.dicoding.submission_capstone.databinding.ActivityGamesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesBinding

    private val gameViewModel: GameViewModel by viewModels()

    @Inject lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}